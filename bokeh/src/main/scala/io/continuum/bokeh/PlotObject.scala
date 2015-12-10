package io.continuum.bokeh

trait ByReference { self: HasFields =>
    type RefType

    def getRef: RefType
    def id: AbstractField { type ValueType = String }
}

case class Ref(id: String, `type`: String)

@model abstract class PlotObject extends HasFields with ByReference {
    type RefType = Ref

    def getRef = Ref(id.value, typeName)
    object id extends Field[String](IdGenerator.next())
}
