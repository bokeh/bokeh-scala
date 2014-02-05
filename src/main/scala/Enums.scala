package org.continuumio.bokeh

sealed trait LineJoin
object LineJoin {
    case object Miter extends LineJoin
    case object Round extends LineJoin
    case object Bevel extends LineJoin
}

sealed trait LineDash
object LineDash {
    case object Solid extends LineDash
    case object Dashed extends LineDash
    case object Dotted extends LineDash
    case object Dotdash extends LineDash
    case object Dashdot extends LineDash
}

sealed trait LineCap
object LineCap {
    case object Butt extends LineCap
    case object Round extends LineCap
    case object Square extends LineCap
}

sealed trait FontStyle
object FontStyle {
    case object Normal extends FontStyle
    case object Italic extends FontStyle
    case object Bold extends FontStyle
}

sealed trait TextAlign
object TextAlign {
    case object Left extends TextAlign
    case object Right extends TextAlign
    case object Center extends TextAlign
}

sealed trait Baseline
object Baseline {
    case object Top extends Baseline
    case object Middle extends Baseline
    case object Bottom extends Baseline
    case object Alphabetic extends Baseline
    case object Hanging extends Baseline
}

sealed trait Direction
object Direction {
    case object Clock extends Direction
    case object AntiClock extends Direction
}

sealed trait Orientation
object Orientation {
    case object Horizontal extends Orientation
    case object Vertical extends Orientation
}

sealed trait Units
object Units {
    case object Screen extends Units
    case object Data extends Units
}

sealed trait AngleUnits
object AngleUnits {
    case object Deg extends AngleUnits
    case object Rad extends AngleUnits
}
