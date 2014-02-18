package org.continuumio.bokeh

class Plot extends PlotObject { plot =>
    object data_sources extends Field[List[DataSource]]
    object title extends Field[String]("Bokeh Plot")

    object x_range extends Field[Range]
    object y_range extends Field[Range]

    //// object outline_props extends Include(LineProps, prefix="outline")

    object renderers extends Field[List[Renderer]]
    object tools extends Field[List[Tool]] with ToolsField

    object width extends Field[Int](600) with ReactiveField[Int] {
        val reactors = (plot.canvas_width := _) :: (plot.outer_width := _) :: Nil
    }
    object height extends Field[Int](600) with ReactiveField[Int] {
        val reactors = (plot.canvas_height := _) :: (plot.outer_height := _) :: Nil
    }

    object background_fill extends Field[Color](Color.White)
    object border_fill extends Field[Color](Color.White)
    object canvas_width extends Field[Int](600)
    object canvas_height extends Field[Int](600)
    object outer_width extends Field[Int](600)
    object outer_height extends Field[Int](600)
    object min_border_top extends Field[Int](50)
    object min_border_bottom extends Field[Int](50)
    object min_border_left extends Field[Int](50)
    object min_border_right extends Field[Int](50)
    object min_border extends Field[Int](50)
    object script_inject_snippet extends Field[String]
}

class GridPlot extends Plot {
    object children extends Field[List[List[Plot]]]
    object border_space extends Field[Int](0)
}

class GMapPlot extends Plot {
    object center_lat extends Field[Double]
    object center_lng extends Field[Double]
    object zoom_level extends Field[Int](12)

    override def scripts: List[xml.Node] =
        <script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script> :: super.scripts
}
