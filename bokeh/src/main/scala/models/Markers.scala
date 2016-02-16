package io.continuum.bokeh

import Json.Writer

@model sealed abstract class Marker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer]
        extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Spatial[X]
    object y extends Spatial[Y]
    object size extends Spatial[Double](SpatialUnits.Screen) with NonNegative
    object angle extends Angular[Double]
}

@model class Asterisk[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class Circle[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y] {
    object radius extends Spatial[Double] with NonNegative
    object radius_dimension extends Field[Dimension]
}

@model class CircleCross[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class CircleX[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class Cross[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class Diamond[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class DiamondCross[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class InvertedTriangle[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class Square[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class SquareCross[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class SquareX[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class Triangle[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y]

@model class PlainX[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Marker[X, Y] {
    override val typeName = "X"
}

object Asterisk {
    def apply() = new Asterisk[Double, Double]
}
object Circle {
    def apply() = new Circle[Double, Double]
}
object CircleCross {
    def apply() = new CircleCross[Double, Double]
}
object CircleX {
    def apply() = new CircleX[Double, Double]
}
object Cross {
    def apply() = new Cross[Double, Double]
}
object Diamond {
    def apply() = new Diamond[Double, Double]
}
object DiamondCross {
    def apply() = new DiamondCross[Double, Double]
}
object InvertedTriangle {
    def apply() = new InvertedTriangle[Double, Double]
}
object Square {
    def apply() = new Square[Double, Double]
}
object SquareCross {
    def apply() = new SquareCross[Double, Double]
}
object SquareX {
    def apply() = new SquareX[Double, Double]
}
object Triangle {
    def apply() = new Triangle[Double, Double]
}
object PlainX {
    def apply() = new PlainX[Double, Double]
}
