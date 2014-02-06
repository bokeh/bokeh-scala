package org.continuumio.bokeh

sealed abstract class Marker extends BaseGlyph with FillProps with LineProps {
    //x = DataSpec
    //y = DataSpec
    //size = DataSpec(units="screen", default=4, min_value=0)
}

class Circle extends Marker {
    //radius = DataSpec(units="data", default=4, min_value=0)
}

class Square extends Marker {
    //angle = DataSpec
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
