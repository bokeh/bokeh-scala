package io.continuum.bokeh

case class Percent(value: Double) {
    require(0.0 <= value && value <= 1.0)
}
