package io.continuum.bokeh

@model abstract class Action extends PlotObject

@model class OpenURL extends Action {
    object url extends Field[String]("http://")
}

@model class Callback extends Action {
    object args extends Field[Map[String, PlotObject]]
    object code extends Field[String]
}
