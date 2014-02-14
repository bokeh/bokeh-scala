package org.continuumio.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

trait HasFields extends macros.HListable with DefaultImplicits { self =>
    type SelfType = self.type

    final def fieldsWithValues: List[(String, Any)] = {
        val im = cm.reflect(this)
        val modules = im
            .symbol
            .typeSignature
            .members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< u.typeOf[HasFields#Field[_]])
            .toList
        val instances = modules.map(im.reflectModule _).map(_.instance)
        val names = instances
            .collect { case field: Field[_] => field.fieldName }
            .zip(modules)
            .collect {
                case (Some(name), _) => name
                case (_, module) => module.name.decoded
            }
        val values = instances
            .collect {
                case data: DataSpec[_] => data.toMap
                case field: Field[_] => field.valueOpt
            }
        names.zip(values)
    }

    class Field[FieldType:DefaultValue] extends HField {
        type DataType = FieldType

        def owner: SelfType = self

        def this(value: FieldType) = {
            this()
            this := value
        }

        val fieldName: Option[String] = None

        private var data: Option[FieldType] = None

        def defaultValue: Option[FieldType] = {
            val default = implicitly[DefaultValue[FieldType]].default
            if (default == null) None else Some(default)
        }

        def valueOpt: Option[FieldType] = data orElse defaultValue

        def value: FieldType = valueOpt.get

        def :=(value: FieldType) {
            data = Some(value)
        }

        def apply(value: FieldType): SelfType = {
            this := value
            owner
        }
    }

    class DataSpec[FieldType:DefaultValue] extends Field[FieldType] {
        def this(value: FieldType) = {
            this()
            this := value
        }

        var name: Option[String] = None
        var units: Option[Units] = None
        var default: Option[FieldType] = None

        def apply(name: String): SelfType = {
            this.name = Some(name)
            owner
        }

        def apply(name: String, units: Units): SelfType = {
            this.name = Some(name)
            this.units = Some(units)
            owner
        }

        def apply(name: String, units: Units, default: FieldType): SelfType = {
            this.name = Some(name)
            this.units = Some(units)
            this.default = Some(default)
            owner
        }

        def toMap: Map[String, Any] = {
            val fields = ("value" -> valueOpt) :: ("field" -> name) :: ("units" -> units) :: ("default" -> default) :: Nil
            fields.collect { case (name, Some(value)) => (name, value) } toMap
        }
    }
}
