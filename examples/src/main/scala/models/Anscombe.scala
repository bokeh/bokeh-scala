package io.continuum.bokeh
package examples
package models

import breeze.linalg.{DenseMatrix,linspace}

object Anscombe extends Example {
    val anscombe_quartet = DenseMatrix(
        (10.0,  8.04, 10.0, 9.14, 10.0,  7.46,  8.0,  6.58),
        ( 8.0,  6.95,  8.0, 8.14,  8.0,  6.77,  8.0,  5.76),
        (13.0,  7.58, 13.0, 8.74, 13.0, 12.74,  8.0,  7.71),
        ( 9.0,  8.81,  9.0, 8.77,  9.0,  7.11,  8.0,  8.84),
        (11.0,  8.33, 11.0, 9.26, 11.0,  7.81,  8.0,  8.47),
        (14.0,  9.96, 14.0, 8.10, 14.0,  8.84,  8.0,  7.04),
        ( 6.0,  7.24,  6.0, 6.13,  6.0,  6.08,  8.0,  5.25),
        ( 4.0,  4.26,  4.0, 3.10,  4.0,  5.39, 19.0, 12.50),
        (12.0, 10.84, 12.0, 9.13, 12.0,  8.15,  8.0,  5.56),
        ( 7.0,  4.82,  7.0, 7.26,  7.0,  6.42,  8.0,  7.91),
        ( 5.0,  5.68,  5.0, 4.74,  5.0,  5.73,  8.0,  6.89))

    object circles_source extends ColumnDataSource {
        val xi   = column(anscombe_quartet(::, 0))
        val yi   = column(anscombe_quartet(::, 1))
        val xii  = column(anscombe_quartet(::, 2))
        val yii  = column(anscombe_quartet(::, 3))
        val xiii = column(anscombe_quartet(::, 4))
        val yiii = column(anscombe_quartet(::, 5))
        val xiv  = column(anscombe_quartet(::, 6))
        val yiv  = column(anscombe_quartet(::, 7))
    }

    object lines_source extends ColumnDataSource {
        val x = column(linspace(-0.5, 20.5, 10))
        val y = column(x.value*0.5 + 3.0)
    }

    import lines_source.{x,y}

    val xdr = new Range1d().start(-0.5).end(20.5)
    val ydr = new Range1d().start(-0.5).end(20.5)

    def make_plot(title: String, xname: Symbol, yname: Symbol) = {
        val plot = new Plot()
            .x_range(xdr)
            .y_range(ydr)
            .title(title)
            .width(400)
            .height(400)
            .border_fill(Color.White)
            .background_fill("#e9e0db")
        val xaxis = new LinearAxis().plot(plot).axis_line_color()
        val yaxis = new LinearAxis().plot(plot).axis_line_color()
        plot.below <<= (xaxis :: _)
        plot.left <<= (yaxis :: _)
        val xgrid = new Grid().plot(plot).axis(xaxis).dimension(0)
        val ygrid = new Grid().plot(plot).axis(yaxis).dimension(1)
        val line_renderer = new GlyphRenderer()
            .data_source(lines_source)
            .glyph(new Line().x(x).y(y).line_color("#666699").line_width(2))
        val circle_renderer = new GlyphRenderer()
            .data_source(circles_source)
            .glyph(new Circle().x(xname).y(yname).size(12).fill_color("#cc6633").line_color("#cc6633").fill_alpha(50%%))
        plot.renderers := List(xaxis, yaxis, xgrid, ygrid, line_renderer, circle_renderer)
        plot
    }

    val I   = make_plot("I",   'xi,   'yi)
    val II  = make_plot("II",  'xii,  'yii)
    val III = make_plot("III", 'xiii, 'yiii)
    val IV  = make_plot("IV",  'xiv,  'yiv)

    val children = List(List(I, II), List(III, IV))
    val grid = new GridPlot().children(children).width(800)

    val document = new Document(grid)
    val html = document.save("anscombe.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
