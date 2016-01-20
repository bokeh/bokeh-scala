package io.continuum.bokeh

@model abstract class Callback extends PlotObject

@model class OpenURL extends Callback {
    object url extends Field[String]("http://")
}

@model class CustomJS extends Callback {
    object args extends Field[Map[String, PlotObject]]
    object code extends Field[String]
    object lang extends Field[ScriptingLanguage](ScriptingLanguage.JavaScript)
}
