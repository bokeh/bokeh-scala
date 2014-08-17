package io.continuum.bokeh

sealed abstract class Marker extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object size extends DataSpec[Double](Units.Screen) with NonNegative
}

class Circle extends Marker {
    object radius extends DataSpec[Double](Units.Data) with NonNegative
}

class Square extends Marker {
    object angle extends DataSpec[Double]
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
