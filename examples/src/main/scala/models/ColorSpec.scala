package io.continuum.bokeh
package examples
package models

object ColorSpec extends Example {
    val colors: List[Color] = List(RGB(0, 100, 120), Color.Green, Color.Blue, "#2c7fb8", RGBA(120, 230, 150, 0.5))

    object source extends ColumnDataSource {
        val x     = column(Array[Double](1, 2, 3, 4, 5))
        val y     = column(Array[Double](5, 4, 3, 2, 1))
        val color = column(colors)
    }

    import source.{x,y,color}

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    val circle = new Circle().x(x).y(y).size(15).fill_color(color).line_color(Color.Black)

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
    val html = document.save("colorspec.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
