package io.continuum.bokeh

@model abstract class Component extends PlotObject {
    object disabled extends Field[Boolean](false)
}
