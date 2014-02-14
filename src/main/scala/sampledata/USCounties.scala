package org.continuumio.bokeh.sampledata

object USCounties extends SampleData {
    type Value = Map[(Int, Int), USCountyData]

    def load(): Value = {
        load("US_Counties.csv").collect {
            case Array(name, _, _, code, geom, _, _, _, _, stateId, countyId, _, _) =>
                val coords = xml.XML.loadString(geom) \\ "outerBoundaryIs" \ "LinearRing" \ "coordinates"
                val Array(lats, lons, _) = coords.head.text.split(" ").map(_.split(",").map(_.toDouble)).transpose
                (stateId.toInt, countyId.toInt) -> USCountyData(name, USState.fromString(code), lats.toList, lons.toList)
        } toMap
    }
}

case class USCountyData(name: String, state: USState, lats: List[Double], lons: List[Double])
