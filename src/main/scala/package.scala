package org.continuumio

package object bokeh {
    case class Percent(value: Double) {
        require(0.0 <= value && value <= 1.0)
    }

    implicit class IntPercent(value: Int) {
        def %% : Percent = Percent(value.toDouble/100)
    }

    def uuid4(): String = java.util.UUID.randomUUID.toString
}
