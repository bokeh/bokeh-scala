package io.continuum.bokeh

@model abstract class BaseGlyph extends HasFields {
    override def typeName: String = Utils.snakify(super.typeName)

    object visible extends Field[Boolean]
    // TODO: object margin extends Field[Size]
    // TODO: object halign extends Field[Align]
    // TODO: object valign extends Field[Align]

    object radius_units extends Field[SpatialUnits]
    object length_units extends Field[SpatialUnits]
    object angle_units extends Field[AngularUnits]
    object start_angle_units extends Field[AngularUnits]
    object end_angle_units extends Field[AngularUnits]
}

@model class AnnularWedge extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object inner_radius extends Spatial[Double] with NonNegative
    object outer_radius extends Spatial[Double] with NonNegative
    object start_angle extends Angular[Double]
    object end_angle extends Angular[Double]
    object direction extends Field[Direction]
}

@model class Annulus extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object inner_radius extends Spatial[Double] with NonNegative
    object outer_radius extends Spatial[Double] with NonNegative
}

@model class Arc extends BaseGlyph with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object radius extends Spatial[Double] with NonNegative
    object start_angle extends Angular[Double]
    object end_angle extends Angular[Double]
    object direction extends Field[Direction]
}

@model class Bezier extends BaseGlyph with LineProps {
    object x0 extends Spatial[Double]
    object y0 extends Spatial[Double]
    object x1 extends Spatial[Double]
    object y1 extends Spatial[Double]
    object cx0 extends Spatial[Double]
    object cy0 extends Spatial[Double]
    object cx1 extends Spatial[Double]
    object cy1 extends Spatial[Double]
}

@model class Image extends BaseGlyph {
    object image extends Vectorized[Seq[Seq[Int]]]
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object dw extends Spatial[Double] with NonNegative
    object dh extends Spatial[Double] with NonNegative
    object palette extends Vectorized[Double]
    object dilate extends Field[Boolean]
}

@model class ImageURL extends BaseGlyph {
    object url extends Vectorized[String]
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object w extends Spatial[Double] with NonNegative
    object h extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
    object dilate extends Field[Boolean]
    object anchor extends Field[Anchor]
}

@model class ImageRGBA extends BaseGlyph {
    object image extends Vectorized[Seq[Seq[Double]]]
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object dw extends Spatial[Double] with NonNegative
    object dh extends Spatial[Double] with NonNegative
    object dilate extends Field[Boolean]
}

@model class Line extends BaseGlyph with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
}

@model class MultiLine extends BaseGlyph with LineProps {
    object xs extends Spatial[Seq[Double]]
    object ys extends Spatial[Seq[Double]]
}

@model class Oval extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object width extends Spatial[Double] with NonNegative
    object height extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
}

@model class Patch extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
}

@model class Patches extends BaseGlyph with LineProps with FillProps {
    object xs extends Spatial[Seq[Double]]
    object ys extends Spatial[Seq[Double]]
}

@model class Quad extends BaseGlyph with FillProps with LineProps {
    object left extends Spatial[Double]
    object right extends Spatial[Double]
    object bottom extends Spatial[Double]
    object top extends Spatial[Double]
}

@model class Quadratic extends BaseGlyph with LineProps {
    object x0 extends Spatial[Double]
    object y0 extends Spatial[Double]
    object x1 extends Spatial[Double]
    object y1 extends Spatial[Double]
    object cx extends Spatial[Double]
    object cy extends Spatial[Double]
}

@model class Ray extends BaseGlyph with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object angle extends Angular[Double]
    object length extends Spatial[Double](SpatialUnits.Screen) with NonNegative
}

@model class Rect extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object width extends Spatial[Double] with NonNegative
    object height extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
    object dilate extends Field[Boolean]
}

@model class Segment extends BaseGlyph with LineProps {
    object x0 extends Spatial[Double]
    object y0 extends Spatial[Double]
    object x1 extends Spatial[Double]
    object y1 extends Spatial[Double]
}

@model class Text extends BaseGlyph with TextProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object text extends Vectorized[String]
    object angle extends Angular[Double]
}

@model class Wedge extends BaseGlyph with FillProps with LineProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object radius extends Spatial[Double] with NonNegative
    object start_angle extends Angular[Double]
    object end_angle extends Angular[Double]
    object direction extends Field[Direction]
}

@model class Gear extends BaseGlyph with LineProps with FillProps {
    object x extends Spatial[Double]
    object y extends Spatial[Double]
    object angle extends Angular[Double]
    object module extends Spatial[Double] with NonNegative
    object teeth extends Vectorized[Int] // TODO: with NonNegative
    object pressure_angle extends Angular[Double](20, AngularUnits.Deg)
    object shaft_size extends Spatial[Double](0.3) with NonNegative
    object internal extends Vectorized[Boolean](false)
}
