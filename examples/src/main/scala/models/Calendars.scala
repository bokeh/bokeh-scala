package io.continuum.bokeh
package examples
package models

import org.joda.time.{LocalDate=>Date}

object Calendars extends Example {
    implicit class DateOps(date: Date) {
        def weekday: Int = date.getDayOfWeek - 1
        def month: Int = date.getMonthOfYear
        def day: Int = date.getDayOfMonth
    }

    class Calendar(firstweekday: Int) {
        def itermonthdates(year: Int, month: Int): List[Date] = {
            val date = new Date(year, month, 1)
            val days = (date.weekday - firstweekday) % 7

            def iterdates(date: Date): List[Date] = {
                date :: {
                    val next = date.plusDays(1)
                    if (next.month != month && next.weekday == firstweekday) Nil else iterdates(next)
                }
            }

            iterdates(date.minusDays(days))
        }

        def itermonthdays(year: Int, month: Int): List[Int] = {
            itermonthdates(year, month).map { date =>
                if (date.month != month) 0 else date.day
            }
        }
    }

    val symbols = new java.text.DateFormatSymbols(java.util.Locale.US)
    val day_abbrs = List("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val month_names = symbols.getMonths().filter(_.nonEmpty)

    def make_calendar(year: Int, month: Int, nameOfFirstweekday: String = "Mon"): Plot = {
        val firstweekday = day_abbrs.indexOf(nameOfFirstweekday)
        val calendar = new Calendar(firstweekday=firstweekday)

        val month_days  = calendar.itermonthdays(year, month).map(day => if (day == 0) None else Some(day.toString))
        val month_weeks = month_days.length/7

        val workday = Color.Linen
        val weekend = Color.LightSteelBlue

        def weekday(date: Date): Int = {
            (date.weekday - firstweekday) % 7
        }

        def pick_weekdays[T](days: List[T]): List[T] = {
            firstweekday until (firstweekday+7) map { i => days(i % 7) } toList
        }

        val day_names = pick_weekdays(day_abbrs)
        val week_days = pick_weekdays(List(workday)*5 ++ List(weekend)*2)

        object source extends ColumnDataSource {
            val days            = column { day_names*month_weeks }
            val weeks           = column { (0 until month_weeks).flatMap(week => List(week.toString)*7) }
            val day_labels      = column { month_days.map(_.map(_.toString)) }
            val day_backgrounds = column { (List(week_days)*month_weeks).flatten }
        }

        import sampledata.{us_holidays,Holiday}

        val holidays = us_holidays.collect {
            case Holiday(date, summary) if date.getYear == year && date.month == month && summary.contains("(US-OPM)") =>
                Holiday(date, summary.replace("(US-OPM)", "").trim())
        }

        object holidays_source extends ColumnDataSource {
            val holidays_days  = column {
                holidays.map(holiday => day_names(weekday(holiday.date)))
            }
            val holidays_weeks = column {
                holidays.map(holiday => ((weekday(holiday.date.withDayOfMonth(1)) + holiday.date.day) / 7).toString)
            }
            val month_holidays = column {
                holidays.map(holiday => holiday.summary)
            }
        }

        val xdr = new FactorRange().factors(day_names)
        val ydr = new FactorRange().factors((0 until month_weeks).map( _.toString).reverse.toList)

        val plot = new Plot()
            .title(month_names(month-1))
            .title_text_color(Color.DarkOliveGreen)
            .x_range(xdr)
            .y_range(ydr)
            .width(300)
            .height(300)
            .outline_line_color()

        val days_glyph = Rect().x('days).y('weeks).width(0.9).height(0.9).fill_color('day_backgrounds).line_color(Color.Silver)
        val days_renderer = new GlyphRenderer().data_source(source).glyph(days_glyph)

        val holidays_glyph = Rect().x('holidays_days).y('holidays_weeks).width(0.9).height(0.9).fill_color(Color.Pink).line_color(Color.IndianRed)
        val holidays_renderer = new GlyphRenderer().data_source(holidays_source).glyph(holidays_glyph)

        val text_glyph = Text().x('days).y('weeks).text('day_labels).text_align(TextAlign.Center).text_baseline(TextBaseline.Middle)
        val text_renderer = new GlyphRenderer().data_source(source).glyph(text_glyph)

        val xaxis = new CategoricalAxis()
            .plot(plot)
            .major_label_text_font_size(8 pt)
            .major_label_standoff(0)
            .major_tick_line_color()
            .axis_line_color()
        plot.above <<= (xaxis :: _)

        val hover_tool = new HoverTool().plot(plot).renderers(holidays_renderer :: Nil).tooltips(Tooltip("Holiday" -> "@month_holidays"))
        plot.tools := hover_tool :: Nil

        plot.renderers := xaxis :: days_renderer :: holidays_renderer :: text_renderer :: Nil

        return plot
    }

    val months = (0 until 4).map(i => (0 until 3).map(j => make_calendar(2014, 3*i + j + 1)).toList).toList
    val grid = new GridPlot().title("Calendar 2014").toolbar_location().children(months)

    val document = new Document(grid)
    val html = document.save("calendars.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
