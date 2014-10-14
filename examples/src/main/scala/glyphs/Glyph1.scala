package io.continuum.bokeh
package examples
package glyphs

import math.{Pi=>pi,sin}

object Glyph1 extends Example {
    val x = -2*pi to 2*pi by 0.1
    val y = x.map(sin)

    val source = new ColumnDataSource()
        .addColumn('x, x)
        .addColumn('y, y)

    val xdr = new DataRange1d().sources(source.columns('x) :: Nil)
    val ydr = new DataRange1d().sources(source.columns('y) :: Nil)

    val circle = new Circle().x('x).y('y).fill_color(Color.Red).size(5).line_color(Color.Black)
    val renderer = new GlyphRenderer().data_source(source).glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr)

    val xaxis = new LinearAxis().plot(plot)
    val yaxis = new LinearAxis().plot(plot)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(xaxis, yaxis, renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val document = new Document(plot)
    val html = document.save("glyph1.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
