package org.continuumio.bokeh

sealed abstract class Marker extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[this.type](this)
    object y extends DataSpec[this.type](this)
    object size extends DataSpec[this.type](this) {
        // override val units = Units.Screen
        // override val default = 4
        // override val min_value = 0
    }
}

class Circle extends Marker {
    // object radius extends DataSpec(units="data", default=4, min_value=0)
}

class Square extends Marker {
    object angle extends DataSpec[this.type](this)
}

class Triangle extends Marker

class Cross extends Marker

class Xmarker extends Marker

class Diamond extends Marker

class InvertedTriangle extends Marker

class SquareX extends Marker

class Asterisk extends Marker

class DiamondCross extends Marker

class CircleCross extends Marker

class HexStar extends Marker

class SquareCross extends Marker

class CircleX extends Marker
