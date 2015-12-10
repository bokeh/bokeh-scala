package io.continuum.bokeh

trait FillProps { self: HasFields with Vectorization =>
    object fill_color extends Vectorized[Color](Color.Gray)
    object fill_alpha extends Vectorized[Percent](1.0)
}

trait LineProps { self: HasFields with Vectorization =>
    object line_color extends Vectorized[Color](Color.Black)
    object line_width extends Vectorized[Double](1.0)
    object line_alpha extends Vectorized[Percent]
    object line_join extends Field[LineJoin]
    object line_cap extends Field[LineCap]
    object line_dash extends Field[List[Int]]
    object line_dash_offset extends Field[Int]
}

trait TextProps { self: HasFields with Vectorization =>
    object text_font extends Field[String]
    object text_font_size extends Vectorized[FontSize](12 pt)
    object text_font_style extends Field[FontStyle]
    object text_color extends Vectorized[Color]("#444444")
    object text_alpha extends Vectorized[Percent]
    object text_align extends Field[TextAlign]
    object text_baseline extends Field[TextBaseline]
}
