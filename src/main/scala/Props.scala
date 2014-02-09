package org.continuumio.bokeh

class Field[OwnerType, FieldType](rec: OwnerType) {
    type DataType = FieldType

    def owner: OwnerType = rec

    def this(rec: OwnerType, value: FieldType) = {
        this(rec)
        this := value
    }

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
