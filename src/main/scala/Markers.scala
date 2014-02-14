package org.continuumio.bokeh

sealed abstract class Marker extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object size extends DataSpec[Double] {
        // override val units = Units.Screen
        // override val default = 4
        // override val min_value = 0
    }
}

class Circle extends Marker {
    // object radius extends DataSpec[Double](units="data", default=4, min_value=0)
}

class Square extends Marker {
    object angle extends DataSpec[Double]
}

class Triangle extends Marker

class Cross extends Marker

class Xmarker extends Marker {
    override val viewModel: String = "x"
}

class Diamond extends Marker

class InvertedTriangle extends Marker

class SquareX extends Marker

class Asterisk extends Marker

class DiamondCross extends Marker

class CircleCross extends Marker

class HexStar extends Marker

class SquareCross extends Marker

class CircleX extends Marker
