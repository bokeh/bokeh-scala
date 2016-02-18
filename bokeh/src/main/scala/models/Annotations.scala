package io.continuum.bokeh

@model abstract class Annotation extends Renderer with BackRef {
    object level extends Field[RenderLevel](RenderLevel.Annotation)
}

@model class Legend extends Annotation {
    object location extends Field[LegendLocation]

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

    // TODO: line_props = Include(LineProps, use_prefix=False)
    // TODO: line_alpha = Override(default=0.3)
    // TODO: line_color = Override(default="#cccccc")

    // TODO: fill_props = Include(FillProps, use_prefix=False)
    // TODO: fill_alpha = Override(default=0.4)
    // TODO: fill_color = Override(default="#fff9ba")

    object render_mode extends Field[RenderMode](RenderMode.Canvas)
}

@model class PolyAnnotation extends Annotation {
    object xs extends Field[Seq[Double]](Seq.empty)
    object xs_units extends Field[SpatialUnits](SpatialUnits.Data)

    object ys extends Field[Seq[Double]](Seq.empty)
    object ys_units extends Field[SpatialUnits](SpatialUnits.Data)

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")

    // TODO: line_props = Include(LineProps, use_prefix=False)
    // TODO: line_alpha = Override(default=0.3)
    // TODO: line_color = Override(default="#cccccc")

    // TODO: fill_props = Include(FillProps, use_prefix=False)
    // TODO: fill_alpha = Override(default=0.4)
    // TODO: fill_color = Override(default="#fff9ba")
}

@model class Span extends Annotation {
    object location extends Field[Double]
    object location_units extends Field[SpatialUnits](SpatialUnits.Data)

    object dimension extends Field[Dimension](Dimension.Width)

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")

    object render_mode extends Field[RenderMode](RenderMode.Canvas)

    // TODO: line_props = Include(LineProps, use_prefix=False)
}

@model abstract class Overlay extends Annotation {
    // TODO: level = Override(default="overlay")
}

/*
@model class Tooltip extends Overlay {
    // TODO: side = Either(Auto, Enum(Side), default="auto")
    object inner_only extends Field[Boolean](true)
}
*/
