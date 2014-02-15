package org.continuumio.bokeh.examples

import org.continuumio.bokeh._

object ColorSpec extends App {
    val source = new ColumnDataSource()
        .addColumn("x", Array(1, 2, 3, 4, 5))
        .addColumn("y", Array(5, 4, 3, 2, 1))
        .addColumn("color", List(/*RGB(0, 100, 120),*/ Color.Green, Color.Blue/*, "#2c7fb8", RGBA(120, 230, 150, 0.5)*/))

    val xdr = new DataRange1d().sources(source.columns("x") :: Nil)
    val ydr = new DataRange1d().sources(source.columns("y") :: Nil)

    val circle = new Circle().x("x").y("y").size(15).fill_color("color").line_color(Color.Black)

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

    plot.renderers := List(glyph_renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val session = new HTMLFileSession("colorspec.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
