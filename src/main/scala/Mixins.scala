package org.continuumio.bokeh

trait FillProps { self: PlotObject =>
    // object fill_color extends ColorSpec(Color.Gray)
    object fill_alpha extends Field[this.type, Percent](this, 100%%)
}

trait LineProps { self: PlotObject =>
    // object line_color extends ColorSpec(Color.Black)
    object line_width extends Field[this.type, Double](this, 1) // Size
    object line_alpha extends Field[this.type, Percent](this, 100%%)
    object line_join extends Field[this.type, LineJoin](this)
    object line_cap extends Field[this.type, LineCap](this)
    object line_dash extends Field[this.type, LineDash](this)
    object line_dash_offset extends Field[this.type, Int](this, 0)
}

trait TextProps { self: PlotObject =>
    object text_font extends Field[this.type, String](this)
    object text_font_size extends Field[this.type, String](this, "10pt")
    object text_font_style extends Field[this.type, FontStyle](this)
    object text_color extends Field[this.type, Color](this, Color.Black)
    object text_alpha extends Field[this.type, Percent](this, 100%%)
    object text_align extends Field[this.type, TextAlign](this)
    object text_baseline extends Field[this.type, Baseline](this)
}
