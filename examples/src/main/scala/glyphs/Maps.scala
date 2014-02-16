package org.continuumio.bokeh.examples

import org.continuumio.bokeh._

object Maps extends App {
    val x_range = new Range1d()
    val y_range = new Range1d()

    val plot = new GMapPlot()
        .x_range(x_range)
        .y_range(y_range)
        .center_lat(30.2861)
        .center_lng(-97.7394)
        .zoom_level(15)
        .data_sources(Nil)
        .canvas_width(600)
        .canvas_height(600)
        .outer_width(600)
        .outer_height(600)
        .title("Austin")

    val select_tool = new BoxSelectTool()
    val overlay = new BoxSelectionOverlay().tool(select_tool)

    plot.renderers <<= (overlay :: _)
    plot.tools <<= (select_tool :: _)

    val xgrid = new Grid().plot(plot).dimension(0)
    val ygrid = new Grid().plot(plot).dimension(1)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.tools <<= (pantool :: wheelzoomtool :: _)

    val source = new ColumnDataSource()
        .addColumn('lat, Array(30.2861, 30.2855, 30.2869))
        .addColumn('lon, Array(-97.7394, -97.7390, -97.7405))
        .addColumn('fill, Array(Color.Orange, Color.Blue, Color.Green))

    val circle_renderer = new Glyph()
        .data_source(source)
        .xdata_range(x_range)
        .ydata_range(y_range)
        .glyph(new Circle().x('lon).y('lat).fill_color('fill).size(15)
               .radius_units(Units.Screen).line_color(Color.Black))

    plot.data_sources := source :: Nil
    plot.renderers <<= (circle_renderer :: _)

    val session = new HTMLFileSession("maps.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
