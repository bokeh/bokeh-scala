package io.continuum.bokeh

@model class GridPlot extends Plot {
    object children extends Field[List[List[Plot]]]
    object border_space extends Field[Int](0)
}
