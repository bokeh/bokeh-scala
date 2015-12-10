package io.continuum.bokeh

@model abstract class Annotation extends Renderer {
    object plot extends Field[Plot]
}

@model class Legend extends Annotation {
    object orientation extends Field[LegendOrientation]

    background = include[LineProps]
    border = include[LineProps]
    label = include[TextProps]

    object label_standoff extends Field[Int](15)
    object label_height extends Field[Int](20)
    object label_width extends Field[Int](50)

    object glyph_height extends Field[Int](20)
    object glyph_width extends Field[Int](20)

    object legend_padding extends Field[Int](10)
    object legend_spacing extends Field[Int](3)

    object legends extends Field[List[(String, List[GlyphRenderer])]]
}

@model class BoxAnnotation extends Annotation /*with LineProps with FillProps*/ {
    // TODO: object left extends Field[Either[Auto, NumberSpec("left")]]
    object left_units extends Field[SpatialUnits](SpatialUnits.Data)

    // TODO: object right extends Field[Either[Auto, NumberSpec("right")]]
    object right_units extends Field[SpatialUnits](SpatialUnits.Data)

    // TODO: object bottom extends Field[Either[Auto, NumberSpec("bottom")]]
    object bottom_units extends Field[SpatialUnits](SpatialUnits.Data)

    // TODO: object top extends Field[Either[Auto, NumberSpec("top")]]
    object top_units extends Field[SpatialUnits](SpatialUnits.Data)

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")

    object level extends Field[RenderLevel](RenderLevel.Annotation)
}
