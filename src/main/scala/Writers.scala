package org.continuumio.bokeh

import scala.reflect.ClassTag

import play.api.libs.json.{Json,Reads,Writes,Format,JsString}
import breeze.linalg.DenseVector

import org.continuumio.bokeh.macros.JsonImpl

object BokehJson {
    def writes[T]: Writes[T] = macro JsonImpl.writesImpl[T]
    def enum[T <: Enum]: Writes[T] = macro JsonImpl.enumImpl[T]
    def sealedWrites[T]: Writes[T] = macro JsonImpl.sealedWritesImpl[T]
}

trait DataFormats {
    implicit def DenseVectorJSON[T:Writes:ClassTag]: Writes[DenseVector[T]] = new Writes[DenseVector[T]] {
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
}

trait EnumFormats {
    implicit val LineJoinJSON = BokehJson.enum[LineJoin]
    implicit val LineDashJSON = BokehJson.enum[LineDash]
    implicit val LineCapJSON = BokehJson.enum[LineCap]
    implicit val FontStyleJSON = BokehJson.enum[FontStyle]
    implicit val TextAlignJSON = BokehJson.enum[TextAlign]
    implicit val BaselineJSON = BokehJson.enum[Baseline]
    implicit val DirectionJSON = BokehJson.enum[Direction]
    implicit val OrientationJSON = BokehJson.enum[Orientation]
    implicit val UnitsJSON = BokehJson.enum[Units]
    implicit val AngleUnitsJSON = BokehJson.enum[AngleUnits]
    implicit val DimensionJSON = BokehJson.enum[Dimension]
    implicit val LocationJSON = BokehJson.enum[Location]
    implicit val NamedColorJSON = BokehJson.enum[NamedColor]
}

object Formats extends DataFormats with EnumFormats
