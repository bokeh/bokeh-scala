package org.continuumio.bokeh

abstract class PlotObject

class PlotContext extends PlotObject {
    object children extends Field[this.type, List[List[Plot]]](this)
}
