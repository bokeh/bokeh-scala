package io.continuum.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

case class Validator[T](fn: T => Boolean, message: String)
class ValueError(message: String) extends Exception(message)

trait AbstractField {
    type DataType

    def set(value: Option[DataType])

    def validators: List[Validator[DataType]] = Nil

    def validate(value: DataType): List[String] = {
        validators.filterNot(_.fn(value)).map(_.message)
    }

    def validates(value: DataType) {
        validate(value) match {
            case error :: _ => throw new ValueError(error)
            case Nil =>
        }
    }
}

trait HasFields { self =>
    type SelfType = self.type

    def typeName: String = getClass.getSimpleName

    final def fieldsList: List[(String, HasFields#Field[_])] = {
        val im = cm.reflect(this)
        val modules = im
            .symbol
            .typeSignature
            .members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< u.typeOf[HasFields#Field[_]])
            .toList
        val instances = modules
            .map(im.reflectModule _)
            .map(_.instance)
            .collect { case field: Field[_] => field }
        val names = instances
            .map(_.fieldName)
            .zip(modules)
            .collect {
                case (Some(name), _) => name
                case (_, module) => module.name.decoded
            }
        names.zip(instances)
    }

    final def fieldsWithValues: List[(String, Option[Any])] = {
        fieldsList.map { case (name, field) => (name, field.toSerializable) }
    }

    final def dirtyFieldsWithValues: List[(String, Option[Any])] = {
        fieldsList.filter(_._2.isDirty)
                  .map { case (name, field) => (name, field.toSerializable) }
    }

    class Field[FieldType:DefaultValue] extends AbstractField {
        type DataType = FieldType

        def owner: SelfType = self

        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        val fieldName: Option[String] = None

        def defaultValue: Option[FieldType] =
            Option(implicitly[DefaultValue[FieldType]].default)

        protected var _value: Option[FieldType] = defaultValue
        protected var _dirty: Boolean = false

        final def isDirty: Boolean = _dirty

        def valueOpt: Option[FieldType] = _value

        def value: FieldType = valueOpt.get

        def setValue(value: Option[FieldType]) {
            value.foreach(validates)
            _value = value
        }

        def set(value: Option[FieldType]) {
            setValue(value)
            _dirty = true
        }

        final def :=(value: FieldType) {
            set(Some(value))
        }

        final def <<=(fn: FieldType => FieldType) {
            set(valueOpt.map(fn))
        }

        final def apply(value: FieldType): SelfType = {
            set(Some(value))
            owner
        }

        final def apply(): SelfType = {
            set(None)
            owner
        }

        def toSerializable: Option[Any] = valueOpt
    }

    class DataSpec[FieldType:DefaultValue] extends Field[FieldType] {
        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        def this(units: SpatialUnits) = {
            this()
            _units = Some(units)
        }

        def this(value: FieldType, units: SpatialUnits) = {
            this(value)
            _units = Some(units)
        }

        def this(field: Symbol) = {
            this()
            _field = Some(field)
        }

        def this(field: Symbol, units: SpatialUnits) = {
            this(field)
            _units = Some(units)
        }

        protected var _field: Option[Symbol] = None
        protected var _units: Option[SpatialUnits] = None

        def fieldOpt: Option[Symbol] = _field
        def unitsOpt: Option[SpatialUnits] = _units

        def field: Symbol = _field.get
        def units: SpatialUnits = _units.get

        def apply(units: SpatialUnits): SelfType = {
            _units = Some(units)
            _dirty = true
            owner
        }

        def apply(value: FieldType, units: SpatialUnits): SelfType = {
            set(Some(value))
            _units = Some(units)
            _dirty = true
            owner
        }

        def apply(field: Symbol): SelfType = {
            _field = Some(field)
            _dirty = true
            owner
        }

        def apply(field: Symbol, units: SpatialUnits): SelfType = {
            _field = Some(field)
            _units = Some(units)
            _dirty = true
            owner
        }

        def toMap: Map[String, Any] = {
            val source = fieldOpt.map("field" -> _) orElse valueOpt.map("value" -> _)
            val units = unitsOpt.map("units" -> _)
            (source.toList ++ units.toList).toMap
        }

        override def toSerializable: Option[Any] = Some(toMap)
    }
}
