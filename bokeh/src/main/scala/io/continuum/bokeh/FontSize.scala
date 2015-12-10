package io.continuum.bokeh

case class FontSize(value: Double, units: FontUnits) {
    def toCSS = s"$value${units.name}"
}
