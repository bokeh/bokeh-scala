package org.continuumio.bokeh.examples

import math.{Pi=>pi}
import breeze.linalg.linspace
import breeze.numerics.sin

import org.continuumio.bokeh._

object Line extends App {
    val x = linspace(-2*pi, 2*pi, 1000)
    val y = sin(x)

    val source = new ColumnDataSource()
        .addColumn("x", x)
        .addColumn("y", y)

    val xdr = new DataRange1d().sources(source.columns("x") :: Nil)
    val ydr = new DataRange1d().sources(source.columns("y") :: Nil)

    val line_glyph = new Line().x("x").y("y").line_color(Color.Blue)

    val renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(line_glyph)

    val plot = new Plot().x_range(xdr).y_range(ydr).data_sources(source :: Nil)

    val xaxis = new LinearAxis().plot(plot).dimension(0).location(Location.Bottom)
    val yaxis = new LinearAxis().plot(plot).dimension(1).location(Location.Left)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(xaxis, yaxis, renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val session = new HTMLFileSession("line.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
