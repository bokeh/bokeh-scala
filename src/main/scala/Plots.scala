package org.continuumio.bokeh

class Plot extends Widget { plot =>
    object data_sources extends Field[List[DataSource]]

    object x_range extends Field[Range]
    object y_range extends Field[Range]

    object title extends Field[String]

    // object title_props extends Include(TextProps, prefix="title")
    // object outline_props extends Include(LineProps, prefix="outline")

    object renderers extends Field[List[Renderer]]
    object tools extends Field[List[Tool]] with ToolsField

    object plot_width extends Field[Int](600) with ReactiveField[Int] {
        val reactors = (plot.canvas_width := _) :: (plot.outer_width := _) :: Nil
    }
    object plot_height extends Field[Int](600) with ReactiveField[Int] {
        val reactors = (plot.canvas_height := _) :: (plot.outer_height := _) :: Nil
    }

    object canvas_width extends Field[Int](600)
    object canvas_height extends Field[Int](600)

    object outer_width extends Field[Int](600)
    object outer_height extends Field[Int](600)

    object min_border_top extends Field[Int](50)
    object min_border_bottom extends Field[Int](50)
    object min_border_left extends Field[Int](50)
    object min_border_right extends Field[Int](50)
    object min_border extends Field[Int](50)

    object border_symmetry extends Field[BorderSymmetry]

    object background_fill extends Field[Color](Color.White)
    object border_fill extends Field[Color](Color.White)

    object script_inject_snippet extends Field[String]
}

class GridPlot extends Plot {
    object children extends Field[List[List[Plot]]]
    object border_space extends Field[Int](0)
}

class MapOptions extends PlotObject with NoRefs {
    object lat extends Field[Double]
    object lng extends Field[Double]
    object zoom extends Field[Int](12)
}

class GMapPlot extends Plot {
    object map_options extends Field[MapOptions]

    override def scripts: List[xml.Node] =
        <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script> :: super.scripts
}
