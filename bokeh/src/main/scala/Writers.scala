package io.continuum.bokeh

import scala.reflect.ClassTag

import shapeless.{HList,Poly1}
import shapeless.ops.hlist.Mapper

import play.api.libs.json.{Json,Writes,JsValue,JsString,JsArray}
import breeze.linalg.DenseVector

trait HListFormats {
    object poly_writer extends Poly1 {
        implicit def default[A:Writes] = at[A](a => implicitly[Writes[A]].writes(a))
    }

    implicit def HListJSON[T <: HList](implicit map: Mapper[poly_writer.type, T]) = new Writes[T] {
        def writes(value: T) = {
            JsArray(map(value).toList.asInstanceOf[List[JsValue]])
        }
    }
}

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

trait Formats extends HListFormats with TupleFormats {
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

    implicit def EnumJSON[T <: core.EnumType] = new Writes[T] {
        def writes(value: T) = value.toJson
    }

    implicit val RefJSON = Json.writes[Ref]
}

object Formats extends Formats
