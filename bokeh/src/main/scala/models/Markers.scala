package io.continuum.bokeh

@fields sealed abstract class Marker extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object size extends Spatial[Double](SpatialUnits.Screen) with NonNegative
}

@fields class Circle extends Marker {
    object radius extends Spatial[Double](SpatialUnits.Data) with NonNegative
}

@fields class Square extends Marker {
    object angle extends Angular[Double]
}

@fields class Triangle extends Marker

@fields class Cross extends Marker

@fields class X extends Marker

@fields class Diamond extends Marker

@fields class InvertedTriangle extends Marker

@fields class SquareX extends Marker

@fields class Asterisk extends Marker

@fields class DiamondCross extends Marker

@fields class CircleCross extends Marker

@fields class HexStar extends Marker

@fields class SquareCross extends Marker

@fields class CircleX extends Marker
