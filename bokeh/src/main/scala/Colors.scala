package io.continuum.bokeh

import core.{EnumType,Enum}

sealed trait Color

sealed trait CSSColor extends Color {
    def toCSS: String
}

case class RGB(red: Int, green: Int, blue: Int) extends CSSColor {
    require(0 <= red   && red   <= 255, s"invalid red component: $red")
    require(0 <= green && green <= 255, s"invalid green component: $green")
    require(0 <= blue  && blue  <= 255, s"invalid blue component: $blue")

    def toHex = f"#$red%02x#$green%02x#$blue%02x"
    def toCSS = s"rgb($red, $green, $blue)"
}

case class RGBA(red: Int, green: Int, blue: Int, alpha: Double) extends CSSColor {
    require(0 <= red   && red   <= 255, s"invalid red component: $red")
    require(0 <= green && green <= 255, s"invalid green component: $green")
    require(0 <= blue  && blue  <= 255, s"invalid blue component: $blue")

    require(0.0 <= alpha && alpha <= 1.0, s"invalid alpha component: $alpha")

    def toCSS = s"rgba($red, $green, $blue, $alpha)"
}

case class HSL(hue: Int, saturation: Percent, lightness: Percent) extends CSSColor {
    def toCSS = s"hsl($hue, $saturation, $lightness)"
}

case class HSLA(hue: Int, saturation: Percent, lightness: Percent, alpha: Double) extends CSSColor {
    def toCSS = s"hsla($hue, $saturation, $lightness, $alpha)"
}

sealed trait NamedColor extends Color with EnumType
object Color extends Enum[NamedColor] {
    implicit def StringToCSSColor(color: String): CSSColor = {
        lazy val HexColor = """^#([\da-fA-F]{2})([\da-fA-F]{2})([\da-fA-F]{2})$""".r
        lazy val RGBColor = """^rgb\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)$""".r

        def hex(s: String) = Integer.parseInt(s, 16)
        def int(s: String) = s.toInt

        color match {
            case HexColor(r, g, b) => RGB(hex(r), hex(g), hex(b))
            case RGBColor(r, g, b) => RGB(int(r), int(g), int(b))
        }
    }

