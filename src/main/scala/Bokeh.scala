package org.continuumio.bokeh

case class Ref(id: String, `type`: String)

abstract class PlotObject extends HasFields {
    def getRef = Ref(id.value, viewModel)

    object id extends Field[this.type, String](this, uuid4())
}

class PlotContext extends PlotObject {
    object children extends Field[this.type, List[List[Plot]]](this)
}
