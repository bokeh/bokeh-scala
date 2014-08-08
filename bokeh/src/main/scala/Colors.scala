package io.continuum.bokeh

sealed trait Color {
    def toCSS: String
}

abstract class RGBAColor(red: Int, green: Int, blue: Int, alpha: Double) extends Color

case class RGBA(red: Int, green: Int, blue: Int, alpha: Double) extends RGBAColor(red, green, blue, alpha) {
    def toCSS = s"rgba($red, $green, $blue, $alpha)"
}

case class RGB(red: Int, green: Int, blue: Int) extends RGBAColor(red, green, blue, 1.0) {
    def toHex = f"#$red%02x#$green%02x#$blue%02x"
    def toCSS = s"rgb($red, $green, $blue)"
}

abstract class HSLAColor(hue: Int, saturation: Percent, lightness: Percent, alpha: Double) extends Color

case class HSLA(hue: Int, saturation: Percent, lightness: Percent, alpha: Double) extends HSLAColor(hue, saturation, lightness, alpha) {
    def toCSS = s"hsla($hue, $saturation, $lightness, $alpha)"
}

case class HSL(hue: Int, saturation: Percent, lightness: Percent) extends HSLAColor(hue, saturation, lightness, 1.0) {
    def toCSS = s"hsl($hue, $saturation, $lightness)"
}

sealed abstract class NamedColor(red: Int, green: Int, blue: Int) extends RGBAColor(red, green, blue, 1.0) with EnumType {
    def toCSS = name
}
object Color extends Enum[NamedColor] {
    implicit def StringToColor(color: String): Color = {
        lazy val HexColor = """^#([\da-fA-F]{2})([\da-fA-F]{2})([\da-fA-F]{2})$""".r
        lazy val RGBColor = """^rgb\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)$""".r

        def hex(s: String) = Integer.parseInt(s, 16)
        def int(s: String) = s.toInt

        color match {
            case HexColor(r, g, b) => RGB(hex(r), hex(g), hex(b))
            case RGBColor(r, g, b) => RGB(int(r), int(g), int(b))
        }
    }

