package org.continuumio.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

trait HasFields {
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

class Field[OwnerType, FieldType](rec: OwnerType) {
    type DataType = FieldType

    def owner: OwnerType = rec

    def this(rec: OwnerType, value: FieldType) = {
        this(rec)
        this := value
    }

    val fieldName: Option[String] = None

    private var data: Option[DataType] = None

    def valueOpt: Option[FieldType] = data

    def value: FieldType = valueOpt.get

    def :=(value: DataType) {
        data = Some(value)
    }

    def apply(value: DataType): OwnerType = {
        this := value
        owner
    }
}

class GenericDataSpec[OwnerType, FieldType](rec: OwnerType) extends Field[OwnerType, FieldType](rec) {
    //val name: String
    //val units: Units
    //val default: FieldType

    def apply(name: String): OwnerType = {
        owner
    }

    def apply(name: String, units: Units): OwnerType = {
        owner
    }

    // name: String, units: Units, default: FieldType
}

class DataSpec[OwnerType](rec: OwnerType) extends GenericDataSpec[OwnerType, Double](rec)
