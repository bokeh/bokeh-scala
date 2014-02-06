package org.continuumio.bokeh

trait Enum

sealed trait LineJoin extends Enum
object LineJoin {
    case object Miter extends LineJoin
    case object Round extends LineJoin
    case object Bevel extends LineJoin
}

sealed trait LineDash extends Enum
object LineDash {
    case object Solid extends LineDash
    case object Dashed extends LineDash
    case object Dotted extends LineDash
    case object Dotdash extends LineDash
    case object Dashdot extends LineDash
}

sealed trait LineCap extends Enum
object LineCap {
    case object Butt extends LineCap
    case object Round extends LineCap
    case object Square extends LineCap
}

sealed trait FontStyle extends Enum
object FontStyle {
    case object Normal extends FontStyle
    case object Italic extends FontStyle
    case object Bold extends FontStyle
}

sealed trait TextAlign extends Enum
object TextAlign {
    case object Left extends TextAlign
    case object Right extends TextAlign
    case object Center extends TextAlign
}

sealed trait Baseline extends Enum
object Baseline {
    case object Top extends Baseline
    case object Middle extends Baseline
    case object Bottom extends Baseline
    case object Alphabetic extends Baseline
    case object Hanging extends Baseline
}

sealed trait Direction extends Enum
object Direction {
    case object Clock extends Direction
    case object AntiClock extends Direction
}

sealed trait Orientation extends Enum
object Orientation {
    case object Horizontal extends Orientation
    case object Vertical extends Orientation
}

sealed trait Units extends Enum
object Units {
    case object Screen extends Units
    case object Data extends Units
}

sealed trait AngleUnits extends Enum
object AngleUnits {
    case object Deg extends AngleUnits
    case object Rad extends AngleUnits
}

sealed trait Dimension extends Enum
object Dimension {
    case object Width extends Dimension
    case object Height extends Dimension
}
