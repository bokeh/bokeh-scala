package io.continuum.bokeh
package examples
package models

import breeze.linalg.linspace
import breeze.numerics.{sin,cos,tan}
import math.{Pi=>pi}

import thirdparty._

object GridExample extends Example {
    object source extends ColumnDataSource {
        val x  = column(linspace(-2*pi, 2*pi, 1000))
        val y1 = column(sin(x.value))
        val y2 = column(cos(x.value))
        val y3 = column(tan(x.value))
        val y4 = column(sin(x.value) :* cos(x.value))
    }

    def make_plot[M[_]](
            x: source.Column[M, Double], y: source.Column[M, Double],
            line_color: Color,
            _xdr: Option[Range]=None, _ydr: Option[Range]=None) = {

        val xdr = _xdr getOrElse new DataRange1d()
        val ydr = _ydr getOrElse new DataRange1d()

        val plot = new Plot().x_range(xdr).y_range(ydr)

        val xaxis = new LinearAxis().plot(plot)
        val yaxis = new LinearAxis().plot(plot)
        plot.below <<= (xaxis :: _)
        plot.left <<= (yaxis :: _)

        val pantool = new PanTool().plot(plot)
        val wheelzoomtool = new WheelZoomTool().plot(plot)

        val renderer = new GlyphRenderer()
            .data_source(source)
            .glyph(new Line().x(x).y(y).line_color(line_color))

        plot.renderers := List(xaxis, yaxis, renderer)
        plot.tools := List(pantool, wheelzoomtool)

        plot
    }

    import source.{x,y1,y2,y3,y4}

    val plot1 = make_plot(x, y1, Color.Blue)
    val plot2 = make_plot(x, y2, Color.Red, _xdr=plot1.x_range.valueOpt)
    val plot3 = make_plot(x, y3, Color.Green)
    val plot4 = make_plot(x, y4, Color.Black)

    val children = List(List(plot1, plot2), List(plot3, plot4))
    val grid = new GridPlot().children(children)

    val document = new Document(grid)
    val html = document.save("grid.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
