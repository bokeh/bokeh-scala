package org.continuumio.bokeh

abstract class GuideRenderer extends Renderer {
    object plot extends Field[this.type, Plot](this)
    object dimension extends Field[this.type, Int](this, 0)
}

class LinearAxis extends GuideRenderer {
    // object type extends String("linear_axis")

    object location extends Field[this.type, Location](this) // Either[Location, Float]
    // object bounds extends String('auto')

    object axis_label extends Field[this.type, String](this)
    object axis_label_standoff extends Field[this.type, Int](this)
    //// object axis_label_props extends Include(TextProps, prefix="axis_label")

    object major_label_standoff extends Field[this.type, Int](this)
    object major_label_orientation extends Field[this.type, Orientation](this) // Either[Orientation, Int]
    //// object major_label_props extends Include(TextProps, prefix="major_label")

    //// object axis_props extends Include(LineProps, prefix="axis")
    //// object tick_props extends Include(LineProps, prefix="major_tick")

    object major_tick_in extends Field[this.type, Int](this)
    object major_tick_out extends Field[this.type, Int](this)
}

class DatetimeAxis extends LinearAxis {
    // object type extends String("datetime_axis")
    // object axis_label extends Field[this.type, String](this, "date")
    // object scale extends Field[this.type, String](this, "time")
    object num_labels extends Field[this.type, Int](this, 8)
    object char_width extends Field[this.type, Int](this, 10)
    object fill_ratio extends Field[this.type, Double](this, 0.3)
    // object formats extends Dict({"days": ["%m/%d/%Y"]})
}

class Grid extends GuideRenderer {
    // object type extends String("grid")
    // object bounds extends String('auto')
    object is_datetime extends Field[this.type, Boolean](this, false)
    //// object grid_props extends Include(LineProps, prefix="grid")
}
