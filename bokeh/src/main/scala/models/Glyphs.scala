package io.continuum.bokeh

import Json.Writer

@model abstract class Glyph[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Model with Vectorization {
    object visible extends Field[Boolean](true)
}

@model class AnnularWedge[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object inner_radius extends Spatial[Double] with NonNegative
    object outer_radius extends Spatial[Double] with NonNegative
    object start_angle extends Angular[Double]
    object end_angle extends Angular[Double]
    object direction extends Field[Direction]
}

@model class Annulus[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object inner_radius extends Spatial[Double] with NonNegative
    object outer_radius extends Spatial[Double] with NonNegative
}

@model class Arc[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object radius extends Spatial[Double] with NonNegative
    object start_angle extends Angular[Double]
    object end_angle extends Angular[Double]
    object direction extends Field[Direction]
}

@model class Bezier[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object x0 extends Vectorized[X]
    object y0 extends Vectorized[Y]
    object x1 extends Vectorized[X]
    object y1 extends Vectorized[Y]
    object cx0 extends Vectorized[X]
    object cy0 extends Vectorized[Y]
    object cx1 extends Vectorized[X]
    object cy1 extends Vectorized[Y]
}

@model class ImageRGBA[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] {
    object image extends Vectorized[Array[Double]]
    object rows extends Vectorized[Int]
    object cols extends Vectorized[Int]
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object dw extends Spatial[Double] with NonNegative
    object dh extends Spatial[Double] with NonNegative
    object dilate extends Field[Boolean]

    def image[T[_]: MatrixLike](value: T[Double]): SelfType = {
        val (image, rows, cols) = implicitly[MatrixLike[T]].data(value)

        this.image := image
        this.rows  := rows
        this.cols  := cols

        this
    }
}

@model class Image[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends ImageRGBA[X, Y] {
    object color_mapper extends Field[ColorMapper]
}

@model class ImageURL[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] {
    object url extends Vectorized[String]
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object w extends Spatial[Double] with NonNegative
    object h extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
    object global_alpha extends Field[Percent](1.0)
    object dilate extends Field[Boolean]
    object anchor extends Field[Anchor]
    object retry_attempts extends Field[Int](0)
    object retry_timeout extends Field[Int](0)
}

@model class Line[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
}

@model class MultiLine[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object xs extends Vectorized[List[X]]
    object ys extends Vectorized[List[Y]]
}

@model class Oval[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object width extends Spatial[Double] with NonNegative
    object height extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
}

@model class Patch[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
}

@model class Patches[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps with FillProps {
    object xs extends Vectorized[List[X]]
    object ys extends Vectorized[List[Y]]
}

@model class Quad[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object left extends Vectorized[X]
    object right extends Vectorized[X]
    object bottom extends Vectorized[Y]
    object top extends Vectorized[Y]
}

@model class Quadratic[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object x0 extends Vectorized[X]
    object y0 extends Vectorized[Y]
    object x1 extends Vectorized[X]
    object y1 extends Vectorized[Y]
    object cx extends Vectorized[X]
    object cy extends Vectorized[Y]
}

@model class Ray[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object length extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
}

@model class Rect[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object width extends Spatial[Double] with NonNegative
    object height extends Spatial[Double] with NonNegative
    object angle extends Angular[Double]
    object dilate extends Field[Boolean]
}

@model class Segment[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps {
    object x0 extends Vectorized[X]
    object y0 extends Vectorized[Y]
    object x1 extends Vectorized[X]
    object y1 extends Vectorized[Y]
}

@model class Text[X: Scalar:Default:Writer, Y: Scalar:Default:Writer] extends Glyph[X, Y] with TextProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object text extends Vectorized[String]
    object angle extends Angular[Double]
    object x_offset extends Spatial[Double]
    object y_offset extends Spatial[Double]
}

@model class Wedge[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with FillProps with LineProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object radius extends Spatial[Double] with NonNegative
    object start_angle extends Angular[Double]
    object end_angle extends Angular[Double]
    object direction extends Field[Direction]
}

@model class Gear[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] extends Glyph[X, Y] with LineProps with FillProps {
    object x extends Vectorized[X]
    object y extends Vectorized[Y]
    object angle extends Angular[Double]
    object module extends Vectorized[Double] with NonNegative
    object teeth extends Vectorized[Int]                                // TODO: with NonNegative
    object pressure_angle extends Angular[Double](20, AngularUnits.Deg)
    object shaft_size extends Vectorized[Double](0.3) with NonNegative
    object internal extends Vectorized[Boolean](false)
}
