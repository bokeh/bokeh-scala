package io.continuum.bokeh
package sampledata
package mtb

object MTB extends CSVSampleData {
    def load(fileName: String): MTBData = {
        val List(lon, lat, alt) = loadRows(fileName).transpose
        MTBData(lon.map(_.toDouble), lat.map(_.toDouble), alt.map(_.toDouble))
    }
}

case class MTBData(lon: List[Double], lat: List[Double], alt: List[Double])
