package org.continuumio.bokeh

class Plot extends PlotObject {
    object data_sources extends Field[this.type, List[DataSource]](this)
    object title extends Field[this.type, String](this, "Bokeh Plot")

    object x_range extends Field[this.type, Range](this)
    object y_range extends Field[this.type, Range](this)

    //// object outline_props extends Include(LineProps, prefix="outline")

    object renderers extends Field[this.type, List[Renderer]](this)
    object tools extends Field[this.type, List[Tool]](this)

    object height extends Field[this.type, Int](this, 600)
    object width extends Field[this.type, Int](this, 600)

    object background_fill extends Field[this.type, Color](this, Color.White)
    object border_fill extends Field[this.type, Color](this, Color.White)
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
    object children extends Field[this.type, List[List[Plot]]](this)
    object border_space extends Field[this.type, Int](this, 0)
}
