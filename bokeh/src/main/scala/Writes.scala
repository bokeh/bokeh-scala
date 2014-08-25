package io.continuum.bokeh

import scala.reflect.ClassTag

import play.api.libs.json.{Json,Writes,JsValue,JsString,JsNumber,JsArray,JsObject}
import org.joda.time.{DateTime,LocalTime=>Time,LocalDate=>Date}
import breeze.linalg.DenseVector

trait MapWrites {
    implicit def StringMapWrites[V:Writes]: Writes[Map[String, V]] = new Writes[Map[String, V]] {
        def writes(obj: Map[String, V]) =
            JsObject(obj.map { case (k, v) => (k, implicitly[Writes[V]].writes(v)) } toSeq)
    }

    implicit def EnumTypeMapWrites[E <: EnumType:Writes, V:Writes]: Writes[Map[E, V]] = new Writes[Map[E, V]] {
        def writes(obj: Map[E, V]) = {
            JsObject(obj.map { case (k, v) => (k.name, implicitly[Writes[V]].writes(v)) } toSeq)
        }
    }
}

trait TupleWrites {
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

trait DateTimeWrites {
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
    implicit def DenseVectorWrites[T:Writes:ClassTag] = new Writes[DenseVector[T]] {
        def writes(vec: DenseVector[T]) =
            implicitly[Writes[Array[T]]].writes(vec.toArray)
    }

    implicit val PercentWrites = new Writes[Percent] {
        def writes(percent: Percent) =
            implicitly[Writes[Double]].writes(percent.value)
    }

    implicit val ColorWrites = new Writes[Color] {
        def writes(color: Color) = JsString(color.toCSS)
    }

    implicit val SymbolWrites = new Writes[Symbol] {
        def writes(symbol: Symbol) = JsString(symbol.name)
    }

    implicit def EnumWrites[T <: EnumType] = new Writes[T] {
        def writes(value: T) = implicitly[Writes[String]].writes(value.name)
    }

    implicit val RefWrites = Json.writes[Ref]

    implicit def FieldWrites[T:Writes] = new Writes[AbstractField { type ValueType = T }] {
        def writes(obj: AbstractField { type ValueType = T }) =
            implicitly[Writes[Option[T]]].writes(obj.valueOpt)
    }

    implicit val HasFieldsWrites = new Writes[HasFields] {
        def writes(obj: HasFields) = obj match {
            case (obj: PlotObject) => implicitly[Writes[Ref]].writes(obj.getRef)
            case _                 => obj.toJson + ("type" -> JsString(obj.typeName))
        }
    }

    implicit val SymbolAnyMapWrites: Writes[Map[Symbol, Any]] = new Writes[Map[Symbol, Any]] {
        private def anyToJson(obj: Any): JsValue = obj match {
            case obj: Boolean        => Json.toJson(obj)
            case obj: Int            => Json.toJson(obj)
            case obj: Double         => Json.toJson(obj)
            case obj: String         => Json.toJson(obj)
            case obj: Color          => Json.toJson(obj)
            case obj: Percent        => Json.toJson(obj)
            case obj: EnumType       => Json.toJson(obj)
            case obj: DateTime       => Json.toJson(obj)
            case obj: Time           => Json.toJson(obj)
            case obj: Date           => Json.toJson(obj)
            case obj: Array[_]       => JsArray(obj.map(anyToJson).toSeq)
            case obj: Traversable[_] => JsArray(obj.map(anyToJson).toSeq)
            case obj: DenseVector[_] => JsArray(obj.iterator.map(_._2).map(anyToJson).toSeq)
            case _ => throw new IllegalArgumentException(s"$obj of type <${obj.getClass}>")
        }

        def writes(obj: Map[Symbol, Any]) =
            JsObject(obj.map { case (k, v) => (k.name, anyToJson(v)) } toList)
    }
}

trait Formats extends MapWrites with TupleWrites with DateTimeWrites with BokehWrites
object Formats extends Formats
