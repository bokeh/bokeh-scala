package io.continuum.bokeh
package examples
package models

import sampledata.iris.flowers

object Iris extends Example {
    val colormap = Map[String, Color]("setosa" -> Color.Red, "versicolor" -> Color.Green, "virginica" -> Color.Blue)

    object source extends ColumnDataSource {
        val petal_length = column(flowers.petal_length)
        val petal_width  = column(flowers.petal_width)
        val sepal_length = column(flowers.sepal_length)
        val sepal_width  = column(flowers.sepal_width)
        val color        = column(flowers.species.map(colormap))
    }

    import source.{petal_length,petal_width,sepal_length,sepal_width,color}

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    val circle = Circle()
        .x(petal_length)
        .y(petal_width)
        .fill_color(color)
        .fill_alpha(0.2)
        .size(10)
        .line_color(color)

    val renderer = new GlyphRenderer()
        .data_source(source)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr).title("Iris Data")

    val xaxis = new LinearAxis().plot(plot)
        .axis_label("petal length").bounds((1.0, 7.0)).major_tick_in(0)
    val yaxis = new LinearAxis().plot(plot)
        .axis_label("petal width").bounds((0.0, 2.5)).major_tick_in(0)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    val xgrid = new Grid().plot(plot).axis(xaxis).dimension(0)
    val ygrid = new Grid().plot(plot).axis(yaxis).dimension(1)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(xaxis, yaxis, xgrid, ygrid, renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val document = new Document(plot)
    val html = document.save("iris.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
