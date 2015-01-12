package io.continuum.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}
import play.api.libs.json.{Writes,JsValue,JsObject,JsNull}

trait HasFields { self =>
    type SelfType = self.type

    def typeName: String = getClass.getSimpleName

    def values: List[(String, Option[JsValue])]
    def fields: List[AbstractField]

    def toJson: JsObject = JsObject(values.collect { case (name, Some(value)) => (name, value) })

    class Field[FieldType:Default:Writes] extends AbstractField with ValidableField {
        type ValueType = FieldType

        def owner: SelfType = self

        def this(value: FieldType) = {
            this()
            set(Some(value))
        }

        def Default: Option[FieldType] =
            Option(implicitly[Default[FieldType]].default)

        protected var _value: Option[FieldType] = Default
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

        def toJson: Option[JsValue] = {
            if (isDirty) Some(_toJson) else None
        }

        def _toJson: JsValue = {
            valueOpt.map(implicitly[Writes[ValueType]].writes _) getOrElse JsNull
        }
    }

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

        def toMap: Map[String, Any] = {
            Map(fieldOpt.map("field" -> _).getOrElse("value" -> valueOpt))
        }

        override def toSerializable: Option[Any] = Some(toMap)

        override def _toJson: JsObject = {
            val value = fieldOpt
                .map { field => "field" -> implicitly[Writes[Symbol]].writes(field) }
                .getOrElse { "value" -> super._toJson }
            JsObject(List(value))
        }
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

        override def toMap: Map[String, Any] = {
            super.toMap ++ unitsOpt.map("units" -> _).toList
        }

        override def _toJson: JsObject = {
            val json = super._toJson
            unitsOpt.map {
                units => json + ("units" -> implicitly[Writes[UnitsType]].writes(units))
            } getOrElse json
        }
    }

    class Spatial[FieldType:Default:Writes] extends VectorizedWithUnits[FieldType, SpatialUnits] {
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

    class Angular[FieldType:Default:Writes] extends VectorizedWithUnits[FieldType, AngularUnits] {
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
