package org.continuumio.bokeh

abstract class PlotObject

class Range1d extends PlotObject {
    //start = Float
    //end = Float
}

class Glyph extends PlotObject {
    //plot = Instance(has_ref=True)
    //data_source = Instance(DataSource, has_ref=True)
    //xdata_range = Instance(DataRange1d, has_ref=True)
    //ydata_range = Instance(DataRange1d, has_ref=True)

    //units = Enum("screen", "data")

    //glyph = Instance()
    //nonselection_glyph = Instance()
    //selection_glyph = Instance()
}

class Plot extends PlotObject {
    //data_sources = List
    //title = String("Bokeh Plot")

    //x_range = Instance(DataRange1d, has_ref=True)
    //y_range = Instance(DataRange1d, has_ref=True)
    //png = String('')
    //title = String('')
    //outline_props = Include(LineProps, prefix="outline")

    //renderers = List(has_ref=True)
    //tools = List(has_ref=True)

    //height = Int(600)
    //width = Int(600)

    //background_fill = Color("white")
    //border_fill = Color("white")
    //canvas_width = Int(400)
    //canvas_height = Int(400)
    //outer_width = Int(400)
    //outer_height = Int(400)
    //min_border_top = Int(50)
    //min_border_bottom = Int(50)
    //min_border_left = Int(50)
    //min_border_right = Int(50)
    //min_border = Int(50)
    //script_inject_snippet = String("")
}

class GridPlot extends Plot {
    //children = List(List(has_ref=True), has_ref=True)
    //border_space = Int(0)
}
