package io.continuum.bokeh

import play.api.libs.json.{Json,JsValue,JsObject,Writes}

trait HasFields { self =>
    type SelfType = self.type

    def typeName: String = getClass.getSimpleName

    def fields: List[FieldRef]

    def fieldsToJson(all: Boolean = false): JsObject =  {
        JsObject(fields.collect {
            case FieldRef(name, field) if all || field.isDirty => (name, field.toJson)
        })
    }

    class Field[FieldType:Default:Writes] extends AbstractField with ValidableField {
        type ValueType = FieldType

        def owner: SelfType = self

        def this(default: FieldType) = {
            this()
            setValue(Some(default))
        }

        def defaultValue: Option[FieldType] = {
            Option(implicitly[Default[FieldType]].default)
        }

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

        override def toJson: JsValue = Json.toJson(valueOpt)
    }
}

trait Vectorization { self: HasFields =>
    class Vectorized[FieldType:Default:Writes] extends Field[FieldType] {
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

        def apply[M[_]](column: ColumnDataSource#Column[M, FieldType]): SelfType = {
            setField(Some(column.name))
            owner
        }

        private case class VectorValue(value: Option[ValueType], field: Option[Symbol])
        private implicit val VectorValueWrites = Json.writes[VectorValue]

        override def toJson: JsValue = Json.toJson(VectorValue(valueOpt, fieldOpt))
    }

    abstract class VectorizedWithUnits[FieldType:Default:Writes, UnitsType <: Units with EnumType: Default] extends Vectorized[FieldType] {
        def defaultUnits: Option[UnitsType] =
            Option(implicitly[Default[UnitsType]].default)

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

        def apply[M[_]](column: ColumnDataSource#Column[M, FieldType], units: UnitsType): SelfType = {
            setUnits(Some(units))
            apply(column)
        }

        private case class VectorValue(value: Option[ValueType], field: Option[Symbol], units: Option[UnitsType])
        private implicit val VectorValueWrites = Json.writes[VectorValue]

        override def toJson: JsValue = Json.toJson(VectorValue(valueOpt, fieldOpt, unitsOpt))
    }

    class Spatial[FieldType:Default:Writes] extends VectorizedWithUnits[FieldType, SpatialUnits] {
        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        def this(units: SpatialUnits) = {
            this()
            setUnits(Some(units))
        }

        def this(value: FieldType, units: SpatialUnits) = {
            this(value)
            setUnits(Some(units))
        }
    }

    class Angular[FieldType:Default:Writes] extends VectorizedWithUnits[FieldType, AngularUnits] {
        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        def this(units: AngularUnits) = {
            this()
            setUnits(Some(units))
        }

        def this(value: FieldType, units: AngularUnits) = {
            this(value)
            setUnits(Some(units))
        }
    }
}
