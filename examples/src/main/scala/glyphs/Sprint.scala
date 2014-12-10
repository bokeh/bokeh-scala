package io.continuum.bokeh
package examples
package glyphs

import sampledata.{sprint,Medal}

object Sprint extends Example {
    // Based on http://www.nytimes.com/interactive/2012/08/05/sports/olympics/the-100-meter-dash-one-race-every-medalist-ever.html

    val abbrev_to_country: PartialFunction[String, String] = {
        case "USA" => "United States"
        case "GBR" => "Britain"
        case "JAM" => "Jamaica"
        case "CAN" => "Canada"
        case "TRI" => "Trinidad and Tobago"
        case "AUS" => "Australia"
        case "GER" => "Germany"
        case "CUB" => "Cuba"
        case "NAM" => "Namibia"
        case "URS" => "Soviet Union"
        case "BAR" => "Barbados"
        case "BUL" => "Bulgaria"
        case "HUN" => "Hungary"
        case "NED" => "Netherlands"
        case "NZL" => "New Zealand"
        case "PAN" => "Panama"
        case "POR" => "Portugal"
        case "RSA" => "South Africa"
        case "EUA" => "United Team of Germany"
    }

    val gold_fill   = "#efcf6d"
    val gold_line   = "#c8a850"
    val silver_fill = "#cccccc"
    val silver_line = "#b0b0b1"
    val bronze_fill = "#c59e8a"
    val bronze_line = "#98715d"

    type PFC = PartialFunction[Medal, Color]

    val fill_color: PFC = { case Medal.Gold => gold_fill case Medal.Silver => silver_fill case Medal.Bronze => bronze_fill }
    val line_color: PFC = { case Medal.Gold => gold_line case Medal.Silver => silver_line case Medal.Bronze => bronze_line }

    val t0 = sprint.time(0)

    object data {
        val abbrev        = sprint.country
        val country       = sprint.country.map(abbrev_to_country)
        val medal         = sprint.medal.map(_.name.toLowerCase)
        val year          = sprint.year
        val speed         = sprint.time.map(t => 100.0/t)
        val meters_back   = sprint.time.map(t => 100.0*(1.0 - t0/t))
        val medal_fill    = sprint.medal.map(fill_color)
        val medal_line    = sprint.medal.map(line_color)
        val selected_name = (sprint.name, sprint.medal, sprint.year).zipped.map { case (name, medal, year) =>
            if (medal == Medal.Gold && Set(1988, 1968, 1936, 1896).contains(year)) Some(name) else None
        }
    }

    val source = new ColumnDataSource().data(Map(
        'Abbrev       -> data.abbrev,
        'Country      -> data.country,
        'Medal        -> data.medal,
        'Year         -> data.year,
        'Speed        -> data.speed,
        'MetersBack   -> data.meters_back,
        'MedalFill    -> data.medal_fill,
        'MedalLine    -> data.medal_line,
        'SelectedName -> data.selected_name))

    val title = "Usain Bolt vs. 116 years of Olympic sprinters"

    val xdr = new Range1d().start(data.meters_back.max+2).end(0)                         // XXX: +2 is poor-man's padding (otherwise misses last tick)
    val ydr = new DataRange1d().sources(source.columns('Year) :: Nil).rangepadding(0.05) // XXX: should be 2 years (both sides)

    val plot = new Plot().title(title).x_range(xdr).y_range(ydr).width(1000).height(600).toolbar_location().outline_line_color()

    val xticker = new SingleIntervalTicker().interval(5).num_minor_ticks(0)
    val xaxis = new LinearAxis().plot(plot).ticker(xticker).axis_line_color().major_tick_line_color()
        .axis_label("Meters behind 2012 Bolt").axis_label_text_font_size("10pt").axis_label_text_font_style(FontStyle.Bold)
    plot.below := xaxis :: Nil
    val xgrid = new Grid().plot(plot).dimension(0).ticker(xaxis.ticker.value)/*.grid_line_dash(LineDash.Dashed)*/
    val yticker = new SingleIntervalTicker().interval(12).num_minor_ticks(0)
    val yaxis = new LinearAxis().plot(plot).ticker(yticker).major_tick_in(-5).major_tick_out(10)
    plot.right := yaxis :: Nil

    val medal_glyph = new Circle().x('MetersBack).y('Year).radius(5, SpatialUnits.Screen).fill_color('MedalFill).line_color('MedalLine).fill_alpha(0.5)
    val medal = new GlyphRenderer().data_source(source).glyph(medal_glyph)

    val athlete_glyph = new Text().x('MetersBack).y('Year).x_offset(10).text('SelectedName)
        .text_align(TextAlign.Left).text_baseline(TextBaseline.Middle).text_font_size("9pt")
    val athlete = new GlyphRenderer().data_source(source).glyph(athlete_glyph)

    val no_olympics_glyph = new Text().x(7.5).y(1942).text("No Olympics in 1940 or 1944")
        .text_align(TextAlign.Center).text_baseline(TextBaseline.Middle).text_font_size("9pt").text_font_style(FontStyle.Italic).text_color(Color.Silver)
    val no_olympics = new GlyphRenderer().data_source(source).glyph(no_olympics_glyph)

    plot.renderers := xaxis :: yaxis :: xgrid :: medal :: athlete :: no_olympics :: Nil

    val tooltips = List(
        ("Name",          "@Name"),
        ("Country",       "@Abbrev"),
        ("Year",          "@Year"),
        ("Time",          "@Time{0.00} s"),
        ("Meters behind", "@{MetersBack}{0.00} m"))

    val hover = new HoverTool().plot(plot).tooltips(tooltips).renderers(medal :: Nil)
    plot.tools := hover :: Nil

    val document = new Document(plot)
    val html = document.save("sprint.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
