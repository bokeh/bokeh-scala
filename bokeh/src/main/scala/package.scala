package io.continuum

package object bokeh {
    case class Percent(value: Double) {
        require(0.0 <= value && value <= 1.0)
    }

    implicit class IntPercent(value: Int) {
        def %% : Percent = Percent(value.toDouble/100)
    }

    implicit def DoubleToPercent(value: Double): Percent = Percent(value)
}