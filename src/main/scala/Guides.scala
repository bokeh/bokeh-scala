package org.continuumio.bokeh

abstract class GuideRenderer extends PlotObject

class LinearAxis extends GuideRenderer {
    //type = String("linear_axis")

    //dimension = Int(0)
    //location = Either(String('min'), Float)
    //bounds = String('auto')

    //axis_label = String
    //axis_label_standoff = Int
    //axis_label_props = Include(TextProps, prefix="axis_label")

    //major_label_standoff = Int
    //major_label_orientation = Either(Enum("horizontal", "vertical"), Int)
    //major_label_props = Include(TextProps, prefix="major_label")

    //axis_props = Include(LineProps, prefix="axis")
    //tick_props = Include(LineProps, prefix="major_tick")

    //major_tick_in = Int
    //major_tick_out = Int
}

class DatetimeAxis extends LinearAxis {
    //type = String("datetime_axis")
    //axis_label = String("date")
    //scale = String("time")
    //num_labels = Int(8)
    //char_width = Int(10)
    //fill_ratio = Float(0.3)
    //formats = Dict({"days": ["%m/%d/%Y"]})
}

class Grid extends GuideRenderer {
    //type = String("grid")

    //dimension = Int(0)
    //bounds = String('auto')

    //is_datetime = Bool(False)

    //grid_props = Include(LineProps, prefix="grid")
}
