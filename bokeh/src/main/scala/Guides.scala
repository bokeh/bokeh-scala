package org.continuumio.bokeh

abstract class GuideRenderer extends Renderer {
    object plot extends Field[Plot]
    object dimension extends Field[Int](0)
    // object bounds extends Field[Either[Auto, (Float, Float)]]
}

abstract class Axis extends GuideRenderer {
    object location extends Field[Location] // Either[Location, Float]

    object ticker extends Field[Ticker]
    object formatter extends Field[TickFormatter]

    object axis_label extends Field[String]
    object axis_label_standoff extends Field[Int]
    //// object axis_label_props extends Include(TextProps, prefix="axis_label")

    object major_label_standoff extends Field[Int]
    object major_label_orientation extends Field[Orientation] // Either[Orientation, Double]
    //// object major_label_props extends Include(TextProps, prefix="major_label")

    //// object axis_props extends Include(LineProps, prefix="axis")
    //// object tick_props extends Include(LineProps, prefix="major_tick")

    object major_tick_in extends Field[Int]
    object major_tick_out extends Field[Int]
}

class LinearAxis extends Axis

class CategoricalAxis extends Axis

class DatetimeAxis extends LinearAxis {
    object scale extends Field[String]("time")
    object num_labels extends Field[Int](8)
    object char_width extends Field[Int](10)
    object fill_ratio extends Field[Double](0.3)
}

class Grid extends GuideRenderer {
    object axis extends Field[Axis]
    //// object grid_props extends Include(LineProps, prefix="grid")
}
