package io.continuum.bokeh
package sampledata

object Sprint extends CSVSampleData {
    def load(): SprintData = {
        val List(name, country, medal, time, year) = loadRows("sprint.csv").transpose
        SprintData(name, country, medal.map(Medal.fromString), time.map(_.toDouble), year.map(_.toInt))
    }
}

sealed trait Medal extends EnumType with UpperCase
@enum object Medal extends Enumerated[Medal] {
    case object Gold extends Medal
    case object Silver extends Medal
    case object Bronze extends Medal
}

case class SprintData(name: List[String], country: List[String], medal: List[Medal], time: List[Double], year: List[Int])
