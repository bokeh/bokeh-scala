package io.continuum.bokeh

@model sealed abstract class Marker extends Glyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object size extends Spatial[Double](SpatialUnits.Screen) with NonNegative
}

@model class Asterisk extends Marker

@model class Circle extends Marker {
    object radius extends Spatial[Double](SpatialUnits.Data) with NonNegative
}

@model class CircleCross extends Marker

@model class CircleX extends Marker

@model class Cross extends Marker

@model class Diamond extends Marker

@model class DiamondCross extends Marker

@model class InvertedTriangle extends Marker

@model class Square extends Marker {
    object angle extends Angular[Double]
}

@model class SquareCross extends Marker

@model class SquareX extends Marker

@model class Triangle extends Marker

@model class PlainX extends Marker {
    override val typeName = "X"
}
