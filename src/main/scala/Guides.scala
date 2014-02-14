package org.continuumio.bokeh

abstract class GuideRenderer extends Renderer {
    object plot extends Field[Plot]
    object dimension extends Field[Int](0)
}

abstract class Axis extends GuideRenderer {
    object location extends Field[Location] // Either[Location, Float]
    // object bounds extends String('auto')

    object axis_label extends Field[String]
    object axis_label_standoff extends Field[Int]
    //// object axis_label_props extends Include(TextProps, prefix="axis_label")

    object major_label_standoff extends Field[Int]
    object major_label_orientation extends Field[Orientation] // Either[Orientation, Int]
    //// object major_label_props extends Include(TextProps, prefix="major_label")

    //// object axis_props extends Include(LineProps, prefix="axis")
    //// object tick_props extends Include(LineProps, prefix="major_tick")

    object major_tick_in extends Field[Int]
    object major_tick_out extends Field[Int]
}

class LinearAxis extends Axis

class CategoricalAxis extends Axis

class DatetimeAxis extends LinearAxis {
    // object axis_label extends Field[String]("date")
    // object scale extends Field[String]("time")
    object num_labels extends Field[Int](8)
    object char_width extends Field[Int](10)
    object fill_ratio extends Field[Double](0.3)
    // object formats extends Dict({"days": ["%m/%d/%Y"]})
}

class Grid extends GuideRenderer {
    // object bounds extends String('auto')
    object is_datetime extends Field[Boolean](false)
    //// object grid_props extends Include(LineProps, prefix="grid")
}
