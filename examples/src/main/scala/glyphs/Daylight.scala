package io.continuum.bokeh
package examples
package glyphs

import org.joda.time.{LocalTime=>Time,LocalDate=>Date}

object Daylight extends App {
    val daylight = sampledata.daylight.Warsaw2013

    val source = new ColumnDataSource()
        .addColumn('dates, daylight.date)
        .addColumn('sunrises, daylight.sunrise)
        .addColumn('sunsets, daylight.sunset)

    val patch1_source = new ColumnDataSource()
        .addColumn('dates, daylight.date ++ daylight.date.reverse)
        .addColumn('times, daylight.sunrise ++ daylight.sunset.reverse)

    val summer = daylight.summerOnly

    val patch2_source = new ColumnDataSource()
        .addColumn('dates, summer.date ++ summer.date.reverse)
        .addColumn('times, summer.sunrise ++ summer.sunset.reverse)

    val summerStartIndex = daylight.summer.indexOf(true)
    val summerEndIndex   = daylight.summer.indexOf(false, summerStartIndex)

    val calendarStart = daylight.date.head
    val summerStart   = daylight.date(summerStartIndex)
    val summerEnd     = daylight.date(summerEndIndex)
    val calendarEnd   = daylight.date.last

    def middle(start: Date, end: Date) =
        new Date((start.toDateTimeAtStartOfDay.getMillis + end.toDateTimeAtStartOfDay.getMillis) / 2)

    val springMiddle = middle(summerStart, calendarStart)
    val summerMiddle = middle(summerEnd,   summerStart)
    val autumnMiddle = middle(calendarEnd, summerEnd)

    val _11_30 = new Time(11, 30)

    val text_source = new ColumnDataSource()
        .addColumn('dates, List(springMiddle, summerMiddle, autumnMiddle))
        .addColumn('times, List(_11_30, _11_30, _11_30))
        .addColumn('texts, List("CST (UTC+1)", "CEST (UTC+2)", "CST (UTC+1)"))

    val xdr = new DataRange1d().sources(List(source.columns('dates)))
    val ydr = new DataRange1d().sources(List(source.columns('sunrises, 'sunsets)))

    val title = "Daylight Hours - Warsaw, Poland"
    val sources = List(source, patch1_source, patch2_source, text_source)
    val plot = new Plot().title(title).data_sources(sources).x_range(xdr).y_range(ydr).plot_width(800).plot_height(400)

    val patch1 = new Patch().x('dates).y('times).fill_color(Color.SkyBlue).fill_alpha(0.8)
    val patch1_glyph = new Glyph().data_source(patch1_source).xdata_range(xdr).ydata_range(ydr).glyph(patch1)

    val patch2 = new Patch().x('dates).y('times).fill_color(Color.Orange).fill_alpha(0.8)
    val patch2_glyph = new Glyph().data_source(patch2_source).xdata_range(xdr).ydata_range(ydr).glyph(patch2)

    val line1 = new Line().x('dates).y('sunrises).line_color(Color.Yellow).line_width(2)
    val line1_glyph = new Glyph().data_source(source).xdata_range(xdr).ydata_range(ydr).glyph(line1)

    val line2 = new Line().x('dates).y('sunsets).line_color(Color.Red).line_width(2)
    val line2_glyph = new Glyph().data_source(source).xdata_range(xdr).ydata_range(ydr).glyph(line2)

    val text = new Text().x('dates).y('times).text('texts).angle(0).text_align(TextAlign.Center)
    val text_glyph = new Glyph().data_source(text_source).xdata_range(xdr).ydata_range(ydr).glyph(text)

    val glyphs = List(patch1_glyph, patch2_glyph, line1_glyph, line2_glyph, text_glyph)
    plot.renderers <<= (glyphs ++ _)

    val xformatter = new DatetimeTickFormatter().formats(Map(DatetimeUnits.Months -> List("%b %Y")))
    val xaxis = new DatetimeAxis().plot(plot).dimension(0).formatter(xformatter)
    val yaxis = new DatetimeAxis().plot(plot).dimension(1)
    val xgrid = new Grid().plot(plot).dimension(0).axis(xaxis)
    val ygrid = new Grid().plot(plot).dimension(1).axis(yaxis)

    plot.renderers <<= (xaxis :: yaxis :: xgrid :: ygrid :: _)

    val legends = Map("sunrise" -> List(line1_glyph),
                      "sunset"  -> List(line2_glyph))
    val legend = new Legend().plot(plot).legends(legends)
    plot.renderers <<= (legend :: _)

    val document = new Document(plot)
    val html = document.save("daylight.html")
    println(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
