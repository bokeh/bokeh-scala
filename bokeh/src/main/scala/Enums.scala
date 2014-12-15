package io.continuum.bokeh

sealed trait LineJoin extends EnumType with SnakeCase
@enum object LineJoin extends Enumerated[LineJoin] {
    case object Miter extends LineJoin
    case object Round extends LineJoin
    case object Bevel extends LineJoin
}

sealed trait LineCap extends EnumType with SnakeCase
@enum object LineCap extends Enumerated[LineCap] {
    case object Butt extends LineCap
    case object Round extends LineCap
    case object Square extends LineCap
}

sealed trait FontStyle extends EnumType with SnakeCase
@enum object FontStyle extends Enumerated[FontStyle] {
    case object Normal extends FontStyle
    case object Italic extends FontStyle
    case object Bold extends FontStyle
}

sealed trait FontUnits extends EnumType with SnakeCase
@enum object FontUnits extends Enumerated[FontUnits] {
    case object EX extends FontUnits
    case object PX extends FontUnits
    case object CM extends FontUnits
    case object MM extends FontUnits
    case object IN extends FontUnits
    case object PT extends FontUnits
    case object PC extends FontUnits
}

sealed trait TextAlign extends EnumType with SnakeCase
@enum object TextAlign extends Enumerated[TextAlign] {
    case object Left extends TextAlign
    case object Right extends TextAlign
    case object Center extends TextAlign
}

sealed trait TextBaseline extends EnumType with SnakeCase
@enum object TextBaseline extends Enumerated[TextBaseline] {
    case object Top extends TextBaseline
    case object Middle extends TextBaseline
    case object Bottom extends TextBaseline
    case object Alphabetic extends TextBaseline
    case object Hanging extends TextBaseline
}

sealed trait Direction extends EnumType with SnakeCase
@enum object Direction extends Enumerated[Direction] {
    case object Clock extends Direction
    case object AntiClock extends Direction
}

trait Units

sealed trait SpatialUnits extends EnumType with SnakeCase with Units
@enum object SpatialUnits extends Enumerated[SpatialUnits] {
    case object Data extends SpatialUnits
    case object Screen extends SpatialUnits
}

sealed trait AngularUnits extends EnumType with SnakeCase with Units
@enum object AngularUnits extends Enumerated[AngularUnits] {
    case object Rad extends AngularUnits
    case object Deg extends AngularUnits
}

sealed trait DatetimeUnits extends EnumType with SnakeCase
@enum object DatetimeUnits extends Enumerated[DatetimeUnits] {
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

sealed trait Dimension extends EnumType with SnakeCase
@enum object Dimension extends Enumerated[Dimension] {
    case object Width extends Dimension
    case object Height extends Dimension
    case object X extends Dimension
    case object Y extends Dimension
}

sealed trait Location extends EnumType with SnakeCase
@enum object Location extends Enumerated[Location] {
    case object Auto extends Location
    case object Above extends Location
    case object Below extends Location
    case object Left extends Location
    case object Right extends Location
}

sealed trait Orientation extends EnumType with SnakeCase
@enum object Orientation extends Enumerated[Orientation] {
    case object Horizontal extends Orientation
    case object Vertical extends Orientation
}

sealed trait LegendOrientation extends EnumType with SnakeCase
@enum object LegendOrientation extends Enumerated[LegendOrientation] {
    case object TopRight extends LegendOrientation
    case object TopLeft extends LegendOrientation
    case object BottomLeft extends LegendOrientation
    case object BottomRight extends LegendOrientation
}

sealed trait BorderSymmetry extends EnumType with SnakeCase
@enum object BorderSymmetry extends Enumerated[BorderSymmetry] {
    case object Horizontal extends BorderSymmetry
    case object Vertical extends BorderSymmetry
    case object HorizontalVertical extends BorderSymmetry
    case object VerticalHorizontal extends BorderSymmetry
}

sealed trait Anchor extends EnumType with SnakeCase
@enum object Anchor extends Enumerated[Anchor] {
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

sealed trait ColumnType extends EnumType with SnakeCase
@enum object ColumnType extends Enumerated[ColumnType] {
    case object String extends ColumnType
    case object Numeric extends ColumnType
    case object Date extends ColumnType
    case object Checkbox extends ColumnType
    case object Select extends ColumnType
    case object Dropdown extends ColumnType
    case object Autocomplete extends ColumnType
    case object Password extends ColumnType
    case object Handsontable extends ColumnType
}

sealed trait ButtonType extends EnumType with SnakeCase
@enum object ButtonType extends Enumerated[ButtonType] {
    case object Default extends ButtonType
    case object Primary extends ButtonType
    case object Success extends ButtonType
    case object Warning extends ButtonType
    case object Danger extends ButtonType
    case object Link extends ButtonType
}

sealed trait MapType extends EnumType with SnakeCase
@enum object MapType extends Enumerated[MapType] {
    case object Satellite extends MapType
    case object Roadmap extends MapType
    case object Terrain extends MapType
    case object Hybrid extends MapType
}

sealed trait Flip extends EnumType with SnakeCase
@enum object Flip extends Enumerated[Flip] {
    case object Horizontal extends Flip
    case object Vertical extends Flip
}

sealed trait LogLevel extends EnumType with SnakeCase
@enum object LogLevel extends Enumerated[LogLevel] {
    case object Trace extends LogLevel
    case object Debug extends LogLevel
    case object Info extends LogLevel
    case object Warn extends LogLevel
    case object Error extends LogLevel
    case object Fatal extends LogLevel
}

sealed trait Checkmark extends EnumType with DashCase
@enum object Checkmark extends Enumerated[Checkmark] {
    case object Check extends Checkmark
    case object CheckCircle extends Checkmark
    case object CheckCircleO extends Checkmark
    case object CheckSquare extends Checkmark
    case object CheckSquareO extends Checkmark
}

sealed trait Sort extends EnumType with SnakeCase
@enum object Sort extends Enumerated[Sort] {
    case object Ascending extends Sort
    case object Descending extends Sort
}

sealed trait Language extends EnumType
@enum object Language extends Enumerated[Language] {
    case object `be-nl` extends Language
    case object `chs` extends Language
    case object `cs` extends Language
    case object `da-dk` extends Language
    case object `de-ch` extends Language
    case object `de` extends Language
    case object `en` extends Language
    case object `en-gb` extends Language
    case object `es-ES` extends Language
    case object `es` extends Language
    case object `et` extends Language
    case object `fi` extends Language
    case object `fr-CA` extends Language
    case object `fr-ch` extends Language
    case object `fr` extends Language
    case object `hu` extends Language
    case object `it` extends Language
    case object `ja` extends Language
    case object `nl-nl` extends Language
    case object `pl` extends Language
    case object `pt-br` extends Language
    case object `pt-pt` extends Language
    case object `ru` extends Language
    case object `ru-UA` extends Language
    case object `sk` extends Language
    case object `th` extends Language
    case object `tr` extends Language
    case object `uk-UA` extends Language
}

sealed trait Logo extends EnumType with SnakeCase
@enum object Logo extends Enumerated[Logo] {
    case object Normal extends Logo
    case object Grey extends Logo
}

sealed trait Layout extends EnumType with SnakeCase
@enum object Layout extends Enumerated[Layout] {
    case object Left extends Layout
    case object Right extends Layout
    case object Above extends Layout
    case object Below extends Layout
    case object Center extends Layout
}
