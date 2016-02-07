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
        def writes(c: Char) = JsString(c.toString)
    }

    implicit val SymbolWrites = new Writes[Symbol] {
        def writes(symbol: Symbol) = JsString(symbol.name)
    }

    implicit def DictWrites[V:Writes]: Writes[Map[String, V]] = new Writes[Map[String, V]] {
        def writes(obj: Map[String, V]) = {
            JsObject(obj.map { case (k, v) => (k, Json.toJson(v)) }.toSeq)
        }
    }

    implicit def Tuple2Writes[T1:Writes, T2:Writes]: Writes[(T1, T2)] = new Writes[(T1, T2)] {
        def writes(t: (T1, T2)) = JsArray(List(implicitly[Writes[T1]].writes(t._1),
                                               implicitly[Writes[T2]].writes(t._2)))
    }

    implicit def Tuple3Writes[T1:Writes, T2:Writes, T3:Writes]: Writes[(T1, T2, T3)] = new Writes[(T1, T2, T3)] {
        def writes(t: (T1, T2, T3)) = JsArray(List(implicitly[Writes[T1]].writes(t._1),
                                                   implicitly[Writes[T2]].writes(t._2),
                                                   implicitly[Writes[T3]].writes(t._3)))
    }
}

trait ThirdpartyWrites {
    implicit def DenseVectorWrites[T:Writes:ClassTag] = new Writes[DenseVector[T]] {
        def writes(vec: DenseVector[T]) =
            implicitly[Writes[Array[T]]].writes(vec.toArray)
    }

    implicit val DateTimeJSON = new Writes[DateTime] {
        def writes(datetime: DateTime) = JsNumber(datetime.getMillis)
    }

    implicit val TimeJSON = new Writes[Time] {
        def writes(time: Time) = JsNumber(time.getMillisOfDay)
    }

    implicit val DateJSON = new Writes[Date] {
        def writes(date: Date) = implicitly[Writes[DateTime]].writes(date.toDateTimeAtStartOfDay)
    }
}

trait BokehWrites {
    implicit val PercentWrites = new Writes[Percent] {
        def writes(percent: Percent) =
            implicitly[Writes[Double]].writes(percent.value)
    }

    implicit val ColorWrites = new Writes[Color] {
        def writes(color: Color) = JsString(color.toCSS)
    }

    implicit val FontSizeWrites = new Writes[FontSize] {
        def writes(size: FontSize) = JsString(size.toCSS)
    }

    implicit val TooltipWrites = new Writes[Tooltip] {
        def writes(tooltip: Tooltip) = tooltip match {
            case StringTooltip(string) => Json.toJson(string)
            case HTMLTooltip(html)     => Json.toJson(html.toString)
            case TabularTooltip(rows)  => Json.toJson(rows)
        }
    }

    implicit def EnumWrites[T <: EnumType] = new Writes[T] {
        def writes(value: T) = implicitly[Writes[String]].writes(value.name)
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

    implicit object SymbolAnyMapWrites extends Writes[Map[Symbol, Any]] {
        private def seqToJson(obj: TraversableOnce[_]): JsValue = {
            JsArray(obj.toIterator.map(anyToJson).toSeq)
        }

        private def anyToJson(obj: Any): JsValue = obj match {
            case obj: Boolean            => Json.toJson(obj)
            case obj: Byte               => Json.toJson(obj)
            case obj: Short              => Json.toJson(obj)
            case obj: Int                => Json.toJson(obj)
            case obj: Long               => Json.toJson(obj)
            case obj: Float              => Json.toJson(obj)
            case obj: Double             => Json.toJson(obj)
            case obj: Char               => Json.toJson(obj)
            case obj: String             => Json.toJson(obj)
            case obj: Color              => Json.toJson(obj)
            case obj: Percent            => Json.toJson(obj)
            case obj: EnumType           => Json.toJson(obj)
            case obj: DateTime           => Json.toJson(obj)
            case obj: Time               => Json.toJson(obj)
            case obj: Date               => Json.toJson(obj)
            case obj: Option[_]          => obj.map(anyToJson) getOrElse JsNull
            case obj: Array[_]           => seqToJson(obj)
            case obj: TraversableOnce[_] => seqToJson(obj)
            case obj: DenseVector[_]     => seqToJson(obj.valuesIterator)
            case _ => throw new IllegalArgumentException(s"$obj of type <${obj.getClass}>")
        }

        def writes(obj: Map[Symbol, Any]) = {
            JsObject(obj.map { case (k, v) => (k.name, anyToJson(v)) } toList)
        }
    }
}

trait Formats extends ScalaWrites with ThirdpartyWrites with BokehWrites
object Formats extends Formats
