package io.continuum.bokeh

import Json.Writer

object Figure {
    def apply(): Figure = {
        val fig = new Figure()

        val xaxis = new LinearAxis()
        val yaxis = new LinearAxis()
        val xgrid = new Grid().axis(xaxis).dimension(0)
        val ygrid = new Grid().axis(yaxis).dimension(1)

        fig.addLayout(xaxis, Place.Below)
        fig.addLayout(yaxis, Place.Left)

        fig.addLayout(xgrid, Place.Center)
        fig.addLayout(ygrid, Place.Center)

        fig
    }

    def apply(width: Int, height: Int): Figure = apply().width(width).height(height)
    def apply(title: String, width: Int, height: Int): Figure = apply(width, height).title(title)
}

@model class Figure extends Plot { plot =>
    final override val typeName = "Plot"

    type Cols = ColumnDataSource

    protected def _addGlyph[X, Y, T[X, Y] <: Glyph[X, Y]](source: ColumnDataSource, glyph: T[X, Y]): T[X, Y] = {
        plot.addGlyph(source, glyph)
        glyph
    }

    // annular_wedge(x, y, inner_radius, outer_radius, start_angle, end_angle)

    def annular_wedge[X, Y](x: Seq[X], y: Seq[Y]): AnnularWedge[X, Y] = ???

    // annulus(x, y, inner_radius, outer_radius)

    def annulus[X, Y](x: Seq[X], y: Seq[Y]): Annulus[X, Y] = ???

    // arc(x, y, radius, start_angle, end_angle)

    def arc[X, Y](x: Seq[X], y: Seq[Y]): Arc[X, Y] = ???

    // bezier(x0, y0, x1, y1, cx0, cy0, cx1, cy1)

    def bezier[X, Y](x0: Seq[X], y0: Seq[Y], x1: Seq[X],  y1: Seq[Y], cx0: Seq[X], cy0: Seq[Y], cx1: Seq[X], cy1: Seq[Y]): Bezier[X, Y] = ???

    // image(image, x, y, dw, dh)

    def image[X, Y](/* image: Seq[???], */ x: Seq[X], y: Seq[Y]): Image[X, Y] = ???

    // image_rgba(image, x, y, dw, dh)

    def image_rgba[X, Y](/* image: Seq[???], */ x: Seq[X], y: Seq[Y]): ImageRGBA[X, Y] = ???

    // image_url(url, x, y)

    def image_url[X, Y](url: Seq[String], x: Seq[X], y: Seq[Y]): ImageURL[X, Y] = ???

    // line(x, y)

    def line[Y:Scalar:Default:Writer](y: Seq[Y]): Line[Int, Y] = {
        line(0 until y.length, y)
    }

    def line[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: X, y: Y): Line[X, Y] = {
        new Line[X, Y].x(x).y(y)
    }

