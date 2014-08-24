package io.continuum.bokeh

trait AbstractField {
    type ValueType

    def set(value: Option[ValueType])
}
