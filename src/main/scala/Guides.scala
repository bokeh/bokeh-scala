package org.continuumio.bokeh

abstract class GuideRenderer extends Renderer {
    object dimension extends Field[this.type, Int](this, 0)
}

class LinearAxis extends GuideRenderer {
    // object type extends String("linear_axis")

    object location extends Field[this.type, Either[Location, Float]](this)
    // object bounds extends String('auto')

    // object axis_label extends String
    // object axis_label_standoff extends Int
    // object axis_label_props extends Include(TextProps, prefix="axis_label")

    // object major_label_standoff extends Int
    // object major_label_orientation extends Either(Enum[Orientation], Int)
    // object major_label_props extends Include(TextProps, prefix="major_label")

    // object axis_props extends Include(LineProps, prefix="axis")
    // object tick_props extends Include(LineProps, prefix="major_tick")

    object major_tick_in extends Field[this.type, Int](this)
    object major_tick_out extends Field[this.type, Int](this)
}

class DatetimeAxis extends LinearAxis {
    // object type extends String("datetime_axis")
    // object axis_label extends String("date")
    // object scale extends String("time")
    // object num_labels extends Int(8)
    // object char_width extends Int(10)
    // object fill_ratio extends Float(0.3)
    // object formats extends Dict({"days": ["%m/%d/%Y"]})
}

class Grid extends GuideRenderer {
    // object type extends String("grid")
    // object bounds extends String('auto')
    // object is_datetime extends Bool(False)
    // object grid_props extends Include(LineProps, prefix="grid")
}
