package org.continuumio.bokeh

import scala.annotation.implicitNotFound
import java.util.Date

@implicitNotFound(msg="Can't find DefaultValue type class for type ${T}.")
class DefaultValue[T](val default: T)

trait TypeDefaults {
    implicit object BooleanDefault extends DefaultValue[Boolean](false)
    implicit object IntDefault extends DefaultValue[Int](0)
    implicit object DoubleDefault extends DefaultValue[Double](0.0)
    implicit object StringDefault extends DefaultValue[String]("")
    implicit object PercentDefault extends DefaultValue[Percent](100%%)
    implicit object DateDefault extends DefaultValue[Date](new Date)

    implicit def SeqDefault[T]: DefaultValue[Seq[T]] = new DefaultValue[Seq[T]](Seq())
    implicit def ListDefault[T]: DefaultValue[List[T]] = new DefaultValue[List[T]](Nil)
    implicit def MapDefault[U, V]: DefaultValue[Map[U, V]] = new DefaultValue[Map[U, V]](Map.empty)
    implicit def HasFieldsDefault[T <: HasFields]: DefaultValue[T] = new DefaultValue[T](null.asInstanceOf[T])

    implicit def Tuple2Default[T1:DefaultValue, T2:DefaultValue]: DefaultValue[(T1, T2)] =
        new DefaultValue[(T1, T2)]((implicitly[DefaultValue[T1]].default, implicitly[DefaultValue[T2]].default))
    implicit def Tuple3Default[T1:DefaultValue, T2:DefaultValue, T3:DefaultValue]: DefaultValue[(T1, T2, T3)] =
        new DefaultValue[(T1, T2, T3)]((implicitly[DefaultValue[T1]].default, implicitly[DefaultValue[T2]].default, implicitly[DefaultValue[T3]].default))
}

trait EnumDefaults {
    implicit object LineJoinDefault extends DefaultValue[LineJoin](LineJoin.Miter)
    implicit object LineDashDefault extends DefaultValue[LineDash](LineDash.Solid)
    implicit object LineCapDefault extends DefaultValue[LineCap](LineCap.Butt)
    implicit object FontStyleDefault extends DefaultValue[FontStyle](FontStyle.Normal)
    implicit object TextAlignDefault extends DefaultValue[TextAlign](TextAlign.Left)
    implicit object TextBaselineDefault extends DefaultValue[TextBaseline](TextBaseline.Top)
    implicit object DirectionDefault extends DefaultValue[Direction](Direction.Clock)
    implicit object UnitsDefault extends DefaultValue[Units](Units.Screen)
    implicit object AngleUnitsDefault extends DefaultValue[AngleUnits](AngleUnits.Deg)
    implicit object DatetimeUnitsDefault extends DefaultValue[DatetimeUnits](DatetimeUnits.Seconds)
    implicit object DimensionDefault extends DefaultValue[Dimension](Dimension.Width)
    implicit object LocationDefault extends DefaultValue[Location](Location.Top)
    implicit object LegendOrientationDefault extends DefaultValue[LegendOrientation](LegendOrientation.TopRight)
    implicit object LabelOrientationDefault extends DefaultValue[LabelOrientation](LabelOrientation.Horizontal)
    implicit object BorderSymmetryDefault extends DefaultValue[BorderSymmetry](BorderSymmetry.Horizontal)
    implicit object DashPatternDefault extends DefaultValue[DashPattern](DashPattern.Solid)
    implicit object AnchorDefault extends DefaultValue[Anchor](Anchor.TopLeft)
    implicit object ColorDefault extends DefaultValue[Color](Color.White)
}

trait DefaultImplicits extends TypeDefaults with EnumDefaults
object DefaultImplicits extends DefaultImplicits
