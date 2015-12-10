package io.continuum.bokeh
package widgets

@model class Dialog extends Widget {
    object visible extends Field[Boolean](false)
    object closable extends Field[Boolean](true)
    object title extends Field[String]
    object content extends Field[String]
    object buttons extends Field[List[String]]
}
