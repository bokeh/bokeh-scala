package io.continuum.bokeh

import scala.reflect.ClassTag

import play.api.libs.json.{Json,Writes,JsValue,JsString,JsNumber,JsArray,JsObject}
import org.joda.time.{DateTime,LocalTime=>Time,LocalDate=>Date}
import breeze.linalg.DenseVector

trait TupleFormats {
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

trait DateTimeFormats {
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

trait Formats extends TupleFormats with DateTimeFormats {
    implicit def StringMapWrites[V:Writes]: Writes[Map[String, V]] = new Writes[Map[String, V]] {
        def writes(obj: Map[String, V]) =
            JsObject(obj.map { case (k, v) => (k, implicitly[Writes[V]].writes(v)) } toList)
    }

    implicit def SymbolMapWrites[V:Writes]: Writes[Map[Symbol, V]] = new Writes[Map[Symbol, V]] {
        def writes(obj: Map[Symbol, V]) =
            implicitly[Writes[Map[String, V]]].writes(obj.map { case (k, v) => (k.name, v) })
    }

    implicit def DenseVectorJSON[T:Writes:ClassTag] = new Writes[DenseVector[T]] {
        def writes(vec: DenseVector[T]) =
            implicitly[Writes[Array[T]]].writes(vec.toArray)
    }

    implicit val PercentJSON = new Writes[Percent] {
        def writes(percent: Percent) =
            implicitly[Writes[Double]].writes(percent.value)
    }

    implicit val ColorJSON = new Writes[Color] {
        def writes(color: Color) = JsString(color.toCSS)
    }

    implicit val SymbolJSON = new Writes[Symbol] {
        def writes(symbol: Symbol) = JsString(symbol.name)
    }

    implicit def EnumJSON[T <: EnumType] = new Writes[T] {
        def writes(value: T) = implicitly[Writes[String]].writes(value.name)
    }

    implicit val RefJSON = Json.writes[Ref]

    implicit val HasFieldsJSON = new Writes[HasFields] {
        def writes(obj: HasFields) = obj match {
            case (obj: PlotObject) => implicitly[Writes[Ref]].writes(obj.getRef)
            case _                 => obj.toJson
        }
    }
}

object Formats extends Formats
