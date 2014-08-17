package io.continuum.bokeh
package examples
package glyphs

import sampledata.iris.flowers

import math.{Pi=>pi}

object IrisSplom extends Example {
    val colormap = Map("setosa" -> Color.Red, "versicolor" -> Color.Green, "virginica" -> Color.Blue)

    val source = new ColumnDataSource()
        .addColumn('petal_length, flowers.petal_length)
        .addColumn('petal_width, flowers.petal_width)
        .addColumn('sepal_length, flowers.sepal_length)
        .addColumn('sepal_width, flowers.sepal_width)
        .addColumn('color, flowers.species.map(colormap))

    val text_source = new ColumnDataSource()
        .addColumn('xcenter, Array(125))
        .addColumn('ycenter, Array(145))

    val columns = List('petal_length, 'petal_width, 'sepal_width, 'sepal_length)

    val xdr = new DataRange1d().sources(source.columns(columns: _*) :: Nil)
    val ydr = new DataRange1d().sources(source.columns(columns: _*) :: Nil)

    def make_plot(xname: Symbol, yname: Symbol, xax: Boolean=false, yax: Boolean=false, text: Option[String]=None) = {
        val plot = new Plot()
            .x_range(xdr)
            .y_range(ydr)
            .data_sources(source :: Nil)
            .background_fill("#ffeedd")
            .plot_width(250)
            .plot_height(250)
            .border_fill(Color.White)
            .title("")
            .min_border(2)

        val xaxis = new LinearAxis().plot(plot).location(Location.Below)
        val yaxis = new LinearAxis().plot(plot).location(Location.Left)
        plot.below <<= (xaxis +: _)
        plot.left <<= (yaxis +: _)

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

        val circle_renderer = new Glyph()
            .data_source(source)
            .xdata_range(xdr)
            .ydata_range(ydr)
            .glyph(circle)

        val pantool = new PanTool().plot(plot)
        val wheelzoomtool = new WheelZoomTool().plot(plot)

        plot.renderers := axes ++ grids ++ List(circle_renderer)
        plot.tools := List(pantool, wheelzoomtool)

        text.foreach { text =>
            val text_glyph = new Text()
                .x('xcenter, SpatialUnits.Screen)
                .y('ycenter, SpatialUnits.Screen)
                .text(text.replaceAll("_", " "))
                .angle(pi/4)
                .text_font_style(FontStyle.Bold)
                .text_baseline(TextBaseline.Top)
                .text_color("#ffaaaa")
                .text_alpha(0.5)
                .text_align(TextAlign.Center)
                .text_font_size("28pt")
            val text_renderer = new Glyph()
                .data_source(text_source)
                .xdata_range(xdr)
                .ydata_range(ydr)
                .glyph(text_glyph)

            plot.data_sources := text_source :: plot.data_sources.value
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
