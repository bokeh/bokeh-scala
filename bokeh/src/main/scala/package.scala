package io.continuum

package object bokeh extends Formats with NodeUtils {
    implicit class IntPercent(value: Int) {
        def %% : Percent = Percent(value.toDouble/100)
    }

    implicit def DoubleToPercent(value: Double): Percent = Percent(value)

    implicit class BooleanOps(val bool: Boolean) extends AnyVal {
        final def option[A](value: => A): Option[A] = if (bool) Some(value) else None
    }
}
