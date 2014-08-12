package io.continuum.bokeh
package examples
package glyphs

import breeze.numerics.sin
import math.{Pi=>pi}

object Glyph1 extends App {
    val x = -2*pi to 2*pi by 0.1 toArray
    val y = sin(x)

    val source = new ColumnDataSource()
        .addColumn('x, x)
        .addColumn('y, y)

    val xdr = new DataRange1d().sources(source.columns('x) :: Nil)
    val ydr = new DataRange1d().sources(source.columns('y) :: Nil)

    val circle = new Circle().x('x).y('y).fill_color(Color.Red).size(5).line_color(Color.Black)

    val glyph_renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr).data_sources(source :: Nil)

    val xaxis = new LinearAxis().plot(plot).dimension(0).location(Location.Bottom)
    val yaxis = new LinearAxis().plot(plot).dimension(1).location(Location.Left)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(xaxis, yaxis, glyph_renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val document = new Document(plot)
    val html = document.save("glyph1.html")
    println(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
