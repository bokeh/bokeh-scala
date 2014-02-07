package org.continuumio.bokeh

abstract class Renderer

class Glyph extends Renderer {
    object data_source extends Field[this.type, DataSource](this)
    object xdata_range extends Field[this.type, PlotRange](this)
    object ydata_range extends Field[this.type, PlotRange](this)

    // object units extends EnumField[Units]

    object glyph extends Field[this.type, BaseGlyph](this) // has_ref = False
    // object nonselection_glyph extends Instance()
    // object selection_glyph extends Instance()
}
