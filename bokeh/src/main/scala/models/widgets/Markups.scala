package io.continuum.bokeh
package widgets

@model class Paragraph extends Widget {
    object text extends Field[String]
}

@model class PreText extends Paragraph
