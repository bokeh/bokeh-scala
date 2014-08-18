package io.continuum.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

case class Validator[T](fn: T => Boolean, message: String)
class ValueError(message: String) extends Exception(message)

trait AbstractField {
    type ValueType

    def set(value: Option[ValueType])

    def validators: List[Validator[ValueType]] = Nil

    def validate(value: ValueType): List[String] = {
        validators.filterNot(_.fn(value)).map(_.message)
    }

    def validates(value: ValueType) {
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
        type ValueType = FieldType

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

    class Vectorized[FieldType:DefaultValue] extends Field[FieldType] {
        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        protected var _field: Option[Symbol] = None
        def fieldOpt: Option[Symbol] = _field
        def field: Symbol = _field.get

        def setField(field: Option[Symbol]) {
            _field = field
            _dirty = true
        }

        def apply(field: Symbol): SelfType = {
            setField(Some(field))
            owner
        }

        def toMap: Map[String, Any] = {
            Map(fieldOpt.map("field" -> _).getOrElse("value" -> valueOpt))
        }

        override def toSerializable: Option[Any] = Some(toMap)
    }

    abstract class VectorizedWithUnits[FieldType:DefaultValue, UnitsType <: Units: DefaultValue] extends Vectorized[FieldType] {
        def defaultUnits: Option[UnitsType] =
            Option(implicitly[DefaultValue[UnitsType]].default)

        protected var _units: Option[UnitsType] = defaultUnits
        def unitsOpt: Option[UnitsType] = _units
        def units: UnitsType = _units.get

        def setUnits(units: Option[UnitsType]) {
            _units = units
            _dirty = true
        }

        def apply(units: UnitsType): SelfType = {
            setUnits(Some(units))
            owner
        }

        def apply(value: FieldType, units: UnitsType): SelfType = {
            set(Some(value))
            setUnits(Some(units))
            owner
        }

        def apply(field: Symbol, units: UnitsType): SelfType = {
            setField(Some(field))
            setUnits(Some(units))
            owner
        }

        override def toMap: Map[String, Any] = {
            super.toMap ++ unitsOpt.map("units" -> _).toList
        }
    }

    class Spatial[FieldType:DefaultValue] extends VectorizedWithUnits[FieldType, SpatialUnits] {
        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        def this(units: SpatialUnits) = {
            this()
            setUnits(Some(units))
            _dirty = false  // XXX: hack, see size vs. radius
        }

        def this(value: FieldType, units: SpatialUnits) = {
            this(value)
            setUnits(Some(units))
        }
    }

    class Angular[FieldType:DefaultValue] extends VectorizedWithUnits[FieldType, AngularUnits] {
        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        def this(units: AngularUnits) = {
            this()
            setUnits(Some(units))
            _dirty = false  // XXX: hack, see size vs. radius
        }

        def this(value: FieldType, units: AngularUnits) = {
            this(value)
            setUnits(Some(units))
        }
    }
}
