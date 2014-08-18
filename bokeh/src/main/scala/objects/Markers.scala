package io.continuum.bokeh

sealed abstract class Marker extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object size extends Spatial[Double](SpatialUnits.Screen) with NonNegative
}

class Circle extends Marker {
    object radius extends Spatial[Double](SpatialUnits.Data) with NonNegative
}

class Square extends Marker {
    object angle extends Angular[Double]
}

class Triangle extends Marker

class Cross extends Marker

class X extends Marker

class Diamond extends Marker

class InvertedTriangle extends Marker

class SquareX extends Marker

class Asterisk extends Marker

class DiamondCross extends Marker

class CircleCross extends Marker

class HexStar extends Marker

class SquareCross extends Marker

class CircleX extends Marker
