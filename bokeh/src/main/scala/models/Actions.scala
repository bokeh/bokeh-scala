package io.continuum.bokeh

@model abstract class Action extends PlotObject

@model class OpenURL extends Action {
    object url extends Field[String]("http://")
}
