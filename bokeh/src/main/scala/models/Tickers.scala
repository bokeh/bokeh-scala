package io.continuum.bokeh

@model abstract class Ticker extends Model {
    object num_minor_ticks extends Field[Int](5)
    object desired_num_ticks extends Field[Int](6)
}

@model class FixedTicker extends Ticker {
    object ticks extends Field[List[Double]]
}

@model class AdaptiveTicker extends Ticker {
    object base extends Field[Double](10.0)
    object mantissas extends Field[List[Double]](List(2, 5, 10))
    object min_interval extends Field[Double](0.0)
    object max_interval extends Field[Double](100.0)
}

@model class CompositeTicker extends Ticker {
    object tickers extends Field[List[Ticker]]
}

@model class SingleIntervalTicker extends Ticker {
    object interval extends Field[Double]
}

@model class DaysTicker extends SingleIntervalTicker {
    object days extends Field[List[Int]]
}

@model class MonthsTicker extends SingleIntervalTicker {
    object months extends Field[List[Int]]
}

@model class YearsTicker extends SingleIntervalTicker

@model class BasicTicker extends Ticker

@model class LogTicker extends AdaptiveTicker

@model class CategoricalTicker extends Ticker

@model class DatetimeTicker extends Ticker

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