    def line[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: Seq[X], y: Y): Line[X, Y] = {
        val outer_x = x
        object source extends ColumnDataSource {
            val x = new Column('x, outer_x)
        }
        _addGlyph(source, new Line[X, Y].x(source.x).y(y))
    }

    def line[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: X, y: Seq[Y]): Line[X, Y] = {
        val outer_y = y
        object source extends ColumnDataSource {
            val y = new Column('y, outer_y)
        }
        _addGlyph(source, new Line[X, Y].x(x).y(source.y))
    }

    def line[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: Seq[X], y: Seq[Y]): Line[X, Y] = {
        val (outer_x, outer_y) = (x, y)
        object source extends ColumnDataSource {
            val x = new Column('x, outer_x)
            val y = new Column('y, outer_y)
        }
        _addGlyph(source, new Line[X, Y].x(source.x).y(source.y))
    }

    def line[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](xy: Seq[(X, Y)]): Line[X, Y] = {
        line(xy.map(_._1), xy.map(_._2))
    }

    def line[S <: Cols, M[_]: ArrayLike, N[_]: ArrayLike, X:Scalar:Default:Writer, Y:Scalar:Default:Writer]
            (x: S#Column[M, X], y: S#Column[N, Y])(implicit ev: x.parent.type =:= y.parent.type): Line[X, Y] = {
        _addGlyph(x.parent, new Line[X, Y].x(x).y(y))
    }

    def line[S <: Cols, M[_]: ArrayLike, N[_]: ArrayLike, X:Scalar:Default:Writer, Y:Scalar:Default:Writer]
            (source: S)(x: source.type => source.Column[M, X], y: source.type => source.Column[N, Y]): Line[X, Y] = {
        _addGlyph(source, new Line[X, Y].x(x(source)).y(y(source)))
    }

    // multi_line(xs, ys)

    def multi_line[X, Y](x: Seq[Seq[X]], y: Seq[Seq[Y]]): Patches[X, Y] = ???

    // oval(x, y, width, height)

    def oval[X, Y](x: Seq[X], y: Seq[Y], width: Seq[X], height: Seq[Y]): Oval[X, Y] = ???

    // patch(x, y)

    def patch[X, Y](x: Seq[X], y: Seq[Y]): Patch[X, Y] = ???

    // patches(xs, ys)

    def patches[X, Y](x: Seq[Seq[X]], y: Seq[Seq[Y]]): Patches[X, Y] = ???

    // quad(left, right, top, bottom)

    def quad[X, Y](left: Seq[X], right: Seq[X], bottom: Seq[Y], top: Seq[Y]): Patch[X, Y] = ???

    // quadratic(x0, y0, x1, y1, cx, cy)

    def quadratic[X, Y](x0: Seq[X], y0: Seq[Y], x1: Seq[X], y1: Seq[Y], cx: Seq[X], cy: Seq[Y]): Quadratic[X, Y] = ???

    // ray(x, y, length, angle)

    def ray[X, Y](x: Seq[X], y: Seq[Y], length: Seq[Double], angle: Seq[Double]): Ray[X, Y] = ???

    // rect(x, y, width, height)

    def rect[X, Y](x: Seq[X], y: Seq[Y], width: Seq[X], height: Seq[Y]): Rect[X, Y] = ???

    // segment(x0, y0, x1, y1)

    def segment[X, Y](x0: Seq[X], y0: Seq[Y], x1: Seq[X], y1: Seq[Y]): Rect[X, Y] = ???

    // text(x, y, text)

    def text[X, Y](x: Seq[X], y: Seq[Y], text: Seq[String]): Text[X, Y] = ???

    // wedge(x, y, radius, start_angle, end_angle)

    def wedge[X, Y](x: Seq[X], y: Seq[Y]): Wedge[X, Y] = ???

    trait MarkerApi {
        type MarkerType[X, Y] <: Marker[X, Y]

        protected def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer]: MarkerType[X, Y]

        def apply[Y:Scalar:Default:Writer](y: Seq[Y]): MarkerType[Int, Y] = {
            apply(0 until y.length, y)
        }

        def apply[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: X, y: Y): MarkerType[X, Y] = {
            newMarker[X, Y].x(x).y(y)
        }

        def apply[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: Seq[X], y: Y): MarkerType[X, Y] = {
            val outer_x = x
            object source extends ColumnDataSource {
                val x = new Column('x, outer_x)
            }
            _addGlyph(source, newMarker[X, Y].x(source.x).y(y))
        }
        def apply[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: X, y: Seq[Y]): MarkerType[X, Y] = {
            val outer_y = y
            object source extends ColumnDataSource {
                val y = new Column('y, outer_y)
            }
            _addGlyph(source, newMarker[X, Y].x(x).y(source.y))
        }

        def apply[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](x: Seq[X], y: Seq[Y]): MarkerType[X, Y] = {
            val (outer_x, outer_y) = (x, y)
            object source extends ColumnDataSource {
                val x = new Column('x, outer_x)
                val y = new Column('y, outer_y)
            }
            _addGlyph(source, newMarker[X, Y].x(source.x).y(source.y))
        }

        def apply[X:Scalar:Default:Writer, Y:Scalar:Default:Writer](xy: Seq[(X, Y)]): MarkerType[X, Y] = {
            apply(xy.map(_._1), xy.map(_._2))
        }

        def apply[S <: Cols, M[_]: ArrayLike, N[_]: ArrayLike, X:Scalar:Default:Writer, Y:Scalar:Default:Writer]
                (x: S#Column[M, X], y: S#Column[N, Y])(implicit ev: x.parent.type =:= y.parent.type): MarkerType[X, Y] = {
            _addGlyph(x.parent, newMarker[X, Y].x(x).y(y))
        }

        def apply[S <: Cols, M[_]: ArrayLike, N[_]: ArrayLike, X:Scalar:Default:Writer, Y:Scalar:Default:Writer]
                (source: S)(x: source.type => source.Column[M, X], y: source.type => source.Column[N, Y]): MarkerType[X, Y] = {
            _addGlyph(source, newMarker[X, Y].x(x(source)).y(y(source)))
        }
    }

    object asterisk extends MarkerApi {
        type MarkerType[X, Y] = Asterisk[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new Asterisk[X, Y]()
    }
    object circle extends MarkerApi {
        type MarkerType[X, Y] = Circle[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new Circle()
    }
    object circle_cross extends MarkerApi {
        type MarkerType[X, Y] = CircleCross[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new CircleCross()
    }
    object circle_x extends MarkerApi {
        type MarkerType[X, Y] = CircleX[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new CircleX()
    }
    object cross extends MarkerApi {
        type MarkerType[X, Y] = Cross[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new Cross()
    }
    object diamond extends MarkerApi {
        type MarkerType[X, Y] = Diamond[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new Diamond()
    }
    object diamond_cross extends MarkerApi {
        type MarkerType[X, Y] = DiamondCross[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new DiamondCross()
    }
    object inverted_triangle extends MarkerApi {
        type MarkerType[X, Y] = InvertedTriangle[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new InvertedTriangle()
    }
    object square extends MarkerApi {
        type MarkerType[X, Y] = Square[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new Square()
    }
    object square_cross extends MarkerApi {
        type MarkerType[X, Y] = SquareCross[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new SquareCross()
    }
    object square_x extends MarkerApi {
        type MarkerType[X, Y] = SquareX[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new SquareX()
    }
    object triangle extends MarkerApi {
        type MarkerType[X, Y] = Triangle[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new Triangle()
    }
    object plain_x extends MarkerApi {
        type MarkerType[X, Y] = PlainX[X, Y]
        def newMarker[X:Scalar:Default:Writer, Y:Scalar:Default:Writer] = new PlainX()
    }
}
