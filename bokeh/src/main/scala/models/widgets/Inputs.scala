package io.continuum.bokeh
package widgets

@model abstract class InputWidget[T:Default:Json.Writer] extends Widget {
    object title extends Field[String]
    object name extends Field[String]
    object value extends Field[T]
}

@model class TextInput extends InputWidget[String]

@model class AutocompleteInput extends TextInput {
    object completions extends Field[List[String]]
}

@model class Select extends InputWidget[String] {
    object options extends Field[List[String]]
}

@model class Slider extends InputWidget[Double] {
    object start extends Field[Double]
    object end extends Field[Double]
    object step extends Field[Double]
    object orientation extends Field[Orientation]
}
