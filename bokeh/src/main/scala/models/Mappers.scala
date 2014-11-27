package io.continuum.bokeh

@model abstract class ColorMapper extends PlotObject

@model class LinearColorMapper extends ColorMapper {
    // TODO: object palette extends Field[Array[Color]]
    object low extends Field[Double]
    object high extends Field[Double]

    object reserve_color extends Field[Color](Color.Transparent)
    object reserve_val extends Field[Double] // TODO: None
}
