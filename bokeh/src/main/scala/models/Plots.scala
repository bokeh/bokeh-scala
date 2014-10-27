package io.continuum.bokeh

@model class Plot extends Widget {
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

    object left extends Field[List[PlotObject]]
    object right extends Field[List[PlotObject]]
    object above extends Field[List[PlotObject]]
    object below extends Field[List[PlotObject]]

    object toolbar_location extends Field[Location](Location.Above)

    object plot_width extends Field[Int](600)
    object plot_height extends Field[Int](600)

    object background_fill extends Field[Color](Color.White)
    object border_fill extends Field[Color](Color.White)

    object min_border_top extends Field[Int]
    object min_border_bottom extends Field[Int]
    object min_border_left extends Field[Int]
    object min_border_right extends Field[Int]
    object min_border extends Field[Int]

    object h_symmetry extends Field[Boolean](true)
    object v_symmetry extends Field[Boolean](false)
}
