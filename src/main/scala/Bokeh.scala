package org.continuumio.bokeh

trait NoRefs { self: PlotObject => }

case class Ref(id: String, `type`: String)

abstract class PlotObject extends HasFields {
    def getRef = Ref(id.value, viewModel)

    def viewModel: String = getClass.getSimpleName

    object id extends Field[String](uuid4())
}

class PlotContext extends PlotObject {
    object children extends Field[List[Plot]]
}
