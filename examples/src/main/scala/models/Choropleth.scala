package io.continuum.bokeh
package examples
package models

import sampledata.USState.{AK,HI}

object Choropleth extends Example {
    val excluded_states: Set[sampledata.USState] = Set(AK, HI)

    val us_states = sampledata.us_states -- excluded_states

    val us_counties = sampledata.us_counties.filterNot { case (_, county) =>
        excluded_states contains county.state
    }

    val unemployment = sampledata.unemployment

    val colors: List[Color] = List("#F1EEF6", "#D4B9DA", "#C994C7", "#DF65B0", "#DD1C77", "#980043")

    object state_source extends ColumnDataSource {
        val state_xs = column(us_states.values.map(_.lons))
        val state_ys = column(us_states.values.map(_.lats))
    }

    object county_source extends ColumnDataSource {
        val county_xs = column(us_counties.values.map(_.lons))
        val county_ys = column(us_counties.values.map(_.lats))

        val county_colors = column {
            us_counties
                .keys
                .toList
                .map(unemployment.get)
                .map {
                    case Some(rate) => colors(math.min(rate/2 toInt, 5))
                    case None => Color.Black
                }
        }
    }

    import state_source.{state_xs,state_ys}
    import county_source.{county_xs,county_ys,county_colors}

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    val county_patches = new Patches()
        .xs(county_xs)
        .ys(county_ys)
        .fill_color(county_colors)
        .fill_alpha(0.7)
        .line_color(Color.White)
        .line_width(0.5)

    val state_patches = new Patches()
        .xs(state_xs)
        .ys(state_ys)
        .fill_alpha(0.0)
        .line_color("#884444")
        .line_width(2)

    val county_renderer = new GlyphRenderer()
        .data_source(county_source)
        .glyph(county_patches)

    val state_renderer = new GlyphRenderer()
        .data_source(state_source)
        .glyph(state_patches)

    val plot = new Plot()
        .x_range(xdr)
        .y_range(ydr)
        .border_fill(Color.White)
        .title("2009 Unemployment Data")
        .width(1300)
        .height(800)

    val resizetool = new ResizeTool().plot(plot)

    plot.renderers := List(county_renderer, state_renderer)
    plot.tools := List(resizetool)

    val document = new Document(plot)
    val html = document.save("choropleth.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
