package io.continuum.bokeh
package examples
package glyphs

import math.{Pi=>pi}

object Colors extends Example {
    val css3_colors = List[(String, Color, String)](
        ("Pink",                 Color.Pink,                 /* #FFC0CB */ "Pink"),
        ("LightPink",            Color.LightPink,            /* #FFB6C1 */ "Pink"),
        ("HotPink",              Color.HotPink,              /* #FF69B4 */ "Pink"),
        ("DeepPink",             Color.DeepPink,             /* #FF1493 */ "Pink"),
        ("PaleVioletRed",        Color.PaleVioletRed,        /* #DB7093 */ "Pink"),
        ("MediumVioletRed",      Color.MediumVioletRed,      /* #C71585 */ "Pink"),
        ("LightSalmon",          Color.LightSalmon,          /* #FFA07A */ "Red"),
        ("Salmon",               Color.Salmon,               /* #FA8072 */ "Red"),
        ("DarkSalmon",           Color.DarkSalmon,           /* #E9967A */ "Red"),
        ("LightCoral",           Color.LightCoral,           /* #F08080 */ "Red"),
        ("IndianRed",            Color.IndianRed,            /* #CD5C5C */ "Red"),
        ("Crimson",              Color.Crimson,              /* #DC143C */ "Red"),
        ("FireBrick",            Color.FireBrick,            /* #B22222 */ "Red"),
        ("DarkRed",              Color.DarkRed,              /* #8B0000 */ "Red"),
        ("Red",                  Color.Red,                  /* #FF0000 */ "Red"),
        ("OrangeRed",            Color.OrangeRed,            /* #FF4500 */ "Orange"),
        ("Tomato",               Color.Tomato,               /* #FF6347 */ "Orange"),
        ("Coral",                Color.Coral,                /* #FF7F50 */ "Orange"),
        ("DarkOrange",           Color.DarkOrange,           /* #FF8C00 */ "Orange"),
        ("Orange",               Color.Orange,               /* #FFA500 */ "Orange"),
        ("Yellow",               Color.Yellow,               /* #FFFF00 */ "Yellow"),
        ("LightYellow",          Color.LightYellow,          /* #FFFFE0 */ "Yellow"),
        ("LemonChiffon",         Color.LemonChiffon,         /* #FFFACD */ "Yellow"),
        ("LightGoldenRodYellow", Color.LightGoldenRodYellow, /* #FAFAD2 */ "Yellow"),
        ("PapayaWhip",           Color.PapayaWhip,           /* #FFEFD5 */ "Yellow"),
        ("Moccasin",             Color.Moccasin,             /* #FFE4B5 */ "Yellow"),
        ("PeachPuff",            Color.PeachPuff,            /* #FFDAB9 */ "Yellow"),
        ("PaleGoldenRod",        Color.PaleGoldenRod,        /* #EEE8AA */ "Yellow"),
        ("Khaki",                Color.Khaki,                /* #F0E68C */ "Yellow"),
        ("DarkKhaki",            Color.DarkKhaki,            /* #BDB76B */ "Yellow"),
        ("Gold",                 Color.Gold,                 /* #FFD700 */ "Yellow"),
        ("Cornsilk",             Color.Cornsilk,             /* #FFF8DC */ "Brown"),
        ("BlanchedAlmond",       Color.BlanchedAlmond,       /* #FFEBCD */ "Brown"),
        ("Bisque",               Color.Bisque,               /* #FFE4C4 */ "Brown"),
        ("NavajoWhite",          Color.NavajoWhite,          /* #FFDEAD */ "Brown"),
        ("Wheat",                Color.Wheat,                /* #F5DEB3 */ "Brown"),
        ("BurlyWood",            Color.BurlyWood,            /* #DEB887 */ "Brown"),
        ("Tan",                  Color.Tan,                  /* #D2B48C */ "Brown"),
        ("RosyBrown",            Color.RosyBrown,            /* #BC8F8F */ "Brown"),
        ("SandyBrown",           Color.SandyBrown,           /* #F4A460 */ "Brown"),
        ("GoldenRod",            Color.GoldenRod,            /* #DAA520 */ "Brown"),
        ("DarkGoldenRod",        Color.DarkGoldenRod,        /* #B8860B */ "Brown"),
        ("Peru",                 Color.Peru,                 /* #CD853F */ "Brown"),
        ("Chocolate",            Color.Chocolate,            /* #D2691E */ "Brown"),
        ("SaddleBrown",          Color.SaddleBrown,          /* #8B4513 */ "Brown"),
        ("Sienna",               Color.Sienna,               /* #A0522D */ "Brown"),
        ("Brown",                Color.Brown,                /* #A52A2A */ "Brown"),
        ("Maroon",               Color.Maroon,               /* #800000 */ "Brown"),
        ("DarkOliveGreen",       Color.DarkOliveGreen,       /* #556B2F */ "Green"),
        ("Olive",                Color.Olive,                /* #808000 */ "Green"),
        ("OliveDrab",            Color.OliveDrab,            /* #6B8E23 */ "Green"),
        ("YellowGreen",          Color.YellowGreen,          /* #9ACD32 */ "Green"),
        ("LimeGreen",            Color.LimeGreen,            /* #32CD32 */ "Green"),
        ("Lime",                 Color.Lime,                 /* #00FF00 */ "Green"),
        ("LawnGreen",            Color.LawnGreen,            /* #7CFC00 */ "Green"),
        ("Chartreuse",           Color.Chartreuse,           /* #7FFF00 */ "Green"),
        ("GreenYellow",          Color.GreenYellow,          /* #ADFF2F */ "Green"),
        ("SpringGreen",          Color.SpringGreen,          /* #00FF7F */ "Green"),
        ("MediumSpringGreen",    Color.MediumSpringGreen,    /* #00FA9A */ "Green"),
        ("LightGreen",           Color.LightGreen,           /* #90EE90 */ "Green"),
        ("PaleGreen",            Color.PaleGreen,            /* #98FB98 */ "Green"),
        ("DarkSeaGreen",         Color.DarkSeaGreen,         /* #8FBC8F */ "Green"),
        ("MediumSeaGreen",       Color.MediumSeaGreen,       /* #3CB371 */ "Green"),
        ("SeaGreen",             Color.SeaGreen,             /* #2E8B57 */ "Green"),
        ("ForestGreen",          Color.ForestGreen,          /* #228B22 */ "Green"),
        ("Green",                Color.Green,                /* #008000 */ "Green"),
        ("DarkGreen",            Color.DarkGreen,            /* #006400 */ "Green"),
        ("MediumAquaMarine",     Color.MediumAquaMarine,     /* #66CDAA */ "Cyan"),
        ("Aqua",                 Color.Aqua,                 /* #00FFFF */ "Cyan"),
        ("Cyan",                 Color.Cyan,                 /* #00FFFF */ "Cyan"),
        ("LightCyan",            Color.LightCyan,            /* #E0FFFF */ "Cyan"),
        ("PaleTurquoise",        Color.PaleTurquoise,        /* #AFEEEE */ "Cyan"),
        ("AquaMarine",           Color.AquaMarine,           /* #7FFFD4 */ "Cyan"),
        ("Turquoise",            Color.Turquoise,            /* #40E0D0 */ "Cyan"),
        ("MediumTurquoise",      Color.MediumTurquoise,      /* #48D1CC */ "Cyan"),
        ("DarkTurquoise",        Color.DarkTurquoise,        /* #00CED1 */ "Cyan"),
        ("LightSeaGreen",        Color.LightSeaGreen,        /* #20B2AA */ "Cyan"),
        ("CadetBlue",            Color.CadetBlue,            /* #5F9EA0 */ "Cyan"),
        ("DarkCyan",             Color.DarkCyan,             /* #008B8B */ "Cyan"),
        ("Teal",                 Color.Teal,                 /* #008080 */ "Cyan"),
        ("LightSteelBlue",       Color.LightSteelBlue,       /* #B0C4DE */ "Blue"),
        ("PowderBlue",           Color.PowderBlue,           /* #B0E0E6 */ "Blue"),
        ("LightBlue",            Color.LightBlue,            /* #ADD8E6 */ "Blue"),
        ("SkyBlue",              Color.SkyBlue,              /* #87CEEB */ "Blue"),
        ("LightSkyBlue",         Color.LightSkyBlue,         /* #87CEFA */ "Blue"),
        ("DeepSkyBlue",          Color.DeepSkyBlue,          /* #00BFFF */ "Blue"),
        ("DodgerBlue",           Color.DodgerBlue,           /* #1E90FF */ "Blue"),
        ("CornFlowerBlue",       Color.CornFlowerBlue,       /* #6495ED */ "Blue"),
        ("SteelBlue",            Color.SteelBlue,            /* #4682B4 */ "Blue"),
        ("RoyalBlue",            Color.RoyalBlue,            /* #4169E1 */ "Blue"),
        ("Blue",                 Color.Blue,                 /* #0000FF */ "Blue"),
        ("MediumBlue",           Color.MediumBlue,           /* #0000CD */ "Blue"),
        ("DarkBlue",             Color.DarkBlue,             /* #00008B */ "Blue"),
        ("Navy",                 Color.Navy,                 /* #000080 */ "Blue"),
        ("MidnightBlue",         Color.MidnightBlue,         /* #191970 */ "Blue"),
        ("Lavender",             Color.Lavender,             /* #E6E6FA */ "Purple"),
        ("Thistle",              Color.Thistle,              /* #D8BFD8 */ "Purple"),
        ("Plum",                 Color.Plum,                 /* #DDA0DD */ "Purple"),
        ("Violet",               Color.Violet,               /* #EE82EE */ "Purple"),
        ("Orchid",               Color.Orchid,               /* #DA70D6 */ "Purple"),
        ("Fuchsia",              Color.Fuchsia,              /* #FF00FF */ "Purple"),
        ("Magenta",              Color.Magenta,              /* #FF00FF */ "Purple"),
        ("MediumOrchid",         Color.MediumOrchid,         /* #BA55D3 */ "Purple"),
        ("MediumPurple",         Color.MediumPurple,         /* #9370DB */ "Purple"),
        ("BlueViolet",           Color.BlueViolet,           /* #8A2BE2 */ "Purple"),
        ("DarkViolet",           Color.DarkViolet,           /* #9400D3 */ "Purple"),
        ("DarkOrchid",           Color.DarkOrchid,           /* #9932CC */ "Purple"),
        ("DarkMagenta",          Color.DarkMagenta,          /* #8B008B */ "Purple"),
        ("Purple",               Color.Purple,               /* #800080 */ "Purple"),
        ("Indigo",               Color.Indigo,               /* #4B0082 */ "Purple"),
        ("DarkSlateBlue",        Color.DarkSlateBlue,        /* #483D8B */ "Purple"),
        ("SlateBlue",            Color.SlateBlue,            /* #6A5ACD */ "Purple"),
        ("MediumSlateBlue",      Color.MediumSlateBlue,      /* #7B68EE */ "Purple"),
        ("White",                Color.White,                /* #FFFFFF */ "White"),
        ("Snow",                 Color.Snow,                 /* #FFFAFA */ "White"),
        ("HoneyDew",             Color.HoneyDew,             /* #F0FFF0 */ "White"),
        ("MintCream",            Color.MintCream,            /* #F5FFFA */ "White"),
        ("Azure",                Color.Azure,                /* #F0FFFF */ "White"),
        ("AliceBlue",            Color.AliceBlue,            /* #F0F8FF */ "White"),
        ("GhostWhite",           Color.GhostWhite,           /* #F8F8FF */ "White"),
        ("WhiteSmoke",           Color.WhiteSmoke,           /* #F5F5F5 */ "White"),
        ("Seashell",             Color.Seashell,             /* #FFF5EE */ "White"),
        ("Beige",                Color.Beige,                /* #F5F5DC */ "White"),
        ("OldLace",              Color.OldLace,              /* #FDF5E6 */ "White"),
        ("FloralWhite",          Color.FloralWhite,          /* #FFFAF0 */ "White"),
        ("Ivory",                Color.Ivory,                /* #FFFFF0 */ "White"),
        ("AntiqueWhite",         Color.AntiqueWhite,         /* #FAEBD7 */ "White"),
        ("Linen",                Color.Linen,                /* #FAF0E6 */ "White"),
        ("LavenderBlush",        Color.LavenderBlush,        /* #FFF0F5 */ "White"),
        ("MistyRose",            Color.MistyRose,            /* #FFE4E1 */ "White"),
        ("Gainsboro",            Color.Gainsboro,            /* #DCDCDC */ "Gray/Black"),
        ("LightGray",            Color.LightGray,            /* #D3D3D3 */ "Gray/Black"),
        ("Silver",               Color.Silver,               /* #C0C0C0 */ "Gray/Black"),
        ("DarkGray",             Color.DarkGray,             /* #A9A9A9 */ "Gray/Black"),
        ("Gray",                 Color.Gray,                 /* #808080 */ "Gray/Black"),
        ("DimGray",              Color.DimGray,              /* #696969 */ "Gray/Black"),
        ("LightSlateGray",       Color.LightSlateGray,       /* #778899 */ "Gray/Black"),
        ("SlateGray",            Color.SlateGray,            /* #708090 */ "Gray/Black"),
        ("DarkSlateGray",        Color.DarkSlateGray,        /* #2F4F4F */ "Gray/Black"),
        ("Black",                Color.Black,                /* #000000 */ "Gray/Black"))

