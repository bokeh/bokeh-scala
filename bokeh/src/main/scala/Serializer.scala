package io.continuum.bokeh

import play.api.libs.json.{Json,JsValue,JsArray,JsObject,JsNull}

trait Serializer {
    case class Model(id: String, `type`: String, attributes: Map[String, Any], doc: Option[String]) {
        def toJson: JsValue =
            Json.obj("id" -> Json.toJson(id),
                     "type" -> Json.toJson(`type`),
                     "attributes" -> anyToJson(attributes),
                     "doc" -> Json.toJson(doc))
    }

    def anyToJson(obj: Any): JsValue = {
        import Json.toJson
        import Formats._
        obj match {
            case obj: Boolean => toJson(obj)
            case obj: Int => toJson(obj)
            case obj: Double => toJson(obj)
            case obj: String => toJson(obj)
            case obj: Symbol => toJson(obj)
            case obj: Ref => toJson(obj)
            case obj: Color => toJson(obj)
            case obj: Percent => toJson(obj)
            case obj: EnumType => toJson(obj)
            case obj: org.joda.time.DateTime => toJson(obj)
            case obj: org.joda.time.LocalTime => toJson(obj)
            case obj: org.joda.time.LocalDate => toJson(obj)
            case obj: breeze.linalg.DenseVector[Double] => toJson(obj)
            case obj: Seq[_] => JsArray(obj.map(anyToJson))
            case obj: Array[_] => JsArray(obj.map(anyToJson))
            case obj: Map[_, _] =>
                JsObject(obj.toList.map {
                    case (key: String,   value) => (key,      anyToJson(value))
                    case (key: Symbol,   value) => (key.name, anyToJson(value))
                    case (key: EnumType, value) => (key.name, anyToJson(value))
                    case _ => throw new IllegalArgumentException(s"$obj of type <${obj.getClass}>")
                })
            case (obj1, obj2) => JsArray(List(anyToJson(obj1), anyToJson(obj2)))
            case (obj1, obj2, obj3) => JsArray(List(anyToJson(obj1), anyToJson(obj2), anyToJson(obj2)))
            case Some(obj) => anyToJson(obj)
            case None => JsNull
            case obj: PlotObject => toJson(obj.getRef)
            case obj: HasFields => anyToJson(allFieldsWithValues(obj))
            case _ => throw new IllegalArgumentException(s"$obj of type <${obj.getClass}>")
        }
    }

    def allFieldsWithValues(obj: HasFields): Map[String, Any] =
        ("type", obj.typeName) :: obj.dirtyFieldsWithValues toMap

    def serializeObjs(objs: List[PlotObject]): String = {
        val models = objs.map(getModel).map(_.toJson)
        Json.stringify(JsArray(models))
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
            allFieldsWithValues(obj).foreach {
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
                case _ =>
            }
        }

        descend(obj)
    }

    def getModel(obj: PlotObject): Model = {
        val Ref(id, tp) = obj.getRef
        Model(id, tp, allFieldsWithValues(obj), None)
    }
}
