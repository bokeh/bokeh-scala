package io.continuum.bokeh

import scala.reflect.ClassTag
import scala.annotation.implicitNotFound
import play.api.libs.json.JsArray

@implicitNotFound(msg="Can't find Default type class for type ${T}.")
class Default[T](val default: T)

trait TypeDefaults {
    implicit object BooleanDefault extends Default[Boolean](false)
    implicit object IntDefault extends Default[Int](0)
    implicit object DoubleDefault extends Default[Double](0.0)
    implicit object StringDefault extends Default[String]("")
    implicit object SymbolDefault extends Default[Symbol](Symbol(""))
    implicit object PercentDefault extends Default[Percent](100%%)

    implicit def OptionDefault[T]: Default[Option[T]] = new Default[Option[T]](None)
    implicit def SeqDefault[T]: Default[Seq[T]] = new Default[Seq[T]](Seq())
    implicit def ListDefault[T]: Default[List[T]] = new Default[List[T]](Nil)
    implicit def ArrayDefault[T:ClassTag]: Default[Array[T]] = new Default[Array[T]](Array[T]())
    implicit def MapDefault[U, V]: Default[Map[U, V]] = new Default[Map[U, V]](Map.empty)
    implicit def HasFieldsDefault[T <: HasFields]: Default[T] = new Default[T](null.asInstanceOf[T])

    implicit def Tuple2Default[T1:Default, T2:Default]: Default[(T1, T2)] =
        new Default[(T1, T2)]((implicitly[Default[T1]].default,
                               implicitly[Default[T2]].default))
    implicit def Tuple3Default[T1:Default, T2:Default, T3:Default]: Default[(T1, T2, T3)] =
        new Default[(T1, T2, T3)]((implicitly[Default[T1]].default,
                                   implicitly[Default[T2]].default,
                                   implicitly[Default[T3]].default))

    implicit object FontSizeDefault extends Default[FontSize](10 pt)
    implicit object TooltipDefault extends Default[Tooltip](TabularTooltip(Nil))
    implicit object SelectedDefault extends Default[Selected](Selected())

    implicit object JsArrayDefault extends Default[JsArray](JsArray(Nil))
}

trait EnumDefaults {
    implicit object LineJoinDefault extends Default[LineJoin](LineJoin.Miter)
    implicit object LineCapDefault extends Default[LineCap](LineCap.Butt)
    implicit object FontStyleDefault extends Default[FontStyle](FontStyle.Normal)
    implicit object FontUnitsDefault extends Default[FontUnits](FontUnits.PT)
    implicit object TextAlignDefault extends Default[TextAlign](TextAlign.Left)
    implicit object TextBaselineDefault extends Default[TextBaseline](TextBaseline.Top)
    implicit object DirectionDefault extends Default[Direction](Direction.Clock)
    implicit object SpatialUnitsDefault extends Default[SpatialUnits](SpatialUnits.Data)
    implicit object AngularUnitsDefault extends Default[AngularUnits](AngularUnits.Rad)
    implicit object DatetimeUnitsDefault extends Default[DatetimeUnits](DatetimeUnits.Seconds)
    implicit object DimensionDefault extends Default[Dimension](Dimension.Width)
    implicit object LocationDefault extends Default[Location](Location.Auto)
    implicit object OrientationDefault extends Default[Orientation](Orientation.Horizontal)
    implicit object BorderSymmetryDefault extends Default[BorderSymmetry](BorderSymmetry.Horizontal)
    implicit object LegendLocationDefault extends Default[LegendLocation](LegendLocation.TopRight)
    implicit object AnchorDefault extends Default[Anchor](Anchor.TopLeft)
    implicit object ColumnTypeDefault extends Default[ColumnType](ColumnType.String)
    implicit object ButtonTypeDefault extends Default[ButtonType](ButtonType.Default)
    implicit object MapTypeDefault extends Default[MapType](MapType.Satellite)
    implicit object FlipDefault extends Default[Flip](Flip.Horizontal)
    implicit object LogLevelDefault extends Default[LogLevel](LogLevel.Info)
    implicit object ColorDefault extends Default[Color](Color.White)
    implicit object NamedIconDefault extends Default[NamedIcon](NamedIcon.Adjust)
    implicit object CheckmarkDefault extends Default[Checkmark](Checkmark.Check)
    implicit object SortDefault extends Default[Sort](Sort.Ascending)
    implicit object LogoDefault extends Default[Logo](Logo.Normal)
    implicit object RoundingFunctionDefault extends Default[RoundingFunction](RoundingFunction.Round)
    implicit object NumeralLanguageDefault extends Default[NumeralLanguage](NumeralLanguage.`en`)
    implicit object HTTPMethodDefault extends Default[HTTPMethod](HTTPMethod.POST)
    implicit object RenderLevelDefault extends Default[RenderLevel](RenderLevel.Glyph)
    implicit object RenderModeDefault extends Default[RenderMode](RenderMode.Canvas)
    implicit object HoverModeDefault extends Default[HoverMode](HoverMode.Mouse)
    implicit object PointPolicyDefault extends Default[PointPolicy](PointPolicy.SnapToData)
    implicit object LinePolicyDefault extends Default[LinePolicy](LinePolicy.Prev)
    implicit object ScriptingLanguageDefault extends Default[ScriptingLanguage](ScriptingLanguage.JavaScript)
}

trait DefaultImplicits extends TypeDefaults with EnumDefaults
object Default extends DefaultImplicits