    case object AliceBlue            extends NamedColor(240, 248, 255)
    case object AntiqueWhite         extends NamedColor(250, 235, 215)
    case object Aqua                 extends NamedColor(  0, 255, 255)
    case object AquaMarine           extends NamedColor(127, 255, 212)
    case object Azure                extends NamedColor(240, 255, 255)
    case object Beige                extends NamedColor(245, 245, 220)
    case object Bisque               extends NamedColor(255, 228, 196)
    case object Black                extends NamedColor(  0,   0,   0)
    case object BlanchedAlmond       extends NamedColor(255, 235, 205)
    case object Blue                 extends NamedColor(  0,   0, 255)
    case object BlueViolet           extends NamedColor(138,  43, 226)
    case object Brown                extends NamedColor(165,  42,  42)
    case object BurlyWood            extends NamedColor(222, 184, 135)
    case object CadetBlue            extends NamedColor( 95, 158, 160)
    case object Chartreuse           extends NamedColor(127, 255,   0)
    case object Chocolate            extends NamedColor(210, 105,  30)
    case object Coral                extends NamedColor(255, 127,  80)
    case object CornFlowerBlue       extends NamedColor(100, 149, 237)
    case object Cornsilk             extends NamedColor(255, 248, 220)
    case object Crimson              extends NamedColor(220,  20,  60)
    case object Cyan                 extends NamedColor(  0, 255, 255)
    case object DarkBlue             extends NamedColor(  0,   0, 139)
    case object DarkCyan             extends NamedColor(  0, 139, 139)
    case object DarkGoldenRod        extends NamedColor(184, 134,  11)
    case object DarkGray             extends NamedColor(169, 169, 169)
    case object DarkGreen            extends NamedColor(  0, 100,   0)
    case object DarkGrey             extends NamedColor(169, 169, 169)
    case object DarKkhaki            extends NamedColor(189, 183, 107)
    case object DarkMagenta          extends NamedColor(139,   0, 139)
    case object DarkOliveGreen       extends NamedColor( 85, 107,  47)
    case object DarkOrange           extends NamedColor(255, 140,   0)
    case object DarkOrchid           extends NamedColor(153,  50, 204)
    case object DarkRed              extends NamedColor(139,   0,   0)
    case object DarkSalmon           extends NamedColor(233, 150, 122)
    case object DarkSeaGreen         extends NamedColor(143, 188, 143)
    case object DarkSlateBlue        extends NamedColor( 72,  61, 139)
    case object DarkSlateGray        extends NamedColor( 47,  79,  79)
    case object DarkSlateGrey        extends NamedColor( 47,  79,  79)
    case object DarkTurquoise        extends NamedColor(  0, 206, 209)
    case object DarkViolet           extends NamedColor(148,   0, 211)
    case object DeepPink             extends NamedColor(255,  20, 147)
    case object DeepSkyBlue          extends NamedColor(  0, 191, 255)
    case object DimGray              extends NamedColor(105, 105, 105)
    case object DimGrey              extends NamedColor(105, 105, 105)
    case object DodgerBlue           extends NamedColor( 30, 144, 255)
    case object FireBrick            extends NamedColor(178,  34,  34)
    case object FloralWhite          extends NamedColor(255, 250, 240)
    case object ForestGreen          extends NamedColor( 34, 139,  34)
    case object Fuchsia              extends NamedColor(255,   0, 255)
    case object Gainsboro            extends NamedColor(220, 220, 220)
    case object GhostWhite           extends NamedColor(248, 248, 255)
    case object Gold                 extends NamedColor(255, 215,   0)
    case object GoldenRod            extends NamedColor(218, 165,  32)
    case object Gray                 extends NamedColor(128, 128, 128)
    case object Green                extends NamedColor(  0, 128,   0)
    case object GreenYellow          extends NamedColor(173, 255,  47)
    case object Grey                 extends NamedColor(128, 128, 128)
    case object HoneyDew             extends NamedColor(240, 255, 240)
    case object HotPink              extends NamedColor(255, 105, 180)
    case object IndianRed            extends NamedColor(205,  92,  92)
    case object Indigo               extends NamedColor( 75,   0, 130)
    case object Ivory                extends NamedColor(255, 255, 240)
    case object Khaki                extends NamedColor(240, 230, 140)
    case object Lavender             extends NamedColor(230, 230, 250)
    case object LavenderBlush        extends NamedColor(255, 240, 245)
    case object LawnGreen            extends NamedColor(124, 252,   0)
    case object LemonChiffon         extends NamedColor(255, 250, 205)
    case object LightBlue            extends NamedColor(173, 216, 230)
    case object LightCoral           extends NamedColor(240, 128, 128)
    case object LightCyan            extends NamedColor(224, 255, 255)
    case object LightGoldenRodYellow extends NamedColor(250, 250, 210)
    case object LightGray            extends NamedColor(211, 211, 211)
    case object LightGreen           extends NamedColor(144, 238, 144)
    case object LightGrey            extends NamedColor(211, 211, 211)
    case object LightPink            extends NamedColor(255, 182, 193)
    case object LightSalmon          extends NamedColor(255, 160, 122)
    case object LightSeaGreen        extends NamedColor( 32, 178, 170)
    case object LightSkyBlue         extends NamedColor(135, 206, 250)
    case object LightSlateGray       extends NamedColor(119, 136, 153)
    case object LightSlateGrey       extends NamedColor(119, 136, 153)
    case object LightSteelBlue       extends NamedColor(176, 196, 222)
    case object LightYellow          extends NamedColor(255, 255, 224)
    case object Lime                 extends NamedColor(  0, 255,   0)
    case object LimeGreen            extends NamedColor( 50, 205,  50)
    case object Linen                extends NamedColor(250, 240, 230)
    case object Magenta              extends NamedColor(255,   0, 255)
    case object Maroon               extends NamedColor(128,   0,   0)
    case object MediumAquaMarine     extends NamedColor(102, 205, 170)
    case object MediumBlue           extends NamedColor(  0,   0, 205)
    case object MediumOrchid         extends NamedColor(186,  85, 211)
    case object MediumPurple         extends NamedColor(147, 112, 219)
    case object MediumSeaGreen       extends NamedColor( 60, 179, 113)
    case object MediumSlateBlue      extends NamedColor(123, 104, 238)
    case object MediumSpringGreen    extends NamedColor(  0, 250, 154)
    case object MediumTurquoise      extends NamedColor( 72, 209, 204)
    case object MediumVioletRed      extends NamedColor(199,  21, 133)
    case object MidnightBlue         extends NamedColor( 25,  25, 112)
    case object MintCream            extends NamedColor(245, 255, 250)
    case object MistyRose            extends NamedColor(255, 228, 225)
    case object Moccasin             extends NamedColor(255, 228, 181)
    case object NavajoWhite          extends NamedColor(255, 222, 173)
    case object Navy                 extends NamedColor(  0,   0, 128)
    case object OldLace              extends NamedColor(253, 245, 230)
    case object Olive                extends NamedColor(128, 128,   0)
    case object OliveDrab            extends NamedColor(107, 142,  35)
    case object Orange               extends NamedColor(255, 165,   0)
    case object OrangeRed            extends NamedColor(255,  69,   0)
    case object Orchid               extends NamedColor(218, 112, 214)
    case object PaleGoldenRod        extends NamedColor(238, 232, 170)
    case object PaleGreen            extends NamedColor(152, 251, 152)
    case object PaleTurquoise        extends NamedColor(175, 238, 238)
    case object PaleVioletRed        extends NamedColor(219, 112, 147)
    case object PapayaWhip           extends NamedColor(255, 239, 213)
    case object PeachPuff            extends NamedColor(255, 218, 185)
    case object Peru                 extends NamedColor(205, 133,  63)
    case object Pink                 extends NamedColor(255, 192, 203)
    case object Plum                 extends NamedColor(221, 160, 221)
    case object PowderBlue           extends NamedColor(176, 224, 230)
    case object Purple               extends NamedColor(128,   0, 128)
    case object Red                  extends NamedColor(255,   0,   0)
    case object RosyBrown            extends NamedColor(188, 143, 143)
    case object RoyalBlue            extends NamedColor( 65, 105, 225)
    case object SaddleBrown          extends NamedColor(139,  69,  19)
    case object Salmon               extends NamedColor(250, 128, 114)
    case object SandyBrown           extends NamedColor(244, 164,  96)
    case object SeaGreen             extends NamedColor( 46, 139,  87)
    case object Seashell             extends NamedColor(255, 245, 238)
    case object Sienna               extends NamedColor(160,  82,  45)
    case object Silver               extends NamedColor(192, 192, 192)
    case object SkyBlue              extends NamedColor(135, 206, 235)
    case object SlateBlue            extends NamedColor(106,  90, 205)
    case object SlateGray            extends NamedColor(112, 128, 144)
    case object SlateGrey            extends NamedColor(112, 128, 144)
    case object Snow                 extends NamedColor(255, 250, 250)
    case object SpringGreen          extends NamedColor(  0, 255, 127)
    case object SteelBlue            extends NamedColor( 70, 130, 180)
    case object Tan                  extends NamedColor(210, 180, 140)
    case object Teal                 extends NamedColor(  0, 128, 128)
    case object Thistle              extends NamedColor(216, 191, 216)
    case object Tomato               extends NamedColor(255,  99,  71)
    case object Turquoise            extends NamedColor( 64, 224, 208)
    case object Violet               extends NamedColor(238, 130, 238)
    case object Wheat                extends NamedColor(245, 222, 179)
    case object White                extends NamedColor(255, 255, 255)
    case object WhiteSmoke           extends NamedColor(245, 245, 245)
    case object Yellow               extends NamedColor(255, 255,   0)
    case object YellowGreen          extends NamedColor(154, 205,  50)
}
