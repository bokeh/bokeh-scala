package io.continuum.bokeh

@model abstract class GuideRenderer extends Renderer {
    object plot extends Field[Plot]
    object bounds extends Field[(Double, Double)] // TODO: Either[Auto, (Float, Float)]]

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")
}

@model abstract class Axis extends GuideRenderer {
    object location extends Field[Location]

    def defaultTicker: Ticker
    def defaultFormatter: TickFormatter

    object ticker extends Field[Ticker](defaultTicker)
    object formatter extends Field[TickFormatter](defaultFormatter)

    object axis_label extends Field[String]
    object axis_label_standoff extends Field[Int]
    // TODO: object axis_label_props extends Include(TextProps, prefix="axis_label")

    object major_label_standoff extends Field[Int]
    object major_label_orientation extends Field[Orientation] // TODO: Either[Orientation, Double]
    // TODO: object major_label_props extends Include(TextProps, prefix="major_label")

    // TODO: object axis_props extends Include(LineProps, prefix="axis")
    // TODO: object tick_props extends Include(LineProps, prefix="major_tick")

    object major_tick_in extends Field[Int]
    object major_tick_out extends Field[Int]
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

@model class Grid extends GuideRenderer {
    object dimension extends Field[Int](0)
    object ticker extends Field[Ticker]

    def axis(axis: Axis): SelfType = {
        axis.ticker.valueOpt.foreach(this.ticker := _)
        this
    }

    // TODO: object grid_props extends Include(LineProps, prefix="grid")
}
