package io.continuum.bokeh
package examples
package glyphs

object Maps extends Example {
    val x_range = new Range1d()
    val y_range = new Range1d()

    val map_options = new GMapOptions()
        .lat(30.2861)
        .lng(-97.7394)
        .zoom(15)
        .map_type(MapType.Satellite)

    val plot = new GMapPlot()
        .x_range(x_range)
        .y_range(y_range)
        .map_options(map_options)
        .title("Austin")

    val select_tool = new BoxSelectTool()
    val overlay = new BoxSelectionOverlay().tool(select_tool)

    plot.renderers <<= (overlay :: _)
    plot.tools <<= (select_tool :: _)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.tools <<= (pantool :: wheelzoomtool :: _)

    object source extends ColumnDataSource {
        val lat  = column(Array(30.2861, 30.2855, 30.2869))
        val lon  = column(Array(-97.7394, -97.7390, -97.7405))
        val fill = column(Array[Color](Color.Orange, Color.Blue, Color.Green))
    }

    import source.{lat,lon,fill}

    val circle = new Circle()
        .x(lon)
        .y(lat)
        .fill_color(fill)
        .size(15)
        .line_color(Color.Black)

    val renderer = new GlyphRenderer()
        .data_source(source)
        .glyph(circle)

    plot.renderers <<= (renderer :: _)

    val document = new Document(plot)
    val html = document.save("maps.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
