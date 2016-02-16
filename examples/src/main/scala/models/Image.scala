package io.continuum.bokeh
package examples
package models

import breeze.linalg.linspace
import breeze.numerics.{sin,cos}

import thirdparty._

object ImageExample extends Example {
    val N = 500

    val x = linspace(0, 10, N)
    val y = linspace(0, 10, N)
    val (xx, yy) = meshgrid(x, y)
    val data = sin(xx) :* cos(yy)

    val xdr = new Range1d().start(0).end(10)
    val ydr = new Range1d().start(0).end(10)

    val plot = new Plot().x_range(xdr).y_range(ydr).title("Image plot with Spectral11 palette")

    val mapper = new LinearColorMapper().palette(Palette.Spectral11)
    val image = new Image().image(data).x(0).y(0).dw(10).dh(10).color_mapper(mapper)
    plot.addGlyph(image)

    val document = new Document(plot)
    val html = document.save("image.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
