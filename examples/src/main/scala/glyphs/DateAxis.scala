package io.continuum.bokeh
package examples
package glyphs

import breeze.linalg.{linspace,DenseVector}
import breeze.numerics.sin
import math.{Pi=>pi}

object DateAxis extends App {
    val x = -2*pi to 2*pi by 0.1 toArray
    val y = sin(x)

    val now = System.currentTimeMillis.toDouble/1000
    val times = DenseVector(0.0 until x.length by 1.0 toArray)*3600000.0 + now

    val source = new ColumnDataSource().addColumn('times, times).addColumn('y, y)

    val xdr = new DataRange1d().sources(source.columns('times) :: Nil)
    val ydr = new DataRange1d().sources(source.columns('y) :: Nil)

    val circle = new Circle().x('times).y('y).fill_color(Color.Red).size(5).line_color(Color.Black)

    val glyph_renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr).data_sources(source :: Nil)

    val xaxis = new DatetimeAxis().plot(plot).dimension(0).location(Location.Min)
    val yaxis = new LinearAxis().plot(plot).dimension(1).location(Location.Min)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(xaxis, yaxis, glyph_renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val document = new Document(plot)
    val html = document.save("dateaxis.html")
    println(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
