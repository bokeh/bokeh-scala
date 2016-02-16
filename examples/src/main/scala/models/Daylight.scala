package io.continuum.bokeh
package examples
package models

import org.joda.time.{LocalTime=>Time,LocalDate=>Date}

import thirdparty._

object Daylight extends Example {
    val daylight = sampledata.daylight.Warsaw2013

    object source extends ColumnDataSource {
        val dates    = column(daylight.date)
        val sunrises = column(daylight.sunrise)
        val sunsets  = column(daylight.sunset)
    }

    object patch1_source extends ColumnDataSource {
        val dates = column(daylight.date ++ daylight.date.reverse)
        val times = column(daylight.sunrise ++ daylight.sunset.reverse)
    }

    val summer = daylight.summerOnly

    object patch2_source extends ColumnDataSource {
        val dates = column(summer.date ++ summer.date.reverse)
        val times = column(summer.sunrise ++ summer.sunset.reverse)
    }

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

    object text_source extends ColumnDataSource {
        val dates = column(List(springMiddle, summerMiddle, autumnMiddle))
        val times = column(List(_11_30, _11_30, _11_30))
        val texts = column(List("CST (UTC+1)", "CEST (UTC+2)", "CST (UTC+1)"))
    }

    val xdr = new DataRange1d()
    val ydr = new DataRange1d()

    val title = "Daylight Hours - Warsaw, Poland"
    val sources = List(source, patch1_source, patch2_source, text_source)
    val plot = new Plot().title(title).x_range(xdr).y_range(ydr).width(800).height(400)

    val patch1 = Patch().x('dates).y('times).fill_color(Color.SkyBlue).fill_alpha(0.8)
    val patch1_glyph = new GlyphRenderer().data_source(patch1_source).glyph(patch1)

    val patch2 = Patch().x('dates).y('times).fill_color(Color.Orange).fill_alpha(0.8)
    val patch2_glyph = new GlyphRenderer().data_source(patch2_source).glyph(patch2)

    val line1 = Line().x('dates).y('sunrises).line_color(Color.Yellow).line_width(2)
    val line1_glyph = new GlyphRenderer().data_source(source).glyph(line1)

    val line2 = Line().x('dates).y('sunsets).line_color(Color.Red).line_width(2)
    val line2_glyph = new GlyphRenderer().data_source(source).glyph(line2)

    val text = Text().x('dates).y('times).text('texts).angle(0).text_align(TextAlign.Center)
    val text_glyph = new GlyphRenderer().data_source(text_source).glyph(text)

    val glyphs = List(patch1_glyph, patch2_glyph, line1_glyph, line2_glyph, text_glyph)
    plot.renderers <<= (glyphs ++ _)

    val xformatter = new DatetimeTickFormatter().formats(Map(DatetimeUnits.Months -> List("%b %Y")))
    val xaxis = new DatetimeAxis().plot(plot).formatter(xformatter)
    val yaxis = new DatetimeAxis().plot(plot)
    plot.below <<= (xaxis :: _)
    plot.left <<= (yaxis :: _)
    val xgrid = new Grid().plot(plot).dimension(0).axis(xaxis)
    val ygrid = new Grid().plot(plot).dimension(1).axis(yaxis)

    plot.renderers <<= (xaxis :: yaxis :: xgrid :: ygrid :: _)

    val legends = List("sunrise" -> List(line1_glyph),
                       "sunset"  -> List(line2_glyph))
    val legend = new Legend().plot(plot).legends(legends)
    plot.renderers <<= (legend :: _)

    val document = new Document(plot)
    val html = document.save("daylight.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
