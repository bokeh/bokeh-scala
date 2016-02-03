package io.continuum.bokeh

class Traversal {
    def collect(objs: List[Model]): List[Model] = {
        val refs = collection.mutable.ListBuffer[Model]()
        traverse(objs, { case ref: Model => refs += ref })
        refs.toList
    }

    def traverse(objs: List[Model], fn: PartialFunction[Model, Unit]) {
        val visited = collection.mutable.HashSet[String]()

        def descendFields(obj: HasFields) {
            obj.fields.map(_.field.valueOpt).foreach(_.foreach(descend _))
        }

        def descend(obj: Any) {
            obj match {
                case obj: Model =>
                    if (!visited.contains(obj.id)) {
                        visited += obj.id
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
