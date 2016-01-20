package io.continuum.bokeh

import play.api.libs.json.{Json,JsValue,JsArray,JsObject,JsString}

class JSONSerializer(val stringifyFn: JsValue => String) {
    case class ModelRepr(id: String, `type`: String, attributes: JsObject, doc: Option[String] = None)

    implicit val ModelFormat = Json.format[ModelRepr]

    def getModelRepr(obj: PlotObject): ModelRepr = {
        val Ref(id, tp) = obj.getRef
        ModelRepr(id, tp, HasFieldsWrites.writeFields(obj))
    }

    def stringify(obj: PlotObject): String = {
        serializeObjs(collectObjs(obj))
    }

    def serializeObjs(objs: List[PlotObject]): String = {
        stringifyFn(Json.toJson(objs.map(getModelRepr)))
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
            obj.fields.map(_.field.valueOpt).foreach(_.foreach(descend _))
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
}
