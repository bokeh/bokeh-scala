package io.continuum.bokeh
package examples
package glyphs

import scala.reflect.ClassTag
import scala.collection.immutable.NumericRange
import breeze.linalg.{DenseVector,DenseMatrix}

trait LinAlgHelpers {
    def meshgrid(x1: DenseVector[Double], x2: DenseVector[Double]): (DenseMatrix[Double], DenseMatrix[Double]) = {
        val x1Mesh = DenseMatrix.zeros[Double](x2.length, x1.length)
        for (i <- 0 until x2.length) {
            x1Mesh(i, ::) := x1.t
        }
        val x2Mesh = DenseMatrix.zeros[Double](x2.length, x1.length)
        for (i <- 0 until x1.length) {
            x2Mesh(::, i) := x2
        }
        (x1Mesh, x2Mesh)
    }

    implicit def NumericRangeToDenseVector[T:ClassTag](range: NumericRange[T]) = new DenseVector(range.toArray)
}

object Hover extends App with LinAlgHelpers {
    val (xx, yy) = meshgrid(0.0 to 100.0 by 4.0,
                            0.0 to 100.0 by 4.0)

    val x = xx.flatten()
    val y = yy.flatten()

    val inds = x.mapPairs((k, _) => k.toString) toArray
    val radii = DenseVector.rand(x.length)*0.4 + 1.7

    val reds = (x*2.0 + 50.0).map(_.toInt).toArray
    val greens = (y*2.0 + 30.0).map(_.toInt).toArray

    val colors = reds.zip(greens).map { case (r, g) => RGB(r, g, 150) }

    val source = new ColumnDataSource()
        .addColumn('x, x)
        .addColumn('y, y)
        .addColumn('radii, radii)
        .addColumn('inds, inds)
        .addColumn('colors, colors)

    val xdr = new DataRange1d().sources(source.columns('x) :: Nil)
    val ydr = new DataRange1d().sources(source.columns('y) :: Nil)

    val plot = new Plot()
        .title("Color Scatter Example")
        .x_range(xdr)
        .y_range(ydr)
        .data_sources(source :: Nil)
        .tools {
            import DefaultTools._
            Pan|WheelZoom|BoxZoom|Reset|PreviewSave
        }

    val circle = new Circle()
        .x('x)
        .y('y)
        .radius('radii)
        .fill_color('colors)
        .fill_alpha(0.6)
        .line_color()

    val circle_renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(circle)

    val text = new Text()
        .x('x)
        .y('y)
        .text('inds)
        .angle(0.0)
        .text_alpha(0.5)
        .text_font_size("5pt")
        .text_baseline(TextBaseline.Middle)
        .text_align(TextAlign.Center)

    val text_renderer = new Glyph()
        .data_source(source)
        .xdata_range(xdr)
        .ydata_range(ydr)
        .glyph(text)

    val hover = new HoverTool()
        .tooltips(Map(
            "index"         -> "$index",
            "fill_color"    -> "$color[hex,swatch]:fill_color",
            "radius"        -> "@radius",
            "data (x, y)"   -> "(@x, @y)",
            "cursor (x, y)" -> "($x, $y)",
            "canvas (x, y)" -> "($sx, $sy)"))

    val xaxis = new LinearAxis().plot(plot).dimension(0)
    val yaxis = new LinearAxis().plot(plot).dimension(1)

    plot.renderers := List(xaxis, yaxis, circle_renderer, text_renderer)
    plot.tools <<= (_ :+ hover)

    val session = new HTMLFileSession("hover.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
