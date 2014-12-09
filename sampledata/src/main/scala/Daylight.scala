package io.continuum.bokeh
package sampledata
package daylight

import org.joda.time.{LocalDate,LocalTime}

object Daylight extends CSVSampleData {
    def load(fileName: String): DaylightData = {
        val List(date, sunrise, sunset, summer) = loadRows(fileName).transpose
        DaylightData(date.map(LocalDate.parse),
                     sunrise.map(LocalTime.parse),
                     sunset.map(LocalTime.parse),
                     summer.map(_.toBoolean))
    }
}

case class DaylightData(
    date: List[LocalDate],
    sunrise: List[LocalTime],
    sunset: List[LocalTime],
    summer: List[Boolean]) {

    def summerOnly: DaylightData = {
        DaylightData(
            date.zip(summer).collect { case (date, true) => date },
            sunrise.zip(summer).collect { case (sunrise, true) => sunrise },
            sunset.zip(summer).collect { case (sunset, true) => sunset },
            summer.filter(_ == true))
    }
}
