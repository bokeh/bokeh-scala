package io.continuum.bokeh
package widgets

@model class Panel extends Widget {
    object title extends Field[String]
    object child extends Field[Widget]
    object closable extends Field[Boolean](false)
}

@model class Tabs extends Widget {
    object tabs extends Field[List[Panel]]
    object active extends Field[Int](0)
}
