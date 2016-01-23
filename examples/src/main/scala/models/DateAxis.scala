package io.continuum.bokeh
package examples
package glyphs

import math.{Pi=>pi,sin}

object DateAxis extends Example {
    val now = System.currentTimeMillis.toDouble/1000
    val x   = -2*pi to 2*pi by 0.1

    object source extends ColumnDataSource {
        val times = column(x.indices.map(3600000.0*_ + now))
        val y     = column(x.map(sin))
    }

    import source.{times,y}

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    val circle = new Circle().x(times).y(y).fill_color(Color.Red).size(5).line_color(Color.Black)

    val renderer = new GlyphRenderer()
        .data_source(source)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr)

    val xaxis = new DatetimeAxis().plot(plot)
    val yaxis = new LinearAxis().plot(plot)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(xaxis, yaxis, renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val document = new Document(plot)
    val html = document.save("dateaxis.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
