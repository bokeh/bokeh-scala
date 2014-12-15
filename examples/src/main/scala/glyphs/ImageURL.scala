package io.continuum.bokeh
package examples
package glyphs

import breeze.linalg.linspace

object ImageURL extends Example {
    val url = "http://bokeh.pydata.org/_static/bokeh-transparent.png"
    val N = 5

    val source = new ColumnDataSource()
        .addColumn('url, List(url)*N)
        .addColumn('x1,  linspace(  0, 150, N))
        .addColumn('y1,  linspace(  0, 150, N))
        .addColumn('w1,  linspace( 10,  50, N))
        .addColumn('h1,  linspace( 10,  50, N))
        .addColumn('x2,  linspace(-50, 150, N))
        .addColumn('y2,  linspace(  0, 200, N))

    val xdr = new Range1d().start(-100).end(200)
    val ydr = new Range1d().start(-100).end(200)

    val plot = new Plot().title("ImageURL").x_range(xdr).y_range(ydr)

    val image1 = new ImageURL().url('url).x('x1).y('y1).w('w1).h('h1).anchor(Anchor.Center)
    plot.addGlyph(source, image1)

    val image2 = new ImageURL().url('url).x('x2).y('y2).w(20).h(20).anchor(Anchor.TopLeft)
    plot.addGlyph(source, image2)

    val image3 = new ImageURL().url(url).x(200).y(-100).anchor(Anchor.BottomRight)
    plot.addGlyph(source, image3)

    val xaxis = new LinearAxis().plot(plot)
    plot.below := xaxis :: Nil

    val yaxis = new LinearAxis().plot(plot)
    plot.left := yaxis :: Nil

    val xgrid = new Grid().plot(plot).dimension(0).ticker(xaxis.ticker.value)
    val ygrid = new Grid().plot(plot).dimension(1).ticker(yaxis.ticker.value)

    plot.renderers <<= (xaxis :: yaxis :: xgrid :: ygrid :: _)

    val document = new Document(plot)
    val html = document.save("image_url.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
