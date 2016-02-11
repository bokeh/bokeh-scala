package io.continuum.bokeh
package examples
package models

import sampledata.world_cities

object MapsCities extends Example with Tools {
    val x_range = Range1d(-160, 160)
    val y_range = Range1d(-80, 80)

    val map_options = new GMapOptions().lat(15).lng(0).zoom(2)

    val plot = new GMapPlot()
        .x_range(x_range)
        .y_range(y_range)
        .width(1000)
        .height(500)
        .map_options(map_options)
        .title("Cities of the world with a population over 5,000 people.")
        .webgl(true)
        .tools(Pan|WheelZoom)

    object source extends ColumnDataSource {
        val lng = column(world_cities.map(_.lng))
        val lat = column(world_cities.map(_.lat))
    }

    import source.{lng,lat}

    val circle = new Circle().x(lng).y(lat).size(5)
        .line_color().fill_color(Color.FireBrick).fill_alpha(0.2)
    plot.addGlyph(source, circle)

    val document = new Document(plot) /* TODO title="Google Maps - World cities Example"*/
    val html = document.save("maps_cities.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
