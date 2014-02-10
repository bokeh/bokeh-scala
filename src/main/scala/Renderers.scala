package org.continuumio.bokeh

abstract class Renderer extends PlotObject

class Glyph extends Renderer {
    object data_source extends Field[this.type, DataSource](this)
    object xdata_range extends Field[this.type, Range](this)
    object ydata_range extends Field[this.type, Range](this)

    object units extends Field[this.type, Units](this)

    // XXX: has_ref = False
    object glyph extends Field[this.type, BaseGlyph](this) {
        override val fieldName = Some("glyphspec")
    }
    object selection_glyph extends Field[this.type, BaseGlyph](this) {
        override val fieldName = Some("selection_glyphspec")
    }
    object nonselection_glyph extends Field[this.type, BaseGlyph](this) {
        override val fieldName = Some("nonselection_glyphspec")
    }
}
