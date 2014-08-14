package io.continuum.bokeh

abstract class Ticker extends PlotObject {
    object num_minor_ticks extends Field[Int](5)
}

class AdaptiveTicker extends Ticker {
    object base extends Field[Double](10.0)
    object mantissas extends Field[List[Double]](List(2, 5, 10))
    object min_interval extends Field[Double](0.0)
    object max_interval extends Field[Double](100.0)
}

class CompositeTicker extends Ticker {
    object tickers extends Field[List[Ticker]]
}

class SingleIntervalTicker extends Ticker {
    object interval extends Field[Double]
}

class DaysTicker extends Ticker {
    object days extends Field[List[Int]]
}

class MonthsTicker extends Ticker {
    object months extends Field[List[Int]]
}

class YearsTicker extends Ticker

class BasicTicker extends Ticker

class LogTicker extends Ticker

class CategoricalTicker extends Ticker

class DatetimeTicker extends Ticker

abstract class TickFormatter extends PlotObject

class BasicTickFormatter extends TickFormatter {
    // TODO: object precision extends Field[Either[Auto, Int]]
    object use_scientific extends Field[Boolean](true)
    object power_limit_high extends Field[Int](5)
    object power_limit_low extends Field[Int](-3)
}

class LogTickFormatter extends TickFormatter

class CategoricalTickFormatter extends TickFormatter

class DatetimeTickFormatter extends TickFormatter {
    object formats extends Field[Map[DatetimeUnits, List[String]]]
}
