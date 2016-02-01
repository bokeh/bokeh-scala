package io.continuum.bokeh

trait ByReference { self: HasFields =>
    type RefType

    def getRef: RefType
    def id: AbstractField { type ValueType = String }
}

case class Ref(id: String, `type`: String)

@model abstract class Model extends HasFields with ByReference {
    type RefType = Ref

    def getRef = Ref(id.value, typeName)
    object id extends Field[String](IdGenerator.next())

    def collect(): List[Model] = Model.collect(this :: Nil)
}

object Model {
    def collect(objs: List[Model]): List[Model] = {
        new Traversal().collect(objs)
    }
}

@model abstract class CustomModel extends Model {
    val implementation: ModelImplementation
}

trait ModelImplementation {
    val source: String
}

case class JavaScript(source: String) extends ModelImplementation
case class CoffeeScript(source: String) extends ModelImplementation

// TODO: source: File
