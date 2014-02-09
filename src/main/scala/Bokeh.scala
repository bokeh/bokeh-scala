package org.continuumio.bokeh

abstract class PlotObject extends HasFields {
    object id extends Field[this.type, String](this, uuid4())
}

class PlotContext extends PlotObject {
    object children extends Field[this.type, List[List[Plot]]](this)
}
