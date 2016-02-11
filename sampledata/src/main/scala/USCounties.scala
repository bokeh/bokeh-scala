package io.continuum.bokeh
package sampledata

object USCounties extends CSVSampleData {
    type Value = Map[(Int, Int), USCountyData]

    def load(): Value = {
        loadRows(Zip("US_Counties.csv")).collect {
            case List(name, _, _, USState(state), geom, _, _, _, _, stateId, countyId, _, _) =>
                val coords = xml.XML.loadString(geom) \\ "outerBoundaryIs" \ "LinearRing" \ "coordinates" head
                val Array(lons, lats) = coords.text.split(" ").map(_.split(",").map(_.toDouble)).transpose
                (stateId.toInt, countyId.toInt) -> USCountyData(name, state, lats.toList, lons.toList)
        } toMap
    }
}

case class USCountyData(name: String, state: USState, lats: List[Double], lons: List[Double])
