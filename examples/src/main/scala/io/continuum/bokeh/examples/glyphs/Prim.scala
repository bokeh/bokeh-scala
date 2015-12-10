package io.continuum.bokeh
package examples
package glyphs

object Prim extends Example {
    object source extends ColumnDataSource {
        val x = column(1.0 to 6.0 by  1.0)
        val y = column(5.0 to 0.0 by -1.0)
    }

    import source.{x,y}

    val xdr = new Range1d().start(0).end(10)
    val ydr = new Range1d().start(0).end(10)

    def make_plot[T <: Glyph](name: String, glyph: T) = {
        val renderer = new GlyphRenderer()
            .data_source(source)
            .glyph(glyph)

        val plot = new Plot().x_range(xdr).y_range(ydr).title(name)
        val xaxis = new LinearAxis().plot(plot)
        val yaxis = new LinearAxis().plot(plot)
        plot.below <<= (xaxis :: _)
        plot.left <<= (yaxis :: _)
        val xgrid = new Grid().plot(plot).axis(xaxis).dimension(0)
        val ygrid = new Grid().plot(plot).axis(yaxis).dimension(1)

        val pantool = new PanTool().plot(plot)
        val wheelzoomtool = new WheelZoomTool().plot(plot)

        plot.renderers := List(xaxis, yaxis, xgrid, ygrid, renderer)
        plot.tools := List(pantool, wheelzoomtool)

        plot
    }

    val plots = List(
        make_plot("annular_wedge", new AnnularWedge().x(x).y(y).inner_radius(0.2).outer_radius(0.5).start_angle(0.8).end_angle(3.8)),
        make_plot("annulus",       new Annulus().x(x).y(y).inner_radius(0.2).outer_radius(0.5)),
        make_plot("arc",           new Arc().x(x).y(y).radius(0.4).start_angle(0.8).end_angle(3.8)),
        make_plot("circle",        new Circle().x(x).y(y).radius(1)),
        make_plot("oval",          new Oval().x(x).y(y).width(0.5).height(0.8).angle(-0.6)),
        make_plot("ray",           new Ray().x(x).y(y).length(25).angle(0.6)),
        make_plot("rect",          new Rect().x(x).y(y).width(0.5).height(0.8).angle(-0.6)),
        make_plot("text",          new Text().x(x).y(y).text("foo").angle(0.6)),
        make_plot("wedge",         new Wedge().x(x).y(y).radius(0.5).start_angle(0.9).end_angle(3.2)))

    val document = new Document(plots: _*)
    val html = document.save("prim.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
