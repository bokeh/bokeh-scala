package org.continuumio.bokeh

sealed trait Enum

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

sealed trait Location extends Enum
object Location {
    case object Top extends Location
    case object Bottom extends Location
    case object Left extends Location
    case object Right extends Location
    case object Min extends Location
}

sealed trait Color extends Enum
object Color {
    case object Indigo extends Color
    case object Gold extends Color
    case object Firebrick extends Color
    case object Indianred extends Color
    case object Yellow extends Color
    case object Darkolivegreen extends Color
    case object Darkseagreen extends Color
    case object Darkslategrey extends Color
    case object Mediumvioletred extends Color
    case object Mediumorchid extends Color
    case object Chartreuse extends Color
    case object Mediumblue extends Color
    case object Black extends Color
    case object Springgreen extends Color
    case object Orange extends Color
    case object Lightsalmon extends Color
    case object Brown extends Color
    case object Turquoise extends Color
    case object Olivedrab extends Color
    case object Cyan extends Color
    case object Silver extends Color
    case object Skyblue extends Color
    case object Gray extends Color
    case object Darkturquoise extends Color
    case object Goldenrod extends Color
    case object Darkgreen extends Color
    case object Darkviolet extends Color
    case object Darkgray extends Color
    case object Lightpink extends Color
    case object Teal extends Color
    case object Darkmagenta extends Color
    case object Lightgoldenrodyellow extends Color
    case object Lavender extends Color
    case object Yellowgreen extends Color
    case object Thistle extends Color
    case object Violet extends Color
    case object Navy extends Color
    case object Dimgrey extends Color
    case object Orchid extends Color
    case object Blue extends Color
    case object Ghostwhite extends Color
    case object Honeydew extends Color
    case object Cornflowerblue extends Color
    case object Purple extends Color
    case object Darkkhaki extends Color
    case object Mediumpurple extends Color
    case object Cornsilk extends Color
    case object Red extends Color
    case object Bisque extends Color
    case object Slategray extends Color
    case object Darkcyan extends Color
    case object Khaki extends Color
    case object Wheat extends Color
    case object Deepskyblue extends Color
    case object Darkred extends Color
    case object Steelblue extends Color
    case object Aliceblue extends Color
    case object Lightslategrey extends Color
    case object Gainsboro extends Color
    case object Mediumturquoise extends Color
    case object Floralwhite extends Color
    case object Coral extends Color
    case object Aqua extends Color
    case object Burlywood extends Color
    case object Darksalmon extends Color
    case object Beige extends Color
    case object Azure extends Color
    case object Lightsteelblue extends Color
    case object Oldlace extends Color
    case object Greenyellow extends Color
    case object Royalblue extends Color
    case object Lightseagreen extends Color
    case object Mistyrose extends Color
    case object Sienna extends Color
    case object Lightcoral extends Color
    case object Orangered extends Color
    case object Navajowhite extends Color
    case object Lime extends Color
    case object Palegreen extends Color
    case object Lightcyan extends Color
    case object Seashell extends Color
    case object Mediumspringgreen extends Color
    case object Fuchsia extends Color
    case object Papayawhip extends Color
    case object Blanchedalmond extends Color
    case object Peru extends Color
    case object Aquamarine extends Color
    case object White extends Color
    case object Darkslategray extends Color
    case object Ivory extends Color
    case object Darkgoldenrod extends Color
    case object Lawngreen extends Color
    case object Lightgreen extends Color
    case object Crimson extends Color
    case object Forestgreen extends Color
    case object Maroon extends Color
    case object Olive extends Color
    case object Mintcream extends Color
    case object Antiquewhite extends Color
    case object Dimgray extends Color
    case object Hotpink extends Color
    case object Moccasin extends Color
    case object Limegreen extends Color
    case object Saddlebrown extends Color
    case object Grey extends Color
    case object Darkslateblue extends Color
    case object Lightskyblue extends Color
    case object Deeppink extends Color
    case object Plum extends Color
    case object Lightgrey extends Color
    case object Dodgerblue extends Color
    case object Slateblue extends Color
    case object Sandybrown extends Color
    case object Magenta extends Color
    case object Tan extends Color
    case object Rosybrown extends Color
    case object Pink extends Color
    case object Lightblue extends Color
    case object Palevioletred extends Color
    case object Mediumseagreen extends Color
    case object Linen extends Color
    case object Darkorange extends Color
    case object Powderblue extends Color
    case object Seagreen extends Color
    case object Snow extends Color
    case object Mediumslateblue extends Color
    case object Midnightblue extends Color
    case object Paleturquoise extends Color
    case object Palegoldenrod extends Color
    case object Whitesmoke extends Color
    case object Darkorchid extends Color
    case object Salmon extends Color
    case object Lightslategray extends Color
    case object Lemonchiffon extends Color
    case object Chocolate extends Color
    case object Tomato extends Color
    case object Cadetblue extends Color
    case object Lightyellow extends Color
    case object Lavenderblush extends Color
    case object Darkblue extends Color
    case object Mediumaquamarine extends Color
    case object Green extends Color
    case object Blueviolet extends Color
    case object Peachpuff extends Color
    case object Darkgrey extends Color
}
