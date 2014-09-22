package io.continuum.bokeh

@model abstract class Widget extends PlotObject {
    object disabled extends Field[Boolean](false)
}
