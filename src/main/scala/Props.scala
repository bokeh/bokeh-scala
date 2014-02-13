package org.continuumio.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

trait HasFields extends DefaultImplicits {
    final def fields: List[String] = {
        cm.reflect(this)
            .symbol
            .typeSignature
            .members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< u.typeOf[Field[_, _]])
            .map(_.name.decoded)
            .toList
    }

    final def fieldsWithValues: List[(String, Any)] = {
        val im = cm.reflect(this)
        val modules = im
            .symbol
            .typeSignature
            .members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< u.typeOf[Field[_, _]])
            .toList
        val instances = modules.map(im.reflectModule _).map(_.instance)
        val names = instances
            .collect { case field: Field[_, _] => field.fieldName }
            .zip(modules)
            .collect {
                case (Some(name), _) => name
                case (_, module) => module.name.decoded
            }
        val values = instances
            .collect {
                case data: GenericDataSpec[_, _] => data.toMap
                case field: Field[_, _] => field.valueOpt
            }
        ("type", viewModel) :: names.zip(values)
    }

    def viewModel: String = getClass.getSimpleName
}

import DefaultImplicits._

class Field[OwnerType, FieldType:DefaultValue](rec: OwnerType) {
    type DataType = FieldType

    def owner: OwnerType = rec

    def this(rec: OwnerType, value: FieldType) = {
        this(rec)
        this := value
    }

    val fieldName: Option[String] = None

    private var data: Option[DataType] = None

    def defaultValue: Option[FieldType] = {
        val default = implicitly[DefaultValue[FieldType]].default
        if (default == null) None else Some(default)
    }

    def valueOpt: Option[FieldType] = data orElse defaultValue

    def value: FieldType = valueOpt.get

    def :=(value: DataType) {
        data = Some(value)
    }

    def apply(value: DataType): OwnerType = {
        this := value
        owner
    }
}

class GenericDataSpec[OwnerType, FieldType:DefaultValue](rec: OwnerType) extends Field[OwnerType, FieldType](rec) {
    def this(rec: OwnerType, value: FieldType) = {
        this(rec)
        this := value
    }

    var name: Option[String] = None
    var units: Option[Units] = None
    var default: Option[FieldType] = None

    def apply(name: String): OwnerType = {
        this.name = Some(name)
        owner
    }

    def apply(name: String, units: Units): OwnerType = {
        this.name = Some(name)
        this.units = Some(units)
        owner
    }

    def apply(name: String, units: Units, default: FieldType): OwnerType = {
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

class DataSpec[OwnerType](rec: OwnerType) extends GenericDataSpec[OwnerType, Double](rec)
