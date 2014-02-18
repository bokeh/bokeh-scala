package org.continuumio.bokeh

import scala.reflect.ClassTag

import play.api.libs.json.{Json,Reads,Writes,Format,JsString}
import breeze.linalg.DenseVector

import org.continuumio.bokeh.core.JsonImpl

object BokehJson {
    def writes[T]: Writes[T] = macro JsonImpl.writesImpl[T]
    def sealedWrites[T]: Writes[T] = macro JsonImpl.sealedWritesImpl[T]
}

trait Formats {
    implicit def DenseVectorJSON[T:Writes:ClassTag] = new Writes[DenseVector[T]] {
        def writes(vec: DenseVector[T]) =
            implicitly[Writes[Array[T]]].writes(vec.toArray)
    }

    implicit val PercentJSON = new Writes[Percent] {
        def writes(percent: Percent) =
            implicitly[Writes[Double]].writes(percent.value)
    }

    implicit val CSSColorJSON = new Writes[CSSColor] {
        def writes(color: CSSColor) = JsString(color.toCSS)
    }

    implicit val SymbolJSON = new Writes[Symbol] {
        def writes(symbol: Symbol) = JsString(symbol.name)
    }

    implicit def EnumJSON[T <: core.EnumType] = new Writes[T] {
        def writes(value: T) = value.toJson
    }
}

object Formats extends Formats
