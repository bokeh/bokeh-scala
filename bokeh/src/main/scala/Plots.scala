package io.continuum.bokeh

class Plot extends Widget { plot =>
    object data_sources extends Field[List[DataSource]]

    object x_range extends Field[Range]
    object y_range extends Field[Range]

    object title extends Field[String]

    // object title_props extends Include(TextProps, prefix="title")
    // object outline_props extends Include(LineProps, prefix="outline")

    object renderers extends Field[List[Renderer]]
    object tools extends Field[List[Tool]] with ToolsField

    object left extends Field[List[PlotObject]]
    object right extends Field[List[PlotObject]]
    object above extends Field[List[PlotObject]]
    object below extends Field[List[PlotObject]]

    object plot_width extends Field[Int](600)
    object plot_height extends Field[Int](600)

    object background_fill extends Field[Color](Color.White)
    object border_fill extends Field[Color](Color.White)

    object min_border_top extends Field[Int](50)
    object min_border_bottom extends Field[Int](50)
    object min_border_left extends Field[Int](50)
    object min_border_right extends Field[Int](50)
    object min_border extends Field[Int](50)

    object h_symmetry extends Field[Boolean](true)
    object v_symmetry extends Field[Boolean](false)
}

class MapOptions extends HasFields {
    object lat extends Field[Double]
    object lng extends Field[Double]
    object zoom extends Field[Int](12)
}

class GMapPlot extends Plot {
    object map_options extends Field[MapOptions]
}

class GridPlot extends Plot {
    object children extends Field[List[List[Plot]]]
    object border_space extends Field[Int](0)
}
