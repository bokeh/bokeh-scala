package org.continuumio.bokeh

abstract class PlotObject

abstract class PlotRange extends PlotObject

class Range1d extends PlotRange {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
}

abstract class DataRange extends PlotRange

class DataRange1d extends DataRange {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
    //object sources extends Field[List[ColumnsRef]]
    object rangepadding extends Field[this.type, Double](this, 0.1)
}

abstract class Renderer

class Glyph extends Renderer {
    object data_source extends Field[this.type, DataSource](this)
    object xdata_range extends Field[this.type, PlotRange](this)
    object ydata_range extends Field[this.type, PlotRange](this)

    // object units extends EnumField[Units]

    object glyph extends Field[this.type, BaseGlyph](this) // has_ref = False
    // object nonselection_glyph extends Instance()
    // object selection_glyph extends Instance()
}

class Plot extends PlotObject {
    object data_sources extends Field[this.type, List[DataSource]](this)
    object title extends Field[this.type, String](this, "Bokeh Plot")

    object x_range extends Field[this.type, PlotRange](this)
    object y_range extends Field[this.type, PlotRange](this)

    //// object outline_props extends Include(LineProps, prefix="outline")

    object renderers extends Field[this.type, List[Renderer]](this)
    object tools extends Field[this.type, List[Tool]](this)

    object height extends Field[this.type, Int](this, 600)
    object width extends Field[this.type, Int](this, 600)

    // object background_fill extends Field[Color]("white")
    // object border_fill extends Field[Color]("white")
    object canvas_width extends Field[this.type, Int](this, 400)
    object canvas_height extends Field[this.type, Int](this, 400)
    object outer_width extends Field[this.type, Int](this, 400)
    object outer_height extends Field[this.type, Int](this, 400)
    object min_border_top extends Field[this.type, Int](this, 50)
    object min_border_bottom extends Field[this.type, Int](this, 50)
    object min_border_left extends Field[this.type, Int](this, 50)
    object min_border_right extends Field[this.type, Int](this, 50)
    object min_border extends Field[this.type, Int](this, 50)
    object script_inject_snippet extends Field[this.type, String](this)
}

class GridPlot extends Plot {
    object children extends Field[GridPlot, List[List[Plot]]](this)
    object border_space extends Field[GridPlot, Int](this, 0)
}
