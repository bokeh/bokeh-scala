package org.continuumio.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

trait AbstractField {
    type DataType

    def set(value: Option[DataType])
}

trait HasFields extends core.HListable with DefaultImplicits { self =>
    type SelfType = self.type

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

    final def fieldsWithValues: List[(String, Any)] = {
        fieldsList.map {
            case (name, data: DataSpec[_]) => (name, data.toMap)
            case (name, field: Field[_]) => (name, field.valueOpt)
        }
    }

    final def dirtyFieldsWithValues: List[(String, Any)] = {
        fieldsList.filter(_._2.isDirty).map {
            case (name, data: DataSpec[_]) => (name, data.toMap)
            case (name, field: Field[_]) => (name, field.valueOpt)
        }
    }

    class Field[FieldType:DefaultValue] extends AbstractField with HField {
        type DataType = FieldType

        def owner: SelfType = self

        def this(value: FieldType) = {
            this()
            internalSet(Some(value))
        }

        val fieldName: Option[String] = None

        def defaultValue: Option[FieldType] = {
            val default = implicitly[DefaultValue[FieldType]].default
            if (default == null) None else Some(default)
        }

        protected var data: Option[FieldType] = None
        protected var dirty: Boolean = false

        final def isDirty: Boolean = dirty

        def valueOpt: Option[FieldType] = data orElse defaultValue

        def value: FieldType = valueOpt.get

        final def internalSet(value: Option[FieldType]) {
            data = value
            dirty = true
        }

        def set(value: Option[FieldType]) {
            internalSet(value)
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
    }

    class DataSpec[FieldType:DefaultValue] extends Field[FieldType] {
        def this(value: FieldType) = {
            this()
            internalSet(Some(value))
        }

        protected var field: Option[Symbol] = None
        protected var units: Option[Units] = None
        protected var default: Option[FieldType] = None

        protected def set(field: Option[Symbol] = None, units: Option[Units] = None, default: Option[FieldType] = None) {
            field.foreach(field => this.field = Some(field))
            units.foreach(units => this.units = Some(units))
            default.foreach(default => this.default = Some(default))
            dirty = true
        }

        def apply(field: Symbol): SelfType = {
            set(field=Some(field))
            owner
        }

        def apply(field: Symbol, units: Units): SelfType = {
            set(field=Some(field), units=Some(units))
            owner
        }

        def apply(field: Symbol, units: Units, default: FieldType): SelfType = {
            set(field=Some(field), units=Some(units), default=Some(default))
            owner
        }

        def toMap: Map[String, Any] = {
            val fields = ("value" -> valueOpt) :: ("field" -> field) :: ("units" -> units) :: ("default" -> default) :: Nil
            fields.collect { case (name, Some(value)) => (name, value) } toMap
        }
    }
}
