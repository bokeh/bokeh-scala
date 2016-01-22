package io.continuum.bokeh

@model abstract class TickFormatter extends Model

@model class BasicTickFormatter extends TickFormatter {
    // TODO: object precision extends Field[Either[Auto, Int]]
    object use_scientific extends Field[Boolean](true)
    object power_limit_high extends Field[Int](5)
    object power_limit_low extends Field[Int](-3)
}

@model class LogTickFormatter extends TickFormatter

@model class CategoricalTickFormatter extends TickFormatter

@model class DatetimeTickFormatter extends TickFormatter {
    object formats extends Field[Map[DatetimeUnits, List[String]]]
}

@model class NumeralTickFormatter extends TickFormatter {
    object format extends Field[String]("0,0")
    object language extends Field[NumeralLanguage]
    object rounding extends Field[RoundingFunction]
}

@model class PrintfTickFormatter extends TickFormatter {
    object format extends Field[String]("%s")
}
