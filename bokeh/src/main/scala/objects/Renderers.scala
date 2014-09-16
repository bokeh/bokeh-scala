package io.continuum.bokeh

@fields abstract class Renderer extends PlotObject

@fields class Glyph extends Renderer {
    // TODO: object server_data_source extends Field[ServerDataSource]
    object data_source extends Field[DataSource]

    object glyph extends Field[BaseGlyph] {
        override val fieldName = Some("glyphspec")
    }
    object selection_glyph extends Field[BaseGlyph] {
        override val fieldName = Some("selection_glyphspec")
    }
    object nonselection_glyph extends Field[BaseGlyph] {
        override val fieldName = Some("nonselection_glyphspec")
    }

    object x_range_name extends Field[String]("default")
    object y_range_name extends Field[String]("default")
}

@fields class Legend extends Renderer {
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

@fields class BoxSelectionOverlay extends Renderer {
    override val typeName = "BoxSelection"

    object tool extends Field[BoxSelectTool]
}
