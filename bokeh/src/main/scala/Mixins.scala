package io.continuum.bokeh

trait FillProps { self: HasFields =>
    object fill_color extends Vectorized[Color](Color.Gray)
    object fill_alpha extends Field[Percent]
}

trait LineProps { self: HasFields =>
    object line_color extends Vectorized[Color](Color.Black)
    object line_width extends Field[Double](1.0)
    object line_alpha extends Field[Percent]
    object line_join extends Field[LineJoin]
    object line_cap extends Field[LineCap]
    object line_dash extends Field[LineDash]
    object line_dash_offset extends Field[Int]
}

trait TextProps { self: HasFields =>
    object text_font extends Field[String]
    object text_font_size extends Field[FontSize](10 pt)
    object text_font_style extends Field[FontStyle]
    object text_color extends Field[Color](Color.Black)
    object text_alpha extends Field[Percent]
    object text_align extends Field[TextAlign]
    object text_baseline extends Field[TextBaseline]
}
