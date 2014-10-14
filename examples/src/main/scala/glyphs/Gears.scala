package io.continuum.bokeh
package examples
package glyphs

import math.Pi

object Gears extends Example with Tools {
    def pitch_radius(module: Double, teeth: Int) =
        (module*teeth)/2

    def half_tooth(teeth: Int) =
        Pi/teeth

    val line_color: Color = "#606060"
    val fill_color: (Color, Color, Color) = ("#ddd0dd", "#d0d0e8", "#ddddd0")

    def sample_gear() = {
        val xdr = new Range1d().start(-30).end(30)
        val ydr = new Range1d().start(-30).end(30)

        val source = new ColumnDataSource().addColumn('dummy, Seq(0))
        val plot = new Plot().x_range(xdr).y_range(ydr)
            .plot_width(800).plot_height(800).tools(Pan|WheelZoom|Reset)

        val glyph = new Gear().x(0).y(0).module(5).teeth(8).angle(0).shaft_size(0.2).fill_color(fill_color._3).line_color(line_color)
        val renderer = new GlyphRenderer().data_source(source).glyph(glyph)
        plot.renderers <<= (renderer :: _)

        plot
    }

    def classical_gear(module: Double, large_teeth: Int, small_teeth: Int) = {
        val xdr = new Range1d().start(-300).end(150)
        val ydr = new Range1d().start(-100).end(100)

        val source = new ColumnDataSource().addColumn('dummy, Seq(0))
        val plot = new Plot().x_range(xdr).y_range(ydr)
            .plot_width(800).plot_height(800).tools(Pan|WheelZoom|Reset)

        def large_gear() = {
            val radius = pitch_radius(module, large_teeth)
            val angle = 0
            val glyph = new Gear().x(-radius).y(0).module(module).teeth(large_teeth).angle(angle).fill_color(fill_color._1).line_color(line_color)
            new GlyphRenderer().data_source(source).glyph(glyph)
        }

        def small_gear() = {
            val radius = pitch_radius(module, small_teeth)
            val angle = half_tooth(small_teeth)
            val glyph = new Gear().x(radius).y(0).module(module).teeth(small_teeth).angle(angle).fill_color(fill_color._2).line_color(line_color)
            new GlyphRenderer().data_source(source).glyph(glyph)
        }

        plot.renderers <<= (large_gear() :: small_gear() :: _)
        plot
    }

    def epicyclic_gear(module: Double, sun_teeth: Int, planet_teeth: Int) = {
        val xdr = new Range1d().start(-150).end(150)
        val ydr = new Range1d().start(-150).end(150)

        val source = new ColumnDataSource().addColumn('dummy, Seq(0))
        val plot = new Plot().x_range(xdr).y_range(ydr)
            .plot_width(800).plot_height(800).tools(Pan|WheelZoom|Reset)

        val annulus_teeth = sun_teeth + 2*planet_teeth

        def annular_gear() = {
            val glyph = new Gear().x(0).y(0).module(module).teeth(annulus_teeth).angle(0).fill_color(fill_color._1).line_color(line_color).internal(true)
            new GlyphRenderer().data_source(source).glyph(glyph)
        }

        def sun_gear() = {
            val glyph = new Gear().x(0).y(0).module(module).teeth(sun_teeth).angle(0).fill_color(fill_color._3).line_color(line_color)
            new GlyphRenderer().data_source(source).glyph(glyph)
        }

        val sun_radius = pitch_radius(module, sun_teeth)
        val planet_radius = pitch_radius(module, planet_teeth)

        val radius = sun_radius + planet_radius
        val angle = half_tooth(planet_teeth)

        val planets = for ((i, j) <- List((+1, 0), (0, +1), (-1, 0), (0, -1))) yield {
            val glyph = new Gear().x(radius*i).y(radius*j).module(module).teeth(planet_teeth).angle(angle).fill_color(fill_color._2).line_color(line_color);
            new GlyphRenderer().data_source(source).glyph(glyph)
        }

        plot.renderers <<= (annular_gear() :: sun_gear() :: planets ++ _)
        plot
    }

    val sample    = sample_gear()
    val classical = classical_gear(5, 52, 24)
    val epicyclic = epicyclic_gear(5, 24, 12)

    val document = new Document(sample, classical, epicyclic)
    val html = document.save("gears.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
