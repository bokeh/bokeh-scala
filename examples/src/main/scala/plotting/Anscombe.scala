package io.continuum.bokeh
package examples
package plotting

import breeze.linalg.{DenseMatrix,DenseVector,linspace}

import thirdparty._

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

    object circles extends ColumnDataSource {
        val xi   = column(anscombe_quartet(::, 0))
        val yi   = column(anscombe_quartet(::, 1))
        val xii  = column(anscombe_quartet(::, 2))
        val yii  = column(anscombe_quartet(::, 3))
        val xiii = column(anscombe_quartet(::, 4))
        val yiii = column(anscombe_quartet(::, 5))
        val xiv  = column(anscombe_quartet(::, 6))
        val yiv  = column(anscombe_quartet(::, 7))
    }

    object lines extends ColumnDataSource {
        val x = column(linspace(-0.5, 20.5, 10))
        val y = column(x.value*0.5 + 3.0)
    }

    object lines1 extends ColumnDataSource {
        val x = column(linspace(-1.5, 20.5, 10))
        val y = column(x.value*0.7 + 1.0)
    }

    val xdr = Range1d(-0.5, 20.5)
    val ydr = Range1d(-0.5, 20.5)

    type Col = circles.Column[DenseVector, Double]

    def make_plot(title: String, x_col: Col, y_col: Col) = {
        val fig = Figure(title, 400, 400)
            .x_range(xdr)
            .y_range(ydr)
            .border_fill_color(Color.White)
            .background_fill_color("#e9e0db")
        // fig.x_axis.axis_line_color()
        // fig.y_axis.axis_line_color()
        fig.line(lines)(_.x, _.y).line_color("#666699").line_width(2)
        fig.circle(x_col, y_col).size(12).fill_color("#cc6633").line_color("#cc6633").fill_alpha(50%%)
        fig
    }

    val I   = make_plot("I",   circles.xi,   circles.yi)
    val II  = make_plot("II",  circles.xii,  circles.yii)
    val III = make_plot("III", circles.xiii, circles.yiii)
    val IV  = make_plot("IV",  circles.xiv,  circles.yiv)

    val grid = GridPlot((I,   II),
                        (III, IV)).width(800)

    val html = grid.save("anscombe.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
