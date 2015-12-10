package io.continuum.bokeh
package examples
package glyphs

import sampledata.iris.flowers

import math.{Pi=>pi}

object IrisSplom extends Example {
    val colormap = Map("setosa" -> Color.Red, "versicolor" -> Color.Green, "virginica" -> Color.Blue)

    object source extends ColumnDataSource {
        val petal_length = column(flowers.petal_length)
        val petal_width = column(flowers.petal_width)
        val sepal_length = column(flowers.sepal_length)
        val sepal_width = column(flowers.sepal_width)
        val color = column(flowers.species.map(colormap))
    }

    val columns = List('petal_length, 'petal_width, 'sepal_width, 'sepal_length)

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    def make_plot(xname: Symbol, yname: Symbol, xax: Boolean=false, yax: Boolean=false, text: Option[String]=None) = {
        val plot = new Plot()
            .x_range(xdr)
            .y_range(ydr)
            .background_fill("#efe8e2")
            .border_fill(Color.White)
            .title("")
            .min_border(2)
            .h_symmetry(false)
            .v_symmetry(false)
            .width(250)
            .height(250)

        val xaxis = new LinearAxis().plot(plot)
        val yaxis = new LinearAxis().plot(plot)
        plot.below <<= (xaxis :: _)
        plot.left <<= (yaxis :: _)

        val xgrid = new Grid().plot(plot).axis(xaxis).dimension(0)
        val ygrid = new Grid().plot(plot).axis(yaxis).dimension(1)

        val axes = List(xax.option(xaxis), yax.option(yaxis)).flatten
        val grids = List(xgrid, ygrid)

        val circle = new Circle()
            .x(xname)
            .y(yname)
            .fill_color('color)
            .fill_alpha(0.2)
            .size(4)
            .line_color('color)

        val renderer = new GlyphRenderer()
            .data_source(source)
            .glyph(circle)

        xdr.renderers <<= (renderer :: _)
        ydr.renderers <<= (renderer :: _)

        val pantool = new PanTool().plot(plot)
        val wheelzoomtool = new WheelZoomTool().plot(plot)

        plot.renderers := axes ++ grids ++ List(renderer)
        plot.tools := List(pantool, wheelzoomtool)

        text.foreach { text =>
            val text_glyph = new Text()
                .x(0).x_offset( 125)
                .y(0).y_offset(-125)
                .text(text.replaceAll("_", " "))
                .angle(pi/4)
                .text_color("#ffaaaa").text_alpha(0.7)
                .text_font_size(28 pt).text_font_style(FontStyle.Bold)
                .text_baseline(TextBaseline.Top).text_align(TextAlign.Center)
            val text_renderer = new GlyphRenderer()
                .glyph(text_glyph)

            plot.renderers := text_renderer :: plot.renderers.value
        }

        plot
    }

    val xattrs = columns
    val yattrs = xattrs.reverse

    val plots: List[List[Plot]] = yattrs.map { y =>
        xattrs.map { x =>
            val xax = y == yattrs.last
            val yax = x == xattrs(0)
            val text = if (x == y) Some(x.name) else None
            make_plot(x, y, xax, yax, text)
        }
    }

    val grid = new GridPlot().children(plots).title("iris_splom")

    val document = new Document(grid)
    val html = document.save("iris_splom.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
