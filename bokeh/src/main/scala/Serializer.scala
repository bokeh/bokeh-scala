package io.continuum.bokeh

import play.api.libs.json.JsObject

case class ModelRepr(id: String, `type`: String, attributes: JsObject)

object JSONSerializer {
    def serialize(obj: Model): List[ModelRepr] = serialize(obj :: Nil)

    def serialize(objs: List[Model]): List[ModelRepr] = collect(objs).map(modelRepr)

    protected def modelRepr(obj: Model): ModelRepr = {
        val Ref(id, tp) = obj.getRef
        ModelRepr(id, tp, HasFieldsWrites.writeFields(obj))
    }

    protected def collect(objs: List[Model]): List[Model] = {
        val refs = collection.mutable.ListBuffer[Model]()
        traverse(objs, { case ref: Model => refs += ref })
        refs.toList
    }

    protected def traverse(objs: List[Model], fn: PartialFunction[Model, Unit]) {
        val visited = collection.mutable.HashSet[String]()

        def descendFields(obj: HasFields) {
            obj.fields.map(_.field.valueOpt).foreach(_.foreach(descend _))
        }

        def descend(obj: Any) {
            obj match {
                case obj: Model =>
                    if (!visited.contains(obj.id.value)) {
                        visited += obj.id.value
                        descendFields(obj)
                        fn(obj)
                    }
                case obj: HasFields =>
                    descendFields(obj)
                case obj: List[_] =>
                    obj.foreach(descend)
                case obj: Map[_, _] =>
                    obj.foreach { case (key, value) => descend(key) -> descend(value) }
                case obj: Product =>
                    obj.productIterator.foreach(descend)
                case _ =>
            }
        }

        descend(objs)
    }
}
