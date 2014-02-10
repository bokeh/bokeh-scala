package org.continuumio.bokeh

import annotation.implicitNotFound

@implicitNotFound(msg="Can't find DefaultValue type class for type ${T}.")
class DefaultValue[T](val default: T)

trait DefaultImplicits {
    implicit object BooleanDefault extends DefaultValue[Boolean](false)
    implicit object IntDefault extends DefaultValue[Int](0)
    implicit object DoubleDefault extends DefaultValue[Double](0.0)
    implicit object StringDefault extends DefaultValue[String]("")
    implicit object PercentDefault extends DefaultValue[Percent](100%%)
    implicit object LineJoinDefault extends DefaultValue[LineJoin](LineJoin.Miter)
    implicit object LineDashDefault extends DefaultValue[LineDash](LineDash.Solid)
    implicit object LineCapDefault extends DefaultValue[LineCap](LineCap.Butt)
    implicit object FontStyleDefault extends DefaultValue[FontStyle](FontStyle.Normal)
    implicit object TextAlignDefault extends DefaultValue[TextAlign](TextAlign.Left)
    implicit object BaselineDefault extends DefaultValue[Baseline](Baseline.Top)
    implicit object DirectionDefault extends DefaultValue[Direction](Direction.Clock)
    implicit object OrientationDefault extends DefaultValue[Orientation](Orientation.Horizontal)
    implicit object UnitsDefault extends DefaultValue[Units](Units.Screen)
    implicit object AngleUnitsDefault extends DefaultValue[AngleUnits](AngleUnits.Deg)
    implicit object DimensionDefault extends DefaultValue[Dimension](Dimension.Width)
    implicit object LocationDefault extends DefaultValue[Location](Location.Top)
    implicit object ColorDefault extends DefaultValue[Color](Color.White)
    implicit def ListDefault[T]: DefaultValue[List[T]] = new DefaultValue[List[T]](Nil)
    implicit def MapDefault[U, V]: DefaultValue[Map[U, V]] = new DefaultValue[Map[U, V]](Map.empty)
    implicit def HasFieldsDefault[T <: HasFields]: DefaultValue[T] = new DefaultValue[T](null.asInstanceOf[T])
}

object DefaultImplicits extends DefaultImplicits
