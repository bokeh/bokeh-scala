package io.continuum.bokeh
package widgets

@model abstract class AbstractButton extends Widget {
    object label extends Field[String]("Button")
    object icon extends Field[AbstractIcon]
    object `type` extends Field[ButtonType]
    object callback extends Field[Callback]
}

@model class Button extends AbstractButton {
    object clicks extends Field[Int](0)
}

@model class Toggle extends AbstractButton {
    object active extends Field[Boolean](false)
}

@model class Dropdown extends AbstractButton {
    object action extends Field[String]
    object default_action extends Field[String]
    object menu extends Field[List[(String, String)]]
}
