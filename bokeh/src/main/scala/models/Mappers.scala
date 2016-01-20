package io.continuum.bokeh

@model abstract class ColorMapper extends Model

@model class LinearColorMapper extends ColorMapper {
    object palette extends Field[Seq[Color]](Palette.Greys9)

    object low extends Field[Double]
    object high extends Field[Double]

    object reserve_color extends Field[Color](Color.Transparent)
    object reserve_val extends Field[Double]
}
