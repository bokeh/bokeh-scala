package io.continuum.bokeh

@model abstract class Renderer extends Model

@model abstract class DataRenderer extends Renderer

@model class TileRenderer extends DataRenderer {
    object tile_source extends Field[TileSource](() => new WMTSTileSource())

    object alpha extends Field[Percent](1.0)

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")

    object level extends Field[RenderLevel](RenderLevel.Underlay)

    object render_parents extends Field[Boolean](true)
}

@model class DynamicImageRenderer extends DataRenderer {
    object image_source extends Field[ImageSource]

    object alpha extends Field[Percent](1.0)

    object level extends Field[RenderLevel](RenderLevel.Underlay)
    object render_parents extends Field[Boolean](true)
}

@model class GlyphRenderer extends Renderer {
    // TODO: object server_data_source extends Field[ServerDataSource]
    object data_source extends Field[DataSource](() => new ColumnDataSource())

    object glyph extends Field[Glyph[_, _]]
    object hover_glyph extends Field[Glyph[_, _]]
    object selection_glyph extends Field[Glyph[_, _]]
    object nonselection_glyph extends Field[Glyph[_, _]]

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")

    object level extends Field[RenderLevel](RenderLevel.Glyph)
}

@model abstract class GuideRenderer extends Renderer with BackRef {
    object bounds extends Field[(Double, Double)] // TODO: Either[Auto, (Float, Float)]]

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")
}
