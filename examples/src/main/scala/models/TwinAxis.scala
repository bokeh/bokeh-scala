package io.continuum.bokeh
package examples
package models

import breeze.linalg.linspace
import breeze.numerics.sin
import math.{Pi=>pi}

import thirdparty._

object TwinAxis extends Example with Tools {
    object source extends ColumnDataSource {
        val x  = column(-2*pi to 2*pi by 0.1 toArray)
        val y1 = column(sin(x.value))
        val y2 = column(linspace(0, 100, x.value.length))
    }

    import source.{x,y1,y2}

    val xdr = new Range1d().start(-6.5).end(6.5)
    val ydr = new Range1d().start(-1.1).end(1.1)

    val plot = new Plot()
        .title("Twin Axis Plot")
        .x_range(xdr)
        .y_range(ydr)
        .min_border(80)
        .tools(Pan|WheelZoom)
        .extra_y_ranges(Map("foo" -> new Range1d().start(0).end(100)))

    val xaxis = new LinearAxis().plot(plot)
    val y1axis = new LinearAxis().plot(plot)
    val y2axis = new LinearAxis().plot(plot).y_range_name("foo")

    plot.below := xaxis :: Nil
    plot.left  := y1axis :: y2axis :: Nil

    val circle1_glyph = new Circle().x(x).y(y1).fill_color(Color.Red).size(5).line_color(Color.Black)
    val circle1 = new GlyphRenderer().data_source(source).glyph(circle1_glyph)

    val circle2_glyph = new Circle().x(x).y(y2).fill_color(Color.Blue).size(5).line_color(Color.Black)
    val circle2 = new GlyphRenderer().data_source(source).glyph(circle2_glyph).y_range_name("foo")

    plot.renderers := xaxis :: y1axis :: y2axis :: circle1 :: circle2 :: Nil

    val document = new Document(plot)
    val html = document.save("twin_axis.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
