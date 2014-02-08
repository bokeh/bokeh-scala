package org.continuumio.bokeh

abstract class Renderer

class Glyph extends Renderer {
    object data_source extends Field[this.type, DataSource](this)
    object xdata_range extends Field[this.type, Range](this)
    object ydata_range extends Field[this.type, Range](this)

    object units extends Field[this.type, Units](this)

    // XXX: has_ref = False
    object glyph extends Field[this.type, BaseGlyph](this)
    object selection_glyph extends Field[this.type, BaseGlyph](this)
    object nonselection_glyph extends Field[this.type, BaseGlyph](this)
}
