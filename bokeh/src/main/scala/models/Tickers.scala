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
    object mantissas extends Field[List[Double]](List(2.0, 5.0, 10.0))
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
