package io.continuum.bokeh

@model abstract class Renderer extends PlotObject

@model class GlyphRenderer extends Renderer {
    // TODO: object server_data_source extends Field[ServerDataSource]
    object data_source extends Field[DataSource]

    object glyph extends Field[Glyph]
    object selection_glyph extends Field[Glyph]
    object nonselection_glyph extends Field[Glyph]

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")
}

@model class Legend extends Renderer {
    object plot extends Field[Plot]

    object orientation extends Field[LegendOrientation]
    border = include[LineProps]

    object label_standoff extends Field[Int](15)
    object label_height extends Field[Int](20)
    object label_width extends Field[Int](50)
    label = include[TextProps]

    object glyph_height extends Field[Int](20)
    object glyph_width extends Field[Int](20)

    object legend_padding extends Field[Int](10)
    object legend_spacing extends Field[Int](3)

    object legends extends Field[List[(String, List[GlyphRenderer])]]
}

@model class BoxSelectionOverlay extends Renderer {
    override val typeName = "BoxSelection"

    object tool extends Field[BoxSelectTool]
}
