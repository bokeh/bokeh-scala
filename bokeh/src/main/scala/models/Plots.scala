package io.continuum.bokeh

@model class Plot extends Component {
    object title extends Field[String]("")

    title = include[TextProps]
    outline = include[LineProps]

    object x_range extends Field[Range]
    object y_range extends Field[Range]

    object extra_x_ranges extends Field[Map[String, Range]]
    object extra_y_ranges extends Field[Map[String, Range]]

    object x_mapper_type extends Field[String]("auto")
    object y_mapper_type extends Field[String]("auto")

    object renderers extends Field[List[Renderer]]
    object tools extends Field[List[Tool]] with ToolsField

    object tool_events extends Field[ToolEvents](() => new ToolEvents())

    object left extends Field[List[Renderer]]
    object right extends Field[List[Renderer]]
    object above extends Field[List[Renderer]]
    object below extends Field[List[Renderer]]

    object toolbar_location extends Field[Location](Location.Above)
    object logo extends Field[Logo](Logo.Normal)

    object plot_width extends Field[Int](600)
    object plot_height extends Field[Int](600)

    def width = plot_width
    def height = plot_height

    object background_fill_color extends Field[Color](Color.White)
    object border_fill_color extends Field[Color](Color.White)

    object min_border_top extends Field[Int]
    object min_border_bottom extends Field[Int]
    object min_border_left extends Field[Int]
    object min_border_right extends Field[Int]
    object min_border extends Field[Int]

    object h_symmetry extends Field[Boolean](true)
    object v_symmetry extends Field[Boolean](false)

    object lod_factor extends Field[Int](10)
    object lod_threshold extends Field[Int](2000)
    object lod_interval extends Field[Int](300)
    object lod_timeout extends Field[Int](500)

    object webgl extends Field[Boolean](false)

    object responsive extends Field[Boolean](false)

    def addGlyph(glyph: Glyph[_, _]): GlyphRenderer = {
        addGlyph(new ColumnDataSource(), glyph)
    }

    def addGlyph(source: DataSource, glyph: Glyph[_, _]): GlyphRenderer = {
        val renderer = new GlyphRenderer().data_source(source).glyph(glyph)
        renderers <<= (_ :+ renderer)
        renderer
    }

    def addLayout(renderer: Renderer, place: Place): Renderer = {
        renderer match {
            case (ref: BackRef) => ref.plot := this
        }

        place match {
            case Place.Left   => left  <<= (renderer +: _)
            case Place.Right  => right <<= (renderer +: _)
            case Place.Above  => above <<= (renderer +: _)
            case Place.Below  => below <<= (renderer +: _)
            case Place.Center =>
        }

        renderers <<= (_ :+ renderer)
        renderer
    }
}

trait BackRef { self: Model =>
    object plot extends Field[Plot]
}

object GridPlot {
    def apply[R](rows: R*)(implicit r: Row[R, Plot]): GridPlot = {
        new GridPlot().children(rows.toList.map(r.toList(_)))
    }

    def apply(row: Plot*): GridPlot = {
        new GridPlot().children(List(row.toList))
    }
}

@model class GridPlot extends Plot {
    object children extends Field[List[List[Plot]]]
    object border_space extends Field[Int](0)
}
