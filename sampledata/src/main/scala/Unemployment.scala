package io.continuum.bokeh
package sampledata

object Unemployment extends CSVSampleData {
    type Value = Map[(Int, Int), Double]

    def load(): Value = {
        loadRows("unemployment09.csv").collect {
            case Array(_, stateId, countyId, _, _, _, _, _, rate) =>
                (stateId.toInt, countyId.toInt) -> rate.toDouble
        } toMap
    }
}
