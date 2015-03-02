package io.continuum.bokeh
package examples
package glyphs

import math.{abs,pow,sin,cos,atan2,sqrt,toRadians,Pi=>pi}
import breeze.linalg.{diff,DenseVector}

import widgets.VBox
import sampledata.mtb.{obiszow_mtb_xcm=>mtb}

object Trail extends Example with Tools {
    def haversin(theta: Double) = pow(sin(0.5*theta), 2)

    def distance(p1: (Double, Double), p2: (Double, Double)): Double = {
        val R = 6371

        val (lat1, lon1) = p1
        val (lat2, lon2) = p2

        val phi1 = toRadians(lat1)
        val phi2 = toRadians(lat2)
        val delta_lat = toRadians(lat2 - lat1)
        val delta_lon = toRadians(lon2 - lon1)

        val a = haversin(delta_lat) + cos(phi1)*cos(phi2)*haversin(delta_lon)
        2*R*atan2(sqrt(a), sqrt(1 - a))
    }

    val dists = (mtb.lat zip mtb.lon)
        .sliding(2).toList
        .map { case List(p1, p2) => distance(p2, p1) }

    val dist = dists.scanLeft(0.0)(_ + _)

    // TODO: val slopes = abs(100*diff(alt)/(1000*dists))
    val slopes = mtb.alt
       .sliding(2).toList
       .map { case List(a1, a2) => a2 - a1 }
       .map(100*_)
       .zip(dists.map(1000*_))
       .map { case (alt, dist) => alt/dist }
       .map(abs)

    val grads = slopes map { slope =>
        if      (               slope <  4) 0
        else if (slope >=  4 && slope <  6) 1
        else if (slope >=  6 && slope < 10) 2
        else if (slope >= 10 && slope < 15) 3
        else                                4
    }

    val colors: Seq[Color] = grads map {
        case 0 => Color.Green
        case 1 => Color.Yellow
        case 2 => Color.Pink
        case 3 => Color.Orange
        case 4 => Color.Red
    }

    val title = "Obiszów MTB XCM"

    val trail_map = {
        val lon = (mtb.lon.min + mtb.lon.max)/2
        val lat = (mtb.lat.min + mtb.lat.max)/2

        val map_options = new GMapOptions().lng(lon).lat(lat).zoom(13)
        val plot = new GMapPlot().title(s"$title - Trail Map").map_options(map_options).width(800).height(800)

        val xaxis = new LinearAxis().plot(plot).formatter(new NumeralTickFormatter().format("0.000"))
        plot.addLayout(xaxis, Layout.Below)

        val yaxis = new LinearAxis().plot(plot).formatter(new PrintfTickFormatter().format("%.3f"))
        plot.addLayout(yaxis, Layout.Left)

        val xgrid = new Grid().plot(plot).dimension(0).ticker(xaxis.ticker.value).grid_line_dash(DashPattern.Dashed).grid_line_color(Color.Gray)
        val ygrid = new Grid().plot(plot).dimension(1).ticker(yaxis.ticker.value).grid_line_dash(DashPattern.Dashed).grid_line_color(Color.Gray)
        plot.renderers <<= (xgrid :: ygrid :: _)

        val hover = new HoverTool().tooltips(Tooltip("distance" -> "@dist"))
        plot.tools := Pan|WheelZoom|Reset|BoxSelect
        plot.tools <<= (hover +: _)

        object line_source extends ColumnDataSource {
            val x = column(mtb.lon)
            val y = column(mtb.lat)
        }

        import line_source.{x,y}

        val line = new Line().x(x).y(y).line_color(Color.Blue).line_width(2)
        plot.addGlyph(line_source, line)

        plot.x_range := new DataRange1d().sources(x :: Nil)
        plot.y_range := new DataRange1d().sources(y :: Nil)

        plot
    }

    val altitude_profile = {
        val plot = new Plot().title(s"$title - Altitude Profile").width(800).height(400)

        val xaxis = new LinearAxis().plot(plot).axis_label("Distance (km)")
        plot.addLayout(xaxis, Layout.Below)

        val yaxis = new LinearAxis().plot(plot).axis_label("Altitude (m)")
        plot.addLayout(yaxis, Layout.Left)

        val xgrid = new Grid().plot(plot).dimension(0).ticker(xaxis.ticker.value)
        val ygrid = new Grid().plot(plot).dimension(1).ticker(yaxis.ticker.value)
        plot.renderers <<= (xgrid :: ygrid :: _)

        plot.tools := Pan|WheelZoom|Reset|BoxSelect

        val (_xs, _ys) = (dist, mtb.alt)
        val y0 = _ys.min

        object patches_source extends ColumnDataSource {
            val xs    = column(_xs.sliding(2).map { case List(xi, xj) => List(xi, xj, xj, xi) } toList)
            val ys    = column(_ys.sliding(2).map { case List(yi, yj) => List(y0, y0, yj, yi) } toList)
            val color = column(colors)
        }

        import patches_source.{xs,ys,color}

        val patches = new Patches().xs(xs).ys(ys).fill_color(color).line_color(color)
        plot.addGlyph(patches_source, patches)

        object line_source extends ColumnDataSource {
            val x = column(dist)
            val y = column(mtb.alt)
        }

        import line_source.{x,y}

        val line = new Line().x(x).y(y).line_color(Color.Black).line_width(1)
        plot.addGlyph(line_source, line)

        plot.x_range := new DataRange1d().sources(x :: Nil)
        plot.y_range := new DataRange1d().sources(y :: Nil)

        plot
    }

    val layout = new VBox().children(List(altitude_profile, trail_map))

    val document = new Document(layout)
    val html = document.save("trail.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
