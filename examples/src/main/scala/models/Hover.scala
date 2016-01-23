package io.continuum.bokeh
package examples
package models

import breeze.linalg.DenseVector

object Hover extends Example with LinAlg with Tools {
    val (xx, yy) = meshgrid(0.0 to 100.0 by 4.0,
                            0.0 to 100.0 by 4.0)

    object source extends ColumnDataSource {
        val x      = column(xx.flatten())
        val y      = column(yy.flatten())
        val inds   = column(x.value.mapPairs { (k, _) => k.toString } toArray)
        val radii  = column(DenseVector.rand(x.value.length)*0.4 + 1.7)
        val colors = column {
            val reds = (x.value*2.0 + 50.0).map(_.toInt).toArray
            val greens = (y.value*2.0 + 30.0).map(_.toInt).toArray
            reds.zip(greens).map { case (r, g) => RGB(r, g, 150): Color }
        }
    }

    import source.{x,y,inds,radii,colors}

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    val plot = new Plot()
        .title("Color Scatter Example")
        .x_range(xdr)
        .y_range(ydr)
        .tools(Pan|WheelZoom|BoxZoom|Reset|PreviewSave)

    val circle = new Circle()
        .x(x)
        .y(y)
        .radius(radii)
        .fill_color(colors)
        .fill_alpha(0.6)
        .line_color()

    val circle_renderer = new GlyphRenderer()
        .data_source(source)
        .glyph(circle)

    val text = new Text()
        .x(x)
        .y(y)
        .text(inds)
        .angle(0.0)
        .text_alpha(0.5)
        .text_font_size(5 pt)
        .text_baseline(TextBaseline.Middle)
        .text_align(TextAlign.Center)

    val text_renderer = new GlyphRenderer()
        .data_source(source)
        .glyph(text)

    val hover = new HoverTool()
        .tooltips(Tooltip(
            "index"         -> "$index",
            "fill_color"    -> "$color[hex,swatch]:fill_color",
            "radius"        -> "@radii",
            "data (x, y)"   -> "(@x, @y)",
            "cursor (x, y)" -> "($x, $y)",
            "canvas (x, y)" -> "($sx, $sy)"))

    val xaxis = new LinearAxis().plot(plot)
    val yaxis = new LinearAxis().plot(plot)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)

    plot.renderers := List(xaxis, yaxis, circle_renderer, text_renderer)
    plot.tools <<= (_ :+ hover)

    val document = new Document(plot)
    val html = document.save("hover.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
