package io.continuum.bokeh
package widgets

@model abstract class AbstractGroup extends Widget {
    object labels extends Field[List[String]]
}

@model abstract class Group extends AbstractGroup {
    object inline extends Field[Boolean](false)
}

@model abstract class ButtonGroup extends AbstractGroup {
    object `type` extends Field[ButtonType]
}

@model class CheckboxGroup extends Group {
    object active extends Field[List[Int]]
}

@model class RadioGroup extends Group {
    object active extends Field[Int]
}

@model class CheckboxButtonGroup extends ButtonGroup {
    object active extends Field[List[Int]]
}

@model class RadioButtonGroup extends ButtonGroup {
    object active extends Field[Int]
}
