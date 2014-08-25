package io.continuum.bokeh

case class Ref(id: String, `type`: String)

@fields abstract class PlotObject extends HasFields with Refs[Ref] {
    def getRef: Ref = Ref(id.value, typeName)
    object id extends Field[String](IdGenerator.next())
}
