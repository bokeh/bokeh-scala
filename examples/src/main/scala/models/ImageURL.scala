package io.continuum.bokeh
package examples
package models

import breeze.linalg.linspace

import thirdparty._

object ImageURLExample extends Example {
    val url = "http://bokeh.pydata.org/en/latest/_static/images/logo.png"
    val N = 5

    object source extends ColumnDataSource {
        val urls = column(List(url)*N)
        val x1   = column(linspace(  0, 150, N))
        val y1   = column(linspace(  0, 150, N))
        val w1   = column(linspace( 10,  50, N))
        val h1   = column(linspace( 10,  50, N))
        val x2   = column(linspace(-50, 150, N))
        val y2   = column(linspace(  0, 200, N))
    }

    import source.{urls,x1,y1,w1,h1,x2,y2}

    val xdr = new Range1d().start(-100).end(200)
    val ydr = new Range1d().start(-100).end(200)

    val plot = new Plot().title("ImageURL").x_range(xdr).y_range(ydr)

    val image1 = ImageURL().url(urls).x(x1).y(y1).w(w1).h(h1).anchor(Anchor.Center)
    plot.addGlyph(source, image1)

    val image2 = ImageURL().url(urls).x(x2).y(y2).w(20).h(20).anchor(Anchor.TopLeft)
    plot.addGlyph(source, image2)

    val image3 = ImageURL().url(url).x(200).y(-100).anchor(Anchor.BottomRight)
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
