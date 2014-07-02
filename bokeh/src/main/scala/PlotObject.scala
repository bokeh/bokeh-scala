package org.continuumio.bokeh

case class Ref(id: String, `type`: String)

trait Renderable {
    def scripts: List[xml.Node] = Nil
    def styles: List[xml.Node] = Nil
}

abstract class PlotObject extends HasFields with Renderable {
    def getRef: Ref = Ref(id.value, typeName)

    object id extends Field[String](Utils.uuid4())
}
