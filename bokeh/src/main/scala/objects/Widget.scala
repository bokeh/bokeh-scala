package io.continuum.bokeh

@fields abstract class Widget extends PlotObject {
    object disabled extends Field[Boolean](false)
}
