package io.continuum.bokeh

@model abstract class Renderer extends PlotObject

@model class GlyphRenderer extends Renderer {
    // TODO: object server_data_source extends Field[ServerDataSource]
    object data_source extends Field[DataSource](new ColumnDataSource())

    object glyph extends Field[Glyph]
    object selection_glyph extends Field[Glyph]
    object nonselection_glyph extends Field[Glyph]

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")

    object level extends Field[RenderLevel](RenderLevel.Glyph)
}

@model class BoxSelectionOverlay extends Renderer {
    override val typeName = "BoxSelection"

    object tool extends Field[BoxSelectTool]
}
