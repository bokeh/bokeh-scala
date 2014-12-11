package io.continuum

package object bokeh extends Formats with NodeUtils {
    implicit class NumbericOps[T:Numeric](value: T) {
        def %% : Percent = Percent(implicitly[Numeric[T]].toDouble(value)/100)

        def ex: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.EX)
        def px: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.PX)
        def cm: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.CM)
        def mm: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.MM)
        def in: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.IN)
        def pt: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.PT)
        def pc: FontSize = FontSize(implicitly[Numeric[T]].toDouble(value), FontUnits.PC)
    }

    implicit def NumbericToPercent[T:Numeric](value: T): Percent = {
        Percent(implicitly[Numeric[T]].toDouble(value))
    }

    implicit class BooleanOps(val bool: Boolean) extends AnyVal {
        final def option[A](value: => A): Option[A] = if (bool) Some(value) else None
    }
}
