package org.continuumio.bokeh.examples

import breeze.numerics.sin
import math.{Pi=>pi}

import org.continuumio.bokeh._

object Glyph1 extends App {
    val x = -2*pi to 2*pi by 0.1 toArray
    val y = sin(x)

    val source = new ColumnDataSource().data(Map()) // "x" -> x, "y" -> y))

    val xdr = new DataRange1d().sources(source.columns("x") :: Nil)
    val ydr = new DataRange1d().sources(source.columns("y") :: Nil)

    val circle = new Circle().x("x").y("y").fill_color(Color.Red).size(5).line_color(Color.Black)

    val glyph_renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr).data_sources(source :: Nil)

    val xaxis = new LinearAxis().plot(plot).dimension(0).location(Location.Min)
    val yaxis = new LinearAxis().plot(plot).dimension(1).location(Location.Min)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(glyph_renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val session = new HTMLFileSession("glyph1.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
