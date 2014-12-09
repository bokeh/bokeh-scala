package io.continuum.bokeh
package sampledata

import org.joda.time.{LocalDate=>Date}

object USHolidays extends ICalSampleData {
    implicit val DateOrdering: Ordering[Date] = Ordering.fromLessThan(_ isBefore _)

    def load(): List[Holiday] = {
        loadEvents("USHolidays.ics").map { event =>
            Holiday(new Date(event.getStartDate.getDate), event.getSummary.getValue)
        }.sortBy(_.date)
    }
}

case class Holiday(date: Date, summary: String)
