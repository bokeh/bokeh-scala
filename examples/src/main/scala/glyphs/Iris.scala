package org.continuumio.bokeh.examples

import org.continuumio.bokeh._
import sampledata.iris.flowers

object Iris extends App {
    val colormap = Map("setosa" -> Color.Red, "versicolor" -> Color.Green, "virginica" -> Color.Blue)

    val source = new ColumnDataSource().data(Map())
        //"petal_length" -> flowers.petal_length,
        //"petal_width" -> flowers.petal_width,
        //"sepal_length" -> flowers.sepal_length,
        //"sepal_width" -> flowers.sepal_width,
        //"color" -> flowers.species.map(colormap _)))

    val xdr = new DataRange1d().sources(source.columns("petal_length") :: Nil)
    val ydr = new DataRange1d().sources(source.columns("petal_width") :: Nil)

    val circle = new Circle()
        .x("petal_length")
        .y("petal_width")
        .fill_color("color")
        .fill_alpha(0.2)
        .size(10)
        .line_color("color")

    val glyph_renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(circle)

    val plot = new Plot().x_range(xdr).y_range(ydr).data_sources(source :: Nil).title("Iris Data")

    val xaxis = new LinearAxis().plot(plot).dimension(0).location(Location.Min)
        .axis_label("petal length")/*.bounds((1.0, 7.0))*/.major_tick_in(0)
    val yaxis = new LinearAxis().plot(plot).dimension(1).location(Location.Min)
        .axis_label("petal width")/*.bounds((0.0, 2.5))*/.major_tick_in(0)

    val xgrid = new Grid().plot(plot).dimension(0)
    val ygrid = new Grid().plot(plot).dimension(1)

    val pantool = new PanTool().plot(plot)
    val wheelzoomtool = new WheelZoomTool().plot(plot)

    plot.renderers := List(glyph_renderer)
    plot.tools := List(pantool, wheelzoomtool)

    val session = new HTMLFileSession("iris.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
