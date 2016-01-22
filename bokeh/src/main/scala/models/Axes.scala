package io.continuum.bokeh

@model abstract class Axis extends GuideRenderer {
    object visible extends Field[Boolean](true)
    object location extends Field[Location]

    def defaultTicker: Ticker
    def defaultFormatter: TickFormatter

    object ticker extends Field[Ticker](defaultTicker)
    object formatter extends Field[TickFormatter](defaultFormatter)

    object axis_label extends Field[String]
    object axis_label_standoff extends Field[Int]
    axis_label = include[TextProps]

    object major_label_standoff extends Field[Int]
    object major_label_orientation extends Field[Orientation] // TODO: Either[Orientation, Double]
    major_label = include[TextProps]

    axis = include[LineProps]

    major_tick = include[LineProps]
    object major_tick_in extends Field[Int]
    object major_tick_out extends Field[Int]

    minor_tick = include[LineProps]
    object minor_tick_in extends Field[Int]
    object minor_tick_out extends Field[Int]
}

@model abstract class ContinuousAxis extends Axis

@model class LinearAxis extends ContinuousAxis {
    def defaultTicker: Ticker = new BasicTicker()
    def defaultFormatter: TickFormatter = new BasicTickFormatter()
}

@model class LogAxis extends ContinuousAxis {
    def defaultTicker: Ticker = new LogTicker().num_minor_ticks(10)
    def defaultFormatter: TickFormatter = new LogTickFormatter()
}

@model class CategoricalAxis extends Axis {
    def defaultTicker: Ticker = new CategoricalTicker()
    def defaultFormatter: TickFormatter = new CategoricalTickFormatter()
}

@model class DatetimeAxis extends LinearAxis {
    override def defaultTicker: Ticker = new DatetimeTicker()
    override def defaultFormatter: TickFormatter = new DatetimeTickFormatter()

    object scale extends Field[String]("time")
    object num_labels extends Field[Int](8)
    object char_width extends Field[Int](10)
    object fill_ratio extends Field[Double](0.3)
}
