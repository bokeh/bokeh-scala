package org.continuumio

package object bokeh {
    case class Percent(value: Double) {
        require(0.0 <= value && value <= 1.0)
    }

    implicit class IntPercent(value: Int) {
        def %% : Percent = Percent(value.toDouble/100)
    }

    implicit def DoubleToPercent(value: Double): Percent = Percent(value)

    def uuid4(): String = java.util.UUID.randomUUID.toString

    def snakify(name: String): String =
        name.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
            .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
            .toLowerCase
}