    case object Indigo extends NamedColor
    case object Gold extends NamedColor
    case object Firebrick extends NamedColor
    case object Indianred extends NamedColor
    case object Yellow extends NamedColor
    case object Darkolivegreen extends NamedColor
    case object Darkseagreen extends NamedColor
    case object Darkslategrey extends NamedColor
    case object Mediumvioletred extends NamedColor
    case object Mediumorchid extends NamedColor
    case object Chartreuse extends NamedColor
    case object Mediumblue extends NamedColor
    case object Black extends NamedColor
    case object Springgreen extends NamedColor
    case object Orange extends NamedColor
    case object Lightsalmon extends NamedColor
    case object Brown extends NamedColor
    case object Turquoise extends NamedColor
    case object Olivedrab extends NamedColor
    case object Cyan extends NamedColor
    case object Silver extends NamedColor
    case object Skyblue extends NamedColor
    case object Gray extends NamedColor
    case object Darkturquoise extends NamedColor
    case object Goldenrod extends NamedColor
    case object Darkgreen extends NamedColor
    case object Darkviolet extends NamedColor
    case object Darkgray extends NamedColor
    case object Lightpink extends NamedColor
    case object Teal extends NamedColor
    case object Darkmagenta extends NamedColor
    case object Lightgoldenrodyellow extends NamedColor
    case object Lavender extends NamedColor
    case object Yellowgreen extends NamedColor
    case object Thistle extends NamedColor
    case object Violet extends NamedColor
    case object Navy extends NamedColor
    case object Dimgrey extends NamedColor
    case object Orchid extends NamedColor
    case object Blue extends NamedColor
    case object Ghostwhite extends NamedColor
    case object Honeydew extends NamedColor
    case object Cornflowerblue extends NamedColor
    case object Purple extends NamedColor
    case object Darkkhaki extends NamedColor
    case object Mediumpurple extends NamedColor
    case object Cornsilk extends NamedColor
    case object Red extends NamedColor
    case object Bisque extends NamedColor
    case object Slategray extends NamedColor
    case object Darkcyan extends NamedColor
    case object Khaki extends NamedColor
    case object Wheat extends NamedColor
    case object Deepskyblue extends NamedColor
    case object Darkred extends NamedColor
    case object Steelblue extends NamedColor
    case object Aliceblue extends NamedColor
    case object Lightslategrey extends NamedColor
    case object Gainsboro extends NamedColor
    case object Mediumturquoise extends NamedColor
    case object Floralwhite extends NamedColor
    case object Coral extends NamedColor
    case object Aqua extends NamedColor
    case object Burlywood extends NamedColor
    case object Darksalmon extends NamedColor
    case object Beige extends NamedColor
    case object Azure extends NamedColor
    case object Lightsteelblue extends NamedColor
    case object Oldlace extends NamedColor
    case object Greenyellow extends NamedColor
    case object Royalblue extends NamedColor
    case object Lightseagreen extends NamedColor
    case object Mistyrose extends NamedColor
    case object Sienna extends NamedColor
    case object Lightcoral extends NamedColor
    case object Orangered extends NamedColor
    case object Navajowhite extends NamedColor
    case object Lime extends NamedColor
    case object Palegreen extends NamedColor
    case object Lightcyan extends NamedColor
    case object Seashell extends NamedColor
    case object Mediumspringgreen extends NamedColor
    case object Fuchsia extends NamedColor
    case object Papayawhip extends NamedColor
    case object Blanchedalmond extends NamedColor
    case object Peru extends NamedColor
    case object Aquamarine extends NamedColor
    case object White extends NamedColor
    case object Darkslategray extends NamedColor
    case object Ivory extends NamedColor
    case object Darkgoldenrod extends NamedColor
    case object Lawngreen extends NamedColor
    case object Lightgreen extends NamedColor
    case object Crimson extends NamedColor
    case object Forestgreen extends NamedColor
    case object Maroon extends NamedColor
    case object Olive extends NamedColor
    case object Mintcream extends NamedColor
    case object Antiquewhite extends NamedColor
    case object Dimgray extends NamedColor
    case object Hotpink extends NamedColor
    case object Moccasin extends NamedColor
    case object Limegreen extends NamedColor
    case object Saddlebrown extends NamedColor
    case object Grey extends NamedColor
    case object Darkslateblue extends NamedColor
    case object Lightskyblue extends NamedColor
    case object Deeppink extends NamedColor
    case object Plum extends NamedColor
    case object Lightgrey extends NamedColor
    case object Dodgerblue extends NamedColor
    case object Slateblue extends NamedColor
    case object Sandybrown extends NamedColor
    case object Magenta extends NamedColor
    case object Tan extends NamedColor
    case object Rosybrown extends NamedColor
    case object Pink extends NamedColor
    case object Lightblue extends NamedColor
    case object Palevioletred extends NamedColor
    case object Mediumseagreen extends NamedColor
    case object Linen extends NamedColor
    case object Darkorange extends NamedColor
    case object Powderblue extends NamedColor
    case object Seagreen extends NamedColor
    case object Snow extends NamedColor
    case object Mediumslateblue extends NamedColor
    case object Midnightblue extends NamedColor
    case object Paleturquoise extends NamedColor
    case object Palegoldenrod extends NamedColor
    case object Whitesmoke extends NamedColor
    case object Darkorchid extends NamedColor
    case object Salmon extends NamedColor
    case object Lightslategray extends NamedColor
    case object Lemonchiffon extends NamedColor
    case object Chocolate extends NamedColor
    case object Tomato extends NamedColor
    case object Cadetblue extends NamedColor
    case object Lightyellow extends NamedColor
    case object Lavenderblush extends NamedColor
    case object Darkblue extends NamedColor
    case object Mediumaquamarine extends NamedColor
    case object Green extends NamedColor
    case object Blueviolet extends NamedColor
    case object Peachpuff extends NamedColor
    case object Darkgrey extends NamedColor
}
