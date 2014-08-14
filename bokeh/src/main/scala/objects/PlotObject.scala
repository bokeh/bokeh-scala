package io.continuum.bokeh

case class Ref(id: String, `type`: String)

abstract class PlotObject extends HasFields {
    def getRef: Ref = Ref(id.value, typeName)

    object id extends Field[String]
    id := Utils.uuid4()
}
