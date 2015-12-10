package io.continuum.bokeh
package widgets

@model abstract class Markup extends Widget

@model class Paragraph extends Markup {
    object text extends Field[String]
}

@model class PreText extends Paragraph
