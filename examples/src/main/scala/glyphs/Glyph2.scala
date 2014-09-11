package io.continuum.bokeh
package examples
package glyphs

import breeze.linalg.DenseVector
import breeze.numerics.{sin,cos}
import math.{Pi=>pi}

object Glyph2 extends Example {
    val x = DenseVector(-2*pi to 2*pi by 0.1 toArray)
    val y = sin(x)
    val r = (cos(x) + 1.0)*6.0 + 6.0

    val source = new ColumnDataSource().data(Map('x -> x, 'y -> y, 'r -> r))

    val xdr = new DataRange1d().sources(source.columns('x) :: Nil)
    val ydr = new DataRange1d().sources(source.columns('y) :: Nil)

    val circle = new Circle()
        .x('x)
        .y('y)
        .radius('r, SpatialUnits.Screen)
        .fill_color(Color.Red)
        .line_color(Color.Black)

    val glyph_renderer = new Glyph()
        .data_source(source)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr).title("glyph2")

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    val xaxis = new LinearAxis().plot(plot)
    val yaxis = new LinearAxis().plot(plot)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val xgrid = new Grid().plot(plot).axis(xaxis).dimension(0)
    val ygrid = new Grid().plot(plot).axis(yaxis).dimension(1)

    plot.renderers := List(xaxis, yaxis, xgrid, ygrid, glyph_renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val document = new Document(plot)
    val html = document.save("glyph2.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