    object source extends ColumnDataSource {
        val names  = column(css3_colors.map(_._1))
        val colors = column(css3_colors.map(_._2))
        val groups = column(css3_colors.map(_._3))
    }

    import source.{names,colors,groups}

    val xdr = new FactorRange().factors(groups.value.distinct)
    val ydr = new FactorRange().factors(names.value.reverse)

    val plot = new Plot().title("CSS3 Color Names").x_range(xdr).y_range(ydr).width(600).height(2000)

    // TODO: categorical dimensions; using Column would cause type error
    val rect_glyph = new Rect().x('groups).y('names).width(1).height(1).fill_color(colors).line_color()
    val rect = new GlyphRenderer().data_source(source).glyph(rect_glyph)

    val x1axis = new CategoricalAxis().plot(plot).major_label_orientation(pi/4)
    plot.above := x1axis :: Nil
    val x2axis = new CategoricalAxis().plot(plot).major_label_orientation(pi/4)
    plot.below := x2axis :: Nil
    val yaxis = new CategoricalAxis().plot(plot)
    plot.left  := yaxis :: Nil

    plot.renderers := x1axis :: x2axis :: yaxis :: rect :: Nil

    val url = "http://www.colors.commutercreative.com/@names/"
    val tooltips = Tooltip(s"""Click the color to go to:<br /><a href="$url">$url</a>""")

    val tap = new TapTool().plot(plot).renderers(rect :: Nil).callback(new OpenURL().url(url))
    val hover = new HoverTool().plot(plot).renderers(rect :: Nil).tooltips(tooltips)
    plot.tools := List(tap, hover)

    val document = new Document(plot)
    val html = document.save("colors.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
