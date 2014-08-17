package io.continuum.bokeh

abstract class Renderer extends PlotObject

class Glyph extends Renderer {
    // TODO: object server_data_source extends Field[ServerDataSource]
    object data_source extends Field[DataSource]
    object xdata_range extends Field[Range]
    object ydata_range extends Field[Range]

    object units extends Field[SpatialUnits]

    object glyph extends Field[BaseGlyph] {
        override val fieldName = Some("glyphspec")
    }
    object selection_glyph extends Field[BaseGlyph] {
        override val fieldName = Some("selection_glyphspec")
    }
    object nonselection_glyph extends Field[BaseGlyph] {
        override val fieldName = Some("nonselection_glyphspec")
    }
}

class Legend extends Renderer {
    object plot extends Field[Plot]
    object orientation extends Field[LegendOrientation]
    // TODO: object border extends Include(LineProps, prefix="border")

    // TODO: object label_props extends Include(TextProps, prefix="label")
    object label_standoff extends Field[Int](15)
    object label_height extends Field[Int](20)
    object label_width extends Field[Int](50)

    object glyph_height extends Field[Int](20)
    object glyph_width extends Field[Int](20)

    object legend_padding extends Field[Int](10)
    object legend_spacing extends Field[Int](3)
    object legends extends Field[Map[String, List[Glyph]]]
}

class DataSlider extends Renderer {
    object plot extends Field[Plot]
    object data_source extends Field[DataSource]
    object field extends Field[String]
}

class BoxSelectionOverlay extends Renderer {
    override val typeName = "BoxSelection"

    object tool extends Field[BoxSelectTool]
}
