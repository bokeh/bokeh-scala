package io.continuum.bokeh

trait FillProps { self: HasFields with Vectorization =>
    object fill_color extends Vectorized[Color](Color.Gray)
    object fill_alpha extends Vectorized[Percent]
}

trait LineProps { self: HasFields with Vectorization =>
    object line_color extends Vectorized[Color](Color.Black)
    object line_width extends Vectorized[Double](1.0)
    object line_alpha extends Vectorized[Percent]
    object line_join extends Vectorized[LineJoin]
    object line_cap extends Vectorized[LineCap]
    object line_dash extends Vectorized[List[Int]]
    object line_dash_offset extends Vectorized[Int]
}

trait TextProps { self: HasFields with Vectorization =>
    object text_font extends Vectorized[String]
    object text_font_size extends Vectorized[FontSize](10 pt)
    object text_font_style extends Vectorized[FontStyle]
    object text_color extends Vectorized[Color](Color.Black)
    object text_alpha extends Vectorized[Percent]
    object text_align extends Vectorized[TextAlign]
    object text_baseline extends Vectorized[TextBaseline]
}
