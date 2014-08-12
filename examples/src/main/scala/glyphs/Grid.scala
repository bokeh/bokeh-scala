package io.continuum.bokeh
package examples
package glyphs

import breeze.linalg.linspace
import breeze.numerics.{sin,cos,tan}
import math.{Pi=>pi}

object Grid extends Example {
    val x = linspace(-2*pi, 2*pi, 1000)

    val source = new ColumnDataSource()
        .addColumn('x,  x)
        .addColumn('y1, sin(x))
        .addColumn('y2, cos(x))
        .addColumn('y3, tan(x))
        .addColumn('y4, sin(x) :* cos(x))

    def make_plot(xname: Symbol, yname: Symbol, line_color: Color,
            _xdr: Option[Range]=None, _ydr: Option[Range]=None) = {

        val xdr = _xdr getOrElse new DataRange1d().sources(source.columns(xname) :: Nil)
        val ydr = _ydr getOrElse new DataRange1d().sources(source.columns(yname) :: Nil)

        val plot = new Plot().x_range(xdr).y_range(ydr).data_sources(source :: Nil)

        val xaxis = new LinearAxis().plot(plot).dimension(0).location(Location.Bottom)
        val yaxis = new LinearAxis().plot(plot).dimension(1).location(Location.Left)

        val pantool = new PanTool().plot(plot)
        val wheelzoomtool = new WheelZoomTool().plot(plot)

        val renderer = new Glyph()
            .data_source(source)
            .xdata_range(xdr)
            .ydata_range(ydr)
            .glyph(new Line().x(xname).y(yname).line_color(line_color))

        plot.renderers := List(xaxis, yaxis, renderer)
        plot.tools := List(pantool, wheelzoomtool)

        plot
    }

    val plot1 = make_plot('x, 'y1, Color.Blue)
    val plot2 = make_plot('x, 'y2, Color.Red, _xdr=plot1.x_range.valueOpt)
    val plot3 = make_plot('x, 'y3, Color.Green)
    val plot4 = make_plot('x, 'y4, Color.Black)

    val children = List(List(plot1, plot2), List(plot3, plot4))
    val grid = new GridPlot().children(children)

    val document = new Document(grid)
    val html = document.save("grid.html")
    println(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
