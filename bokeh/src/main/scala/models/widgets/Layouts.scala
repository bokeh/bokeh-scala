package io.continuum.bokeh
package widgets

@model abstract class Layout extends Widget {
    object width extends Field[Int]
    object height extends Field[Int]
}

@model class HBox extends Layout {
    object children extends Field[List[Widget]]
}

@model class VBox extends Layout {
    object children extends Field[List[Widget]]
}
