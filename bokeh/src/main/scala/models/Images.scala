package io.continuum.bokeh

@model class ImageSource extends Model {
    object url extends Field[String]
    object extra_url_vars extends Field[Map[String, String]]
}
