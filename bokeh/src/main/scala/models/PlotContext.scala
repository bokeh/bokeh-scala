package io.continuum.bokeh

@model class PlotContext extends Model {
    object children extends Field[List[Component]]
}
