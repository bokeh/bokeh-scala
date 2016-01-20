package io.continuum.bokeh

@model class PlotContext extends PlotObject {
    object children extends Field[List[Component]]
}
