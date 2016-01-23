package io.continuum.bokeh
package examples
package glyphs

import play.api.libs.json.Json.{arr,obj}

object Maps extends Example {
    val x_range = new Range1d()
    val y_range = new Range1d()

    val map_options = new GMapOptions()
        .lat(30.2861)
        .lng(-97.7394)
        .zoom(13)
        .map_type(MapType.Roadmap)
        .styles(arr(
            obj(
                "featureType" -> "administrative",
                "elementType" -> "all",
                "stylers"     -> arr(obj("visibility" -> "on"), obj("lightness" -> 33))),
            obj(
                "featureType" -> "landscape",
                "elementType" -> "all",
                "stylers"     -> arr(obj("color" -> "#f2e5d4"))),
            obj(
                "featureType" -> "poi.park",
                "elementType" -> "geometry",
                "stylers"     -> arr(obj("color" -> "#c5dac6"))),
            obj(
                "featureType" -> "poi.park",
                "elementType" -> "labels",
                "stylers"     -> arr(obj("visibility" -> "on"), obj("lightness" -> 20))),
            obj(
                "featureType" -> "road",
                "elementType" -> "all",
                "stylers"     -> arr(obj("lightness" -> 20))),
            obj(
                "featureType" -> "road.highway",
                "elementType" -> "geometry",
                "stylers"     -> arr(obj("color" -> "#c5c6c6"))),
            obj(
                "featureType" -> "road.arterial",
                "elementType" -> "geometry",
                "stylers"     -> arr(obj("color" -> "#e4d7c6"))),
            obj(
                "featureType" -> "road.local",
                "elementType" -> "geometry",
                "stylers"     -> arr(obj("color" -> "#fbfaf7"))),
            obj(
                "featureType" -> "water",
                "elementType" -> "all",
                "stylers"     -> arr(obj("visibility" -> "on"), obj("color" -> "#acbcc9")))))

    val plot = new GMapPlot()
        .x_range(x_range)
        .y_range(y_range)
        .map_options(map_options)
        .title("Austin")

    val xaxis = new LinearAxis().plot(plot).axis_label("lat").major_tick_in(0).formatter(new NumeralTickFormatter().format("0.000"))
    plot.addLayout(xaxis, Layout.Below)

    val yaxis = new LinearAxis().plot(plot).axis_label("lon").major_tick_in(0).formatter(new PrintfTickFormatter().format("%.3f"))
    plot.addLayout(yaxis, Layout.Left)

    val pan = new PanTool().plot(plot)
    val zoom = new WheelZoomTool().plot(plot)
    val select = new BoxSelectTool().plot(plot)

    plot.tools <<= (pan :: zoom :: select :: _)

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
