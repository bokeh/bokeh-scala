package org.continuumio.bokeh

abstract class Renderer extends PlotObject

class Glyph extends Renderer {
    object data_source extends Field[DataSource]
    object xdata_range extends Field[Range]
    object ydata_range extends Field[Range]

    object units extends Field[Units]

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

class BoxSelectionOverlay extends Renderer {
    override val viewModel = "BoxSelection"

    object tool extends Field[BoxSelectTool]
}
