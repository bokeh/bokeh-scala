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
    case class Angle(value: Double) extends Orientation
    case object Horizontal extends Orientation
    case object Vertical extends Orientation
}

sealed trait BorderSymmetry extends EnumType with SnakeCase
@enum object BorderSymmetry extends Enumerated[BorderSymmetry] {
    case object Horizontal extends BorderSymmetry
    case object Vertical extends BorderSymmetry
    case object HorizontalVertical extends BorderSymmetry
    case object VerticalHorizontal extends BorderSymmetry
}

sealed trait LegendLocation extends EnumType with SnakeCase
@enum object LegendLocation extends Enumerated[LegendLocation] {
    case object TopLeft extends LegendLocation
    case object TopCenter extends LegendLocation
    case object TopRight extends LegendLocation
    case object RightCenter extends LegendLocation
    case object BottomRight extends LegendLocation
    case object BottomCenter extends LegendLocation
    case object BottomLeft extends LegendLocation
    case object LeftCenter extends LegendLocation
    case object Center extends LegendLocation
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

sealed trait Logo extends EnumType with SnakeCase
@enum object Logo extends Enumerated[Logo] {
    case object Normal extends Logo
    case object Grey extends Logo
}

sealed trait Place extends EnumType with SnakeCase
@enum object Place extends Enumerated[Place] {
    case object Left extends Place
    case object Right extends Place
    case object Above extends Place
    case object Below extends Place
    case object Center extends Place
}

sealed trait DateFormat extends EnumType
@enum object DateFormat extends Enumerated[DateFormat] {
    case object ATOM extends DateFormat
    case object W3C extends DateFormat
    case object `RFC-3339` extends DateFormat
    case object `ISO-8601` extends DateFormat
    case object COOKIE extends DateFormat
    case object `RFC-822` extends DateFormat
    case object `RFC-850` extends DateFormat
    case object `RFC-1036` extends DateFormat
    case object `RFC-1123` extends DateFormat
    case object `RFC-2822` extends DateFormat
    case object RSS extends DateFormat
    case object TIMESTAMP extends DateFormat
}

sealed trait RoundingFunction extends EnumType with LowerCase
@enum object RoundingFunction extends Enumerated[RoundingFunction] {
    case object Round extends RoundingFunction
    case object Nearest extends RoundingFunction
    case object Floor extends RoundingFunction
    case object RoundDown extends RoundingFunction
    case object Ceil extends RoundingFunction
    case object RoundUp extends RoundingFunction
}

sealed trait NumeralLanguage extends EnumType
@enum object NumeralLanguage extends Enumerated[NumeralLanguage] {
    case object `be-nl` extends NumeralLanguage
    case object `chs` extends NumeralLanguage
    case object `cs` extends NumeralLanguage
    case object `da-dk` extends NumeralLanguage
    case object `de-ch` extends NumeralLanguage
    case object `de` extends NumeralLanguage
    case object `en` extends NumeralLanguage
    case object `en-gb` extends NumeralLanguage
    case object `es-ES` extends NumeralLanguage
    case object `es` extends NumeralLanguage
    case object `et` extends NumeralLanguage
    case object `fi` extends NumeralLanguage
    case object `fr-CA` extends NumeralLanguage
    case object `fr-ch` extends NumeralLanguage
    case object `fr` extends NumeralLanguage
    case object `hu` extends NumeralLanguage
    case object `it` extends NumeralLanguage
    case object `ja` extends NumeralLanguage
    case object `nl-nl` extends NumeralLanguage
    case object `pl` extends NumeralLanguage
    case object `pt-br` extends NumeralLanguage
    case object `pt-pt` extends NumeralLanguage
    case object `ru` extends NumeralLanguage
    case object `ru-UA` extends NumeralLanguage
    case object `sk` extends NumeralLanguage
    case object `th` extends NumeralLanguage
    case object `tr` extends NumeralLanguage
    case object `uk-UA` extends NumeralLanguage
}

sealed trait HTTPMethod extends EnumType
@enum object HTTPMethod extends Enumerated[HTTPMethod] {
    case object POST extends HTTPMethod
    case object GET extends HTTPMethod
}

sealed trait RenderLevel extends EnumType with SnakeCase
@enum object RenderLevel extends Enumerated[RenderLevel] {
    case object Image extends RenderLevel
    case object Underlay extends RenderLevel
    case object Glyph extends RenderLevel
    case object Overlay extends RenderLevel
    case object Annotation extends RenderLevel
    case object Tool extends RenderLevel
}

sealed trait RenderMode extends EnumType with SnakeCase
@enum object RenderMode extends Enumerated[RenderMode] {
    case object Canvas extends RenderMode
    case object CSS extends RenderMode
}

sealed trait HoverMode extends EnumType with LowerCase
@enum object HoverMode extends Enumerated[HoverMode] {
    case object Mouse extends HoverMode
    case object HLine extends HoverMode
    case object VLine extends HoverMode
}

sealed trait PointPolicy extends EnumType with SnakeCase
@enum object PointPolicy extends Enumerated[PointPolicy] {
    case object SnapToData extends PointPolicy
    case object FollowMouse extends PointPolicy
    case object None extends PointPolicy
}

sealed trait LinePolicy extends EnumType with SnakeCase
@enum object LinePolicy extends Enumerated[LinePolicy] {
    case object Prev extends LinePolicy
    case object Next extends LinePolicy
    case object Nearest extends LinePolicy
    case object Interp extends LinePolicy
    case object None extends LinePolicy
}

sealed trait ScriptingLanguage extends EnumType with LowerCase
@enum object ScriptingLanguage extends Enumerated[ScriptingLanguage] {
    case object JavaScript extends ScriptingLanguage
    case object CoffeeScript extends ScriptingLanguage
}

sealed trait StartEnd extends EnumType with LowerCase
@enum object StartEnd extends Enumerated[StartEnd] {
    case object Start extends StartEnd
    case object End extends StartEnd
}
