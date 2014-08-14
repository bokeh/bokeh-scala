package io.continuum.bokeh

abstract class BaseGlyph extends HasFields {
    override def typeName: String = Utils.snakify(super.typeName)

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
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object inner_radius extends DataSpec[Double] // with Radius
    object outer_radius extends DataSpec[Double] // with Radius
    object start_angle extends DataSpec[Double]
    object end_angle extends DataSpec[Double]
    object direction extends Field[Direction]
}

class Annulus extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object inner_radius extends DataSpec[Double] // with Radius
    object outer_radius extends DataSpec[Double] // with Radius
}

class Arc extends BaseGlyph with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object radius extends DataSpec[Double] // with Radius
    object start_angle extends DataSpec[Double]
    object end_angle extends DataSpec[Double]
    object direction extends Field[Direction]
}

class Bezier extends BaseGlyph with LineProps {
    object x0 extends DataSpec[Double]
    object y0 extends DataSpec[Double]
    object x1 extends DataSpec[Double]
    object y1 extends DataSpec[Double]
    object cx0 extends DataSpec[Double]
    object cy0 extends DataSpec[Double]
    object cx1 extends DataSpec[Double]
    object cy1 extends DataSpec[Double]
}

class Image extends BaseGlyph {
    object image extends DataSpec[Seq[Seq[Int]]]
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object dw extends DataSpec[Double]
    object dh extends DataSpec[Double]
    object palette extends DataSpec[Double]
    object dilate extends Field[Boolean]
}

class ImageURL extends BaseGlyph {
    object url extends DataSpec[String]
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object w extends DataSpec[Double]
    object h extends DataSpec[Double]
    object angle extends DataSpec[Double]
    object dilate extends Field[Boolean]
    object anchor extends Field[Anchor]
}

class ImageRGBA extends BaseGlyph {
    object image extends DataSpec[Seq[Seq[Double]]]
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object dw extends DataSpec[Double]
    object dh extends DataSpec[Double]
    object dilate extends Field[Boolean]
}

class Line extends BaseGlyph with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
}

class MultiLine extends BaseGlyph with LineProps {
    object xs extends DataSpec[Seq[Double]]
    object ys extends DataSpec[Seq[Double]]
}

class Oval extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object width extends DataSpec[Double]
    object height extends DataSpec[Double]
    object angle extends DataSpec[Double]
}

class Patch extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
}

class Patches extends BaseGlyph with LineProps with FillProps {
    object xs extends DataSpec[Seq[Double]]
    object ys extends DataSpec[Seq[Double]]
}

class Quad extends BaseGlyph with FillProps with LineProps {
    object left extends DataSpec[Double]
    object right extends DataSpec[Double]
    object bottom extends DataSpec[Double]
    object top extends DataSpec[Double]
}

class Quadratic extends BaseGlyph with LineProps {
    object x0 extends DataSpec[Double]
    object y0 extends DataSpec[Double]
    object x1 extends DataSpec[Double]
    object y1 extends DataSpec[Double]
    object cx extends DataSpec[Double]
    object cy extends DataSpec[Double]
}

class Ray extends BaseGlyph with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object angle extends DataSpec[Double]
    object length extends DataSpec[Double]
}

class Rect extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object width extends DataSpec[Double]
    object height extends DataSpec[Double]
    object angle extends DataSpec[Double]
    object dilate extends Field[Boolean]
}

class Segment extends BaseGlyph with LineProps {
    object x0 extends DataSpec[Double]
    object y0 extends DataSpec[Double]
    object x1 extends DataSpec[Double]
    object y1 extends DataSpec[Double]
}

class Text extends BaseGlyph with TextProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object text extends DataSpec[String]
    object angle extends DataSpec[Double]
}

class Wedge extends BaseGlyph with FillProps with LineProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object radius extends DataSpec[Double]             // TODO: with Radius
    object start_angle extends DataSpec[Double]
    object end_angle extends DataSpec[Double]
    object direction extends Field[Direction]
}

class Gear extends BaseGlyph with LineProps with FillProps {
    object x extends DataSpec[Double]
    object y extends DataSpec[Double]
    object angle extends DataSpec[Double]
    object module extends DataSpec[Double]             // TODO: module >= 0
    object teeth extends DataSpec[Int]
    object pressure_angle extends DataSpec[Double](20) // TODO: units = Some(AngleUnits.Deg)
    object shaft_size extends DataSpec[Double](0.3)    // TODO: shaft_size >= 0
    object internal extends DataSpec[Boolean]
}
