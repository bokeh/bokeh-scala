package io.continuum.bokeh

trait AbstractField {
    type ValueType

    def valueOpt: Option[ValueType]
    def value: ValueType

    def set(value: Option[ValueType])
}

trait Refs[Ref] {
    def getRef: Ref
    def id: AbstractField { type ValueType = String }
}
