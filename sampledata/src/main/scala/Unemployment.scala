package io.continuum.bokeh
package sampledata

object Unemployment extends SampleData {
    type Value = Map[(Int, Int), Double]

    def load(): Value = {
        load("unemployment09.csv").collect {
            case Array(_, stateId, countyId, _, _, _, _, _, rate) =>
                (stateId.toInt, countyId.toInt) -> rate.toDouble
        } toMap
    }
}
