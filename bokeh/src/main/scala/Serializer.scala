package io.continuum.bokeh

import play.api.libs.json.{Json,JsValue,JsArray,JsObject,JsString}

trait JSONSerializer {
    case class Model(id: String, `type`: String, attributes: JsObject, doc: Option[String]) {
        def toJson: JsValue =
            Json.obj("id" -> Json.toJson(id),
                     "type" -> Json.toJson(`type`),
                     "attributes" -> attributes,
                     "doc" -> Json.toJson(doc))
    }

    val stringifyFn: JsValue => String

    def stringify(obj: PlotObject): String = {
        serializeObjs(collectObjs(obj))
    }

    def serializeObjs(objs: List[PlotObject]): String = {
        val models = objs.map(getModel).map(_.toJson)
        stringifyFn(JsArray(models))
    }

    def collectObjs(obj: HasFields): List[PlotObject] = {
        val objs = collection.mutable.ListBuffer[PlotObject]()

        traverse(obj, obj => obj match {
            case _: PlotObject => objs += obj
            case _ =>
        })

        objs.toList
    }

    def traverse(obj: HasFields, fn: PlotObject => Unit) {
        val ids = collection.mutable.HashSet[String]()

        def descendFields(obj: HasFields) {
            obj.dirtyFieldsWithValues.foreach {
                case (_, Some(obj)) => descend(obj)
                case _ =>
            }
        }

        def descend(obj: Any) {
            obj match {
                case obj: PlotObject =>
                    if (!ids.contains(obj.id.value)) {
                        ids += obj.id.value
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

        descend(obj)
    }

    def getModel(obj: PlotObject): Model = {
        val Ref(id, tp) = obj.getRef
        Model(id, tp, obj.toJson + ("type" -> JsString(obj.typeName)), None)
    }
}
