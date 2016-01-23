package io.continuum.bokeh
package examples
package models

import math.{Pi=>pi,sin,cos}

object Gauges extends Example {
    val start_angle = pi + pi/4
    val end_angle = -pi/4

    val max_kmh = 250
    val max_mph = max_kmh*0.621371

    val major_step = 25
    val minor_step = 5

    val xdr = new Range1d().start(-1.25).end(1.25)
    val ydr = new Range1d().start(-1.25).end(1.25)

    val plot = new Plot().title("Speedometer").x_range(xdr).y_range(ydr).width(600).height(600)

    plot.addGlyph(new Circle().x(0).y(0).radius(1.00).fill_color(Color.White).line_color(Color.Black))
    plot.addGlyph(new Circle().x(0).y(0).radius(0.05).fill_color(Color.Gray).line_color(Color.Black))

    plot.addGlyph(new Text().x(0).y(+0.15).angle(0).text("km/h").text_color(Color.Red)
        .text_align(TextAlign.Center).text_baseline(TextBaseline.Bottom).text_font_style(FontStyle.Bold))
    plot.addGlyph(new Text().x(0).y(-0.15).angle(0).text("mph").text_color(Color.Blue)
        .text_align(TextAlign.Center).text_baseline(TextBaseline.Top).text_font_style(FontStyle.Bold))

    def speed_to_angle(speed: Double, kmh_units: Boolean): Double = {
        val max_speed = if (kmh_units) max_kmh else max_mph
        val bounded_speed = speed.max(0).min(max_speed)
        val total_angle = start_angle - end_angle
        val angle = total_angle*bounded_speed/max_speed
        start_angle - angle
    }

    def add_needle(speed: Double, kmh_units: Boolean) {
        val angle = speed_to_angle(speed, kmh_units)
        plot.addGlyph(new Ray().x(0).y(0).length(0.75, SpatialUnits.Data).angle(angle)   .line_color(Color.Black).line_width(3))
        plot.addGlyph(new Ray().x(0).y(0).length(0.10, SpatialUnits.Data).angle(angle-pi).line_color(Color.Black).line_width(3))
    }

    def polar_to_cartesian(r: Double, alpha: Double) = (r*cos(alpha), r*sin(alpha))

    def add_gauge(radius: Double, max_value: Double, length: Double, direction: Int, color: Color, major_step: Double, minor_step: Double) {
        var major_angles = List[Double]()
        var minor_angles = List[Double]()

        val total_angle = start_angle - end_angle

        val major_angle_step = major_step/max_value*total_angle
        val minor_angle_step = minor_step/max_value*total_angle

        var major_angle = 0.0

        while (major_angle <= total_angle) {
            major_angles :+= start_angle - major_angle
            major_angle += major_angle_step
        }

        var minor_angle = 0.0

        while (minor_angle <= total_angle) {
            minor_angles :+= start_angle - minor_angle
            minor_angle += minor_angle_step
        }

        var major_labels = major_angles.zipWithIndex.map { case (_, i) => (major_step*i).toInt.toString }
        var minor_labels = minor_angles.zipWithIndex.map { case (_, i) => (minor_step*i).toInt.toString }

        val n = major_step/minor_step

        minor_angles = minor_angles.zipWithIndex.collect { case (x, i) if i % n != 0 => x }
        minor_labels = minor_labels.zipWithIndex.collect { case (x, i) if i % n != 0 => x }

        plot.addGlyph(new Arc().x(0).y(0).radius(radius).start_angle(start_angle).end_angle(end_angle).direction(Direction.Clock).line_color(color).line_width(2))

        val rotation = if (direction == 1) 0 else -pi

        {
            val (x, y) = major_angles.map(polar_to_cartesian(radius, _)).unzip
            val angles = major_angles.map(_ + rotation)
            val source = new ColumnDataSource().data(Map('x -> x, 'y -> y, 'angle -> angles))
            val glyph = new Ray().x('x).y('y).length(length, SpatialUnits.Data).angle('angle).line_color(color).line_width(2)
            plot.addGlyph(source, glyph)
        }

        {
            val (x, y) = minor_angles.map(polar_to_cartesian(radius, _)).unzip
            val angles = minor_angles.map(_ + rotation)
            val source = new ColumnDataSource().data(Map('x -> x, 'y -> y, 'angle -> angles))
            val glyph = new Ray().x('x).y('y).length(length/2, SpatialUnits.Data).angle('angle).line_color(color).line_width(1)
            plot.addGlyph(source, glyph)
        }

        {
            val (x, y) = major_angles.map(polar_to_cartesian(radius+2*length*direction, _)).unzip
            val angles = major_angles.map(_ - pi/2)
            val source = new ColumnDataSource().data(Map('x -> x, 'y -> y, 'angle -> angles, 'text -> major_labels))
            val glyph = new Text().x('x).y('y).angle('angle).text('text).text_align(TextAlign.Center).text_baseline(TextBaseline.Middle)
            plot.addGlyph(source, glyph)
        }
    }

    add_gauge(0.75, max_kmh, 0.05, +1, Color.Red,  major_step, minor_step)
    add_gauge(0.70, max_mph, 0.05, -1, Color.Blue, major_step, minor_step)

    add_needle(55, kmh_units=true)

    val document = new Document(plot)
    val html = document.save("gauges.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
