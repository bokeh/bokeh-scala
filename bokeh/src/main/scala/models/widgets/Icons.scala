package io.continuum.bokeh
package widgets

@model class AbstractIcon extends Widget

@model class Icon extends AbstractIcon {
    object name extends Field[NamedIcon]
    object size extends Field[Double] with NonNegative
    object flip extends Field[Flip]
    object spin extends Field[Boolean](false)
}
