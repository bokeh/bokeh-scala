package io.continuum.bokeh

@model abstract class Layout extends Component {
    object width extends Field[Int]
    object height extends Field[Int]
}

@model abstract class BaseBox extends Layout {
    object children extends Field[List[Component]]
}

@model class HBox extends BaseBox

@model class VBox extends BaseBox
