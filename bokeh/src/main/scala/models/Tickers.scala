package io.continuum.bokeh

@fields abstract class Ticker extends PlotObject {
    object num_minor_ticks extends Field[Int](5)
}

@fields class AdaptiveTicker extends Ticker {
    object base extends Field[Double](10.0)
    object mantissas extends Field[List[Double]](List(2, 5, 10))
    object min_interval extends Field[Double](0.0)
    object max_interval extends Field[Double](100.0)
}

@fields class CompositeTicker extends Ticker {
    object tickers extends Field[List[Ticker]]
}

@fields class SingleIntervalTicker extends Ticker {
    object interval extends Field[Double]
}

@fields class DaysTicker extends Ticker {
    object days extends Field[List[Int]]
}

@fields class MonthsTicker extends Ticker {
    object months extends Field[List[Int]]
}

@fields class YearsTicker extends Ticker

@fields class BasicTicker extends Ticker

@fields class LogTicker extends Ticker

@fields class CategoricalTicker extends Ticker

@fields class DatetimeTicker extends Ticker

@fields abstract class TickFormatter extends PlotObject

@fields class BasicTickFormatter extends TickFormatter {
    // TODO: object precision extends Field[Either[Auto, Int]]
    object use_scientific extends Field[Boolean](true)
    object power_limit_high extends Field[Int](5)
    object power_limit_low extends Field[Int](-3)
}

@fields class LogTickFormatter extends TickFormatter

@fields class CategoricalTickFormatter extends TickFormatter

@fields class DatetimeTickFormatter extends TickFormatter {
    object formats extends Field[Map[DatetimeUnits, List[String]]]
}
