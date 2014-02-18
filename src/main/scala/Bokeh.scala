package org.continuumio.bokeh

trait NoRefs { self: PlotObject => }

case class Ref(id: String, `type`: String)

trait Renderable {
    def scripts: List[xml.Node] = Nil
    def styles: List[xml.Node] = Nil
}

abstract class PlotObject extends HasFields with Renderable {
    def viewModel: String = getClass.getSimpleName
    def getRef: Ref = Ref(id.value, viewModel)

    object id extends Field[String](Utils.uuid4())
}

class PlotContext extends PlotObject {
    object children extends Field[List[Plot]]
}
