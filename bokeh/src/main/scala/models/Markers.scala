package io.continuum.bokeh

@model sealed abstract class Marker /*[X:Numeric, Y:Numeric]*/ extends Glyph with FillProps with LineProps {
    object x extends Spatial[X]
    object y extends Spatial[Y]
    object size extends Spatial[Double](SpatialUnits.Screen) with NonNegative
    object angle extends Angular[Double]
}

@model class Asterisk extends Marker

@model class Circle extends Marker {
    object radius extends Spatial[Double] with NonNegative
    object radius_dimension extends Field[Dimension]
}

@model class CircleCross extends Marker

@model class CircleX extends Marker

@model class Cross extends Marker

@model class Diamond extends Marker

@model class DiamondCross extends Marker

@model class InvertedTriangle extends Marker

@model class Square extends Marker

@model class SquareCross extends Marker

@model class SquareX extends Marker

@model class Triangle extends Marker

@model class PlainX extends Marker {
    override val typeName = "X"
}
