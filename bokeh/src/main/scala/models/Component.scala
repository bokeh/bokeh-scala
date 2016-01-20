package io.continuum.bokeh

@model abstract class Component extends Model {
    object disabled extends Field[Boolean](false)
}
