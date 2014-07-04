package io.continuum.bokeh

import core.{EnumType,Enum}

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

sealed abstract class NamedColor(name: String, red: Int, green: Int, blue: Int) extends RGBAColor(red, green, blue, 1.0) with EnumType {
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

    case object AliceBlue            extends NamedColor("aliceblue",             240,  248,  255)
    case object AntiqueWhite         extends NamedColor("antiquewhite",          250,  235,  215)
    case object Aqua                 extends NamedColor("aqua",                    0,  255,  255)
    case object AquaMarine           extends NamedColor("aquamarine",            127,  255,  212)
    case object Azure                extends NamedColor("azure",                 240,  255,  255)
    case object Beige                extends NamedColor("beige",                 245,  245,  220)
    case object Bisque               extends NamedColor("bisque",                255,  228,  196)
    case object Black                extends NamedColor("black",                   0,    0,    0)
    case object BlanchedAlmond       extends NamedColor("blanchedalmond",        255,  235,  205)
    case object Blue                 extends NamedColor("blue",                    0,    0,  255)
    case object BlueViolet           extends NamedColor("blueviolet",            138,   43,  226)
    case object Brown                extends NamedColor("brown",                 165,   42,   42)
    case object BurlyWood            extends NamedColor("burlywood",             222,  184,  135)
    case object CadetBlue            extends NamedColor("cadetblue",              95,  158,  160)
    case object Chartreuse           extends NamedColor("chartreuse",            127,  255,    0)
    case object Chocolate            extends NamedColor("chocolate",             210,  105,   30)
    case object Coral                extends NamedColor("coral",                 255,  127,   80)
    case object CornFlowerBlue       extends NamedColor("cornflowerblue",        100,  149,  237)
    case object Cornsilk             extends NamedColor("cornsilk",              255,  248,  220)
    case object Crimson              extends NamedColor("crimson",               220,   20,   60)
    case object Cyan                 extends NamedColor("cyan",                    0,  255,  255)
    case object DarkBlue             extends NamedColor("darkblue",                0,    0,  139)
    case object DarkCyan             extends NamedColor("darkcyan",                0,  139,  139)
    case object DarkGoldenRod        extends NamedColor("darkgoldenrod",         184,  134,   11)
    case object DarkGray             extends NamedColor("darkgray",              169,  169,  169)
    case object DarkGreen            extends NamedColor("darkgreen",               0,  100,   0)
    case object DarkGrey             extends NamedColor("darkgrey",              169,  169,  169)
    case object DarKkhaki            extends NamedColor("darkkhaki",             189,  183,  107)
    case object DarkMagenta          extends NamedColor("darkmagenta",           139,    0,  139)
    case object DarkOliveGreen       extends NamedColor("darkolivegreen",         85,  107,   47)
    case object DarkOrange           extends NamedColor("darkorange",            255,  140,    0)
    case object DarkOrchid           extends NamedColor("darkorchid",            153,   50,  204)
    case object DarkRed              extends NamedColor("darkred",               139,    0,    0)
    case object DarkSalmon           extends NamedColor("darksalmon",            233,  150,  122)
    case object DarkSeaGreen         extends NamedColor("darkseagreen",          143,  188,  143)
    case object DarkSlateBlue        extends NamedColor("darkslateblue",          72,   61,  139)
    case object DarkSlateGray        extends NamedColor("darkslategray",          47,   79,   79)
    case object DarkSlateGrey        extends NamedColor("darkslategrey",          47,   79,   79)
    case object DarkTurquoise        extends NamedColor("darkturquoise",           0,  206,  209)
    case object DarkViolet           extends NamedColor("darkviolet",            148,    0,  211)
    case object DeepPink             extends NamedColor("deeppink",              255,   20,  147)
    case object DeepSkyBlue          extends NamedColor("deepskyblue",             0,  191,  255)
    case object DimGray              extends NamedColor("dimgray",               105,  105,  105)
    case object DimGrey              extends NamedColor("dimgrey",               105,  105,  105)
    case object DodgerBlue           extends NamedColor("dodgerblue",             30,  144,  255)
    case object FireBrick            extends NamedColor("firebrick",             178,   34,   34)
    case object FloralWhite          extends NamedColor("floralwhite",           255,  250,  240)
    case object ForestGreen          extends NamedColor("forestgreen",            34,  139,   34)
    case object Fuchsia              extends NamedColor("fuchsia",               255,    0,  255)
    case object Gainsboro            extends NamedColor("gainsboro",             220,  220,  220)
    case object GhostWhite           extends NamedColor("ghostwhite",            248,  248,  255)
    case object Gold                 extends NamedColor("gold",                  255,  215,    0)
    case object GoldenRod            extends NamedColor("goldenrod",             218,  165,   32)
    case object Gray                 extends NamedColor("gray",                  128,  128,  128)
    case object Green                extends NamedColor("green",                   0,  128,    0)
    case object GreenYellow          extends NamedColor("greenyellow",           173,  255,   47)
    case object Grey                 extends NamedColor("grey",                  128,  128,  128)
    case object HoneyDew             extends NamedColor("honeydew",              240,  255,  240)
    case object HotPink              extends NamedColor("hotpink",               255,  105,  180)
    case object IndianRed            extends NamedColor("indianred",             205,   92,   92)
    case object Indigo               extends NamedColor("indigo",                 75,    0,  130)
    case object Ivory                extends NamedColor("ivory",                 255,  255,  240)
    case object Khaki                extends NamedColor("khaki",                 240,  230,  140)
    case object Lavender             extends NamedColor("lavender",              230,  230,  250)
    case object LavenderBlush        extends NamedColor("lavenderblush",         255,  240,  245)
    case object LawnGreen            extends NamedColor("lawngreen",             124,  252,    0)
    case object LemonChiffon         extends NamedColor("lemonchiffon",          255,  250,  205)
    case object LightBlue            extends NamedColor("lightblue",             173,  216,  230)
    case object LightCoral           extends NamedColor("lightcoral",            240,  128,  128)
    case object LightCyan            extends NamedColor("lightcyan",             224,  255,  255)
    case object LightGoldenRodYellow extends NamedColor("lightgoldenrodyellow",  250,  250,  210)
    case object LightGray            extends NamedColor("lightgray",             211,  211,  211)
    case object LightGreen           extends NamedColor("lightgreen",            144,  238,  144)
    case object LightGrey            extends NamedColor("lightgrey",             211,  211,  211)
    case object LightPink            extends NamedColor("lightpink",             255,  182,  193)
    case object LightSalmon          extends NamedColor("lightsalmon",           255,  160,  122)
    case object LightSeaGreen        extends NamedColor("lightseagreen",          32,  178,  170)
    case object LightSkyBlue         extends NamedColor("lightskyblue",          135,  206,  250)
    case object LightSlateGray       extends NamedColor("lightslategray",        119,  136,  153)
    case object LightSlateGrey       extends NamedColor("lightslategrey",        119,  136,  153)
    case object LightSteelBlue       extends NamedColor("lightsteelblue",        176,  196,  222)
    case object LightYellow          extends NamedColor("lightyellow",           255,  255,  224)
    case object Lime                 extends NamedColor("lime",                    0,  255,    0)
    case object LimeGreen            extends NamedColor("limegreen",              50,  205,   50)
    case object Linen                extends NamedColor("linen",                 250,  240,  230)
    case object Magenta              extends NamedColor("magenta",               255,    0,  255)
    case object Maroon               extends NamedColor("maroon",                128,    0,    0)
    case object MediumAquaMarine     extends NamedColor("mediumaquamarine",      102,  205,  170)
    case object MediumBlue           extends NamedColor("mediumblue",              0,    0,  205)
    case object MediumOrchid         extends NamedColor("mediumorchid",          186,   85,  211)
    case object MediumPurple         extends NamedColor("mediumpurple",          147,  112,  219)
    case object MediumSeaGreen       extends NamedColor("mediumseagreen",         60,  179,  113)
    case object MediumSlateBlue      extends NamedColor("mediumslateblue",       123,  104,  238)
    case object MediumSpringGreen    extends NamedColor("mediumspringgreen",       0,  250,  154)
    case object MediumTurquoise      extends NamedColor("mediumturquoise",        72,  209,  204)
    case object MediumVioletRed      extends NamedColor("mediumvioletred",       199,   21,  133)
    case object MidnightBlue         extends NamedColor("midnightblue",           25,   25,  112)
    case object MintCream            extends NamedColor("mintcream",             245,  255,  250)
    case object MistyRose            extends NamedColor("mistyrose",             255,  228,  225)
    case object Moccasin             extends NamedColor("moccasin",              255,  228,  181)
    case object NavajoWhite          extends NamedColor("navajowhite",           255,  222,  173)
    case object Navy                 extends NamedColor("navy",                    0,    0,  128)
    case object OldLace              extends NamedColor("oldlace",               253,  245,  230)
    case object Olive                extends NamedColor("olive",                 128,  128,    0)
    case object OliveDrab            extends NamedColor("olivedrab",             107,  142,   35)
    case object Orange               extends NamedColor("orange",                255,  165,    0)
    case object OrangeRed            extends NamedColor("orangered",             255,   69,    0)
    case object Orchid               extends NamedColor("orchid",                218,  112,  214)
    case object PaleGoldenRod        extends NamedColor("palegoldenrod",         238,  232,  170)
    case object PaleGreen            extends NamedColor("palegreen",             152,  251,  152)
    case object PaleTurquoise        extends NamedColor("paleturquoise",         175,  238,  238)
    case object PaleVioletRed        extends NamedColor("palevioletred",         219,  112,  147)
    case object PapayaWhip           extends NamedColor("papayawhip",            255,  239,  213)
    case object PeachPuff            extends NamedColor("peachpuff",             255,  218,  185)
    case object Peru                 extends NamedColor("peru",                  205,  133,   63)
    case object Pink                 extends NamedColor("pink",                  255,  192,  203)
    case object Plum                 extends NamedColor("plum",                  221,  160,  221)
    case object PowderBlue           extends NamedColor("powderblue",            176,  224,  230)
    case object Purple               extends NamedColor("purple",                128,    0,  128)
    case object Red                  extends NamedColor("red",                   255,    0,    0)
    case object RosyBrown            extends NamedColor("rosybrown",             188,  143,  143)
    case object RoyalBlue            extends NamedColor("royalblue",              65,  105,  225)
    case object SaddleBrown          extends NamedColor("saddlebrown",           139,   69,   19)
    case object Salmon               extends NamedColor("salmon",                250,  128,  114)
    case object SandyBrown           extends NamedColor("sandybrown",            244,  164,   96)
    case object SeaGreen             extends NamedColor("seagreen",               46,  139,   87)
    case object Seashell             extends NamedColor("seashell",              255,  245,  238)
    case object Sienna               extends NamedColor("sienna",                160,   82,   45)
    case object Silver               extends NamedColor("silver",                192,  192,  192)
    case object SkyBlue              extends NamedColor("skyblue",               135,  206,  235)
    case object SlateBlue            extends NamedColor("slateblue",             106,   90,  205)
    case object SlateGray            extends NamedColor("slategray",             112,  128,  144)
    case object SlateGrey            extends NamedColor("slategrey",             112,  128,  144)
    case object Snow                 extends NamedColor("snow",                  255,  250,  250)
    case object SpringGreen          extends NamedColor("springgreen",             0,  255,  127)
    case object SteelBlue            extends NamedColor("steelblue",              70,  130,  180)
    case object Tan                  extends NamedColor("tan",                   210,  180,  140)
    case object Teal                 extends NamedColor("teal",                    0,  128,  128)
    case object Thistle              extends NamedColor("thistle",               216,  191,  216)
    case object Tomato               extends NamedColor("tomato",                255,   99,   71)
    case object Turquoise            extends NamedColor("turquoise",              64,  224,  208)
    case object Violet               extends NamedColor("violet",                238,  130,  238)
    case object Wheat                extends NamedColor("wheat",                 245,  222,  179)
    case object White                extends NamedColor("white",                 255,  255,  255)
    case object WhiteSmoke           extends NamedColor("whitesmoke",            245,  245,  245)
    case object Yellow               extends NamedColor("yellow",                255,  255,    0)
    case object YellowGreen          extends NamedColor("yellowgreen",           154,  205,   50)
}
