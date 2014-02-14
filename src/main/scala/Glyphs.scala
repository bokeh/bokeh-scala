package org.continuumio.bokeh

sealed abstract class BaseGlyph extends PlotObject with NoRefs {
    override def viewModel: String = snakify(super.viewModel)

    object visible extends Field[Boolean]
    //object margin extends Field[Size]
    //object halign extends Field[Align]
    //object valign extends Field[Align]

    object radius_units extends Field[Units]
    object length_units extends Field[Units]
    object angle_units extends Field[AngleUnits]
    object start_angle_units extends Field[AngleUnits]
    object end_angle_units extends Field[AngleUnits]
}

class AnnularWedge extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec
    object y extends DataSpec
    object inner_radius extends DataSpec // with Radius
    object outer_radius extends DataSpec // with Radius
    object start_angle extends DataSpec
    object end_angle extends DataSpec
    object direction extends Field[Direction]
}

class Annulus extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec
    object y extends DataSpec
    object inner_radius extends DataSpec // with Radius
    object outer_radius extends DataSpec // with Radius
}

class Arc extends BaseGlyph with LineProps {
    object x extends DataSpec
    object y extends DataSpec
    object radius extends DataSpec // with Radius
    object start_angle extends DataSpec
    object end_angle extends DataSpec
    object direction extends Field[Direction]
}

class Bezier extends BaseGlyph with LineProps {
    object x0 extends DataSpec
    object y0 extends DataSpec
    object x1 extends DataSpec
    object y1 extends DataSpec
    object cx0 extends DataSpec
    object cy0 extends DataSpec
    object cx1 extends DataSpec
    object cy1 extends DataSpec
}

class Image extends BaseGlyph {
    object image extends GenericDataSpec[List[List[Int]]]
    object x extends DataSpec
    object y extends DataSpec
    object dw extends DataSpec
    object dh extends DataSpec
    object palette extends DataSpec
}

class ImageURI extends BaseGlyph {
    object x extends DataSpec
    object y extends DataSpec
    object angle extends DataSpec
    object url extends GenericDataSpec[String]
}

class ImageRGBA extends BaseGlyph {
    object image extends DataSpec
    object x extends DataSpec
    object y extends DataSpec
    object dw extends DataSpec
    object dh extends DataSpec
}

class Line extends BaseGlyph with LineProps {
    object x extends DataSpec
    object y extends DataSpec
}

class MultiLine extends BaseGlyph with LineProps {
    object xs extends DataSpec
    object ys extends DataSpec
}

class Oval extends BaseGlyph with FillProps with LineProps {
    object width extends DataSpec
    object height extends DataSpec
    object angle extends DataSpec
}

class Patch extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec
    object y extends DataSpec
}

class Patches extends BaseGlyph with LineProps with FillProps {
    object xs extends DataSpec
    object ys extends DataSpec
}

class Quad extends BaseGlyph with FillProps with LineProps {
    object left extends DataSpec
    object right extends DataSpec
    object bottom extends DataSpec
    object top extends DataSpec
}

class Quadratic extends BaseGlyph with LineProps {
    object x0 extends DataSpec
    object y0 extends DataSpec
    object x1 extends DataSpec
    object y1 extends DataSpec
    object cx extends DataSpec
    object cy extends DataSpec
}

class Ray extends BaseGlyph with LineProps {
    object x extends DataSpec
    object y extends DataSpec
    object angle extends DataSpec
    object length extends DataSpec
}

class Rect extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec
    object y extends DataSpec
    object width extends DataSpec
    object height extends DataSpec
    object angle extends DataSpec
}

class Segment extends BaseGlyph with LineProps {
    object x0 extends DataSpec
    object y0 extends DataSpec
    object x1 extends DataSpec
    object y1 extends DataSpec
}

class Text extends BaseGlyph with TextProps {
    object x extends DataSpec
    object y extends DataSpec
    object text extends GenericDataSpec[String]
    object angle extends DataSpec
}

class Wedge extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec
    object y extends DataSpec
    object radius extends DataSpec // with Radius
    object start_angle extends DataSpec
    object end_angle extends DataSpec
    object direction extends Field[Direction]
}

trait Marker extends FillProps with LineProps { self: BaseGlyph =>
    object x extends DataSpec
    object y extends DataSpec
    object size extends DataSpec {
        // override val units = Units.Screen
        // override val default = 4
        // override val min_value = 0
    }
}

class Circle extends BaseGlyph with Marker {
    // object radius extends DataSpec(units="data", default=4, min_value=0)
}

class Square extends BaseGlyph with Marker {
    object angle extends DataSpec
}

class Triangle extends BaseGlyph with Marker

class Cross extends BaseGlyph with Marker

class Xmarker extends BaseGlyph with Marker {
    override val viewModel: String = "x"
}

class Diamond extends BaseGlyph with Marker

class InvertedTriangle extends BaseGlyph with Marker

class SquareX extends BaseGlyph with Marker

class Asterisk extends BaseGlyph with Marker

class DiamondCross extends BaseGlyph with Marker

class CircleCross extends BaseGlyph with Marker

class HexStar extends BaseGlyph with Marker

class SquareCross extends BaseGlyph with Marker

class CircleX extends BaseGlyph with Marker
