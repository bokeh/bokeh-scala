package io.continuum.bokeh

import scala.reflect.ClassTag

import play.api.libs.json.{Json,Writes,JsValue,JsString,JsNumber,JsArray,JsObject,JsNull}
import org.joda.time.{DateTime,LocalTime=>Time,LocalDate=>Date}
import breeze.linalg.DenseVector

trait ScalaWrites {
    implicit object ByteWrites extends Writes[Byte] {
        def writes(b: Byte) = JsNumber(b)
    }

    implicit object CharWrites extends Writes[Char] {
        def writes(c: Char) = Json.toJson(c.toString)
    }

    implicit val SymbolWrites = new Writes[Symbol] {
        def writes(symbol: Symbol) = Json.toJson(symbol.name)
    }

    implicit def StringMapWrites[V:Writes]: Writes[Map[String, V]] = new Writes[Map[String, V]] {
        def writes(obj: Map[String, V]) = {
            JsObject(obj.map { case (k, v) => (k, Json.toJson(v)) }.toSeq)
        }
    }

    implicit def SymbolMapWrites[V:Writes]: Writes[Map[Symbol, V]] = new Writes[Map[Symbol, V]] {
        def writes(obj: Map[Symbol, V]) = {
            JsObject(obj.map { case (k, v) => (k.name, Json.toJson(v)) }.toSeq)
        }
    }

    implicit def Tuple2Writes[T1:Writes, T2:Writes]: Writes[(T1, T2)] = new Writes[(T1, T2)] {
        def writes(t: (T1, T2)) = JsArray(List(Json.toJson(t._1),
                                               Json.toJson(t._2)))
    }

    implicit def Tuple3Writes[T1:Writes, T2:Writes, T3:Writes]: Writes[(T1, T2, T3)] = new Writes[(T1, T2, T3)] {
        def writes(t: (T1, T2, T3)) = JsArray(List(Json.toJson(t._1),
                                                   Json.toJson(t._2),
                                                   Json.toJson(t._3)))
    }
}

trait ThirdpartyWrites {
    implicit def DenseVectorWrites[T:Writes:ClassTag] = new Writes[DenseVector[T]] {
        def writes(vec: DenseVector[T]) = Json.toJson(vec.toArray)
    }

    implicit val DateTimeJSON = new Writes[DateTime] {
        def writes(datetime: DateTime) = Json.toJson(datetime.getMillis)
    }

    implicit val TimeJSON = new Writes[Time] {
        def writes(time: Time) = Json.toJson(time.getMillisOfDay)
    }

    implicit val DateJSON = new Writes[Date] {
        def writes(date: Date) = Json.toJson(date.toDateTimeAtStartOfDay)
    }
}

trait BokehWrites {
    implicit val PercentWrites = new Writes[Percent] {
        def writes(percent: Percent) = Json.toJson(percent.value)
    }

    implicit val ColorWrites = new Writes[Color] {
        def writes(color: Color) = Json.toJson(color.toCSS)
    }

    implicit val FontSizeWrites = new Writes[FontSize] {
        def writes(size: FontSize) = Json.toJson(size.toCSS)
    }

    implicit val TooltipWrites = new Writes[Tooltip] {
        def writes(tooltip: Tooltip) = tooltip match {
            case StringTooltip(string) => Json.toJson(string)
            case HTMLTooltip(html)     => Json.toJson(html.toString)
            case TabularTooltip(rows)  => Json.toJson(rows)
        }
    }

    implicit def EnumWrites[T <: EnumType] = new Writes[T] {
        def writes(value: T) = Json.toJson(value.name)
    }

    implicit object OrientationWrites extends Writes[Orientation] {
        def writes(value: Orientation) = value match {
            case Orientation.Angle(value) => Json.toJson(value)
            case _                        => implicitly[Writes[EnumType]].writes(value)
        }
    }

    implicit val Selected0dWrites = Json.writes[Selected0d]
    implicit val Selected1dWrites = Json.writes[Selected1d]
    implicit val Selected2dWrites = Json.writes[Selected2d]
    implicit val SelectedWrites = Json.writes[Selected]

    implicit val RefWrites = Json.writes[Ref]

    implicit object HasFieldsWrites extends Writes[HasFields] {
        def writes(obj: HasFields) = obj match {
            case obj: Model => Json.toJson(obj.getRef)
            case _          => obj.fieldsToJson(false)
        }
    }
}

trait Formats extends ScalaWrites with ThirdpartyWrites with BokehWrites
object Formats extends Formats
