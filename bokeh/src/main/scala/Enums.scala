package io.continuum.bokeh

sealed trait LineJoin extends EnumType
object LineJoin extends Enum[LineJoin] {
    case object Miter extends LineJoin
    case object Round extends LineJoin
    case object Bevel extends LineJoin
}

sealed trait LineDash extends EnumType
object LineDash extends Enum[LineDash] {
    case object Solid extends LineDash
    case object Dashed extends LineDash
    case object Dotted extends LineDash
    case object Dotdash extends LineDash
    case object Dashdot extends LineDash
}

sealed trait LineCap extends EnumType
object LineCap extends Enum[LineCap] {
    case object Butt extends LineCap
    case object Round extends LineCap
    case object Square extends LineCap
}

sealed trait FontStyle extends EnumType
object FontStyle extends Enum[FontStyle] {
    case object Normal extends FontStyle
    case object Italic extends FontStyle
    case object Bold extends FontStyle
}

sealed trait TextAlign extends EnumType
object TextAlign extends Enum[TextAlign] {
    case object Left extends TextAlign
    case object Right extends TextAlign
    case object Center extends TextAlign
}

sealed trait TextBaseline extends EnumType
object TextBaseline extends Enum[TextBaseline] {
    case object Top extends TextBaseline
    case object Middle extends TextBaseline
    case object Bottom extends TextBaseline
    case object Alphabetic extends TextBaseline
    case object Hanging extends TextBaseline
}

sealed trait Direction extends EnumType
object Direction extends Enum[Direction] {
    case object Clock extends Direction
    case object AntiClock extends Direction
}

sealed trait Units extends EnumType
object Units extends Enum[Units] {
    case object Screen extends Units
    case object Data extends Units
}

sealed trait AngleUnits extends EnumType
object AngleUnits extends Enum[AngleUnits] {
    case object Deg extends AngleUnits
    case object Rad extends AngleUnits
}

sealed trait DatetimeUnits extends EnumType
object DatetimeUnits extends Enum[DatetimeUnits] {
    case object Microseconds extends DatetimeUnits
    case object Milliseconds extends DatetimeUnits
    case object Seconds extends DatetimeUnits
    case object Minsec extends DatetimeUnits
    case object Minutes extends DatetimeUnits
    case object Hourmin extends DatetimeUnits
    case object Hours extends DatetimeUnits
    case object Days extends DatetimeUnits
    case object Months extends DatetimeUnits
    case object Years extends DatetimeUnits
}

sealed trait Dimension extends EnumType
object Dimension extends Enum[Dimension] {
    case object Width extends Dimension
    case object Height extends Dimension
    case object X extends Dimension
    case object Y extends Dimension
}

sealed trait Location extends EnumType
object Location extends Enum[Location] {
    case object Top extends Location
    case object Bottom extends Location
    case object Left extends Location
    case object Right extends Location
}

sealed trait Orientation extends EnumType
object Orientation extends Enum[Orientation] {
    case object Horizontal extends Orientation
    case object Vertical extends Orientation
}

sealed trait LegendOrientation extends EnumType
object LegendOrientation extends Enum[LegendOrientation] {
    case object TopRight extends LegendOrientation
    case object TopLeft extends LegendOrientation
    case object BottomLeft extends LegendOrientation
    case object BottomRight extends LegendOrientation
}

sealed trait BorderSymmetry extends EnumType
object BorderSymmetry extends Enum[BorderSymmetry] {
    case object Horizontal extends BorderSymmetry
    case object Vertical extends BorderSymmetry
    case object HorizontalVertical extends BorderSymmetry
    case object VerticalHorizontal extends BorderSymmetry
}

sealed trait DashPattern extends EnumType
object DashPattern extends Enum[DashPattern] {
    case object Solid extends DashPattern
    case object Dashed extends DashPattern
    case object Dotted extends DashPattern
    case object DotDash extends DashPattern
    case object DashDot extends DashPattern
}

sealed trait Anchor extends EnumType
object Anchor extends Enum[Anchor] {
    case object TopLeft extends Anchor
    case object TopCenter extends Anchor
    case object TopRight extends Anchor
    case object RightCenter extends Anchor
    case object BottomRight extends Anchor
    case object BottomCenter extends Anchor
    case object BottomLeft extends Anchor
    case object LeftCenter extends Anchor
    case object Center extends Anchor
}

sealed trait ColumnType extends EnumType
object ColumnType extends Enum[ColumnType] {
    case object Text extends ColumnType
    case object Numeric extends ColumnType
    case object Date extends ColumnType
    case object AutoComplete extends ColumnType
}
