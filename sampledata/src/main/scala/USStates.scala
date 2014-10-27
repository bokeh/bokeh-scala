package io.continuum.bokeh
package sampledata

object USStates extends SampleData {
    type Value = Map[USState, USStateData]

    def load(): Value = {
        loadRows("US_States.csv").collect {
            case Array(region, name, USState(state), geom, _) =>
                val coords = xml.XML.loadString(geom) \\ "outerBoundaryIs" \ "LinearRing" \ "coordinates"
                val Array(lons, lats, _) = coords.head.text.split(" ").map(_.split(",").map(_.toDouble)).transpose
                state -> USStateData(name, region, lats.toList, lons.toList)
        } toMap
    }
}

case class USStateData(name: String, region: String, lats: List[Double], lons: List[Double])

sealed trait USState extends EnumType
@enum object USState extends Enumerated[USState] {
    case object AK extends USState
    case object AL extends USState
    case object AR extends USState
    case object AZ extends USState
    case object CA extends USState
    case object CO extends USState
    case object CT extends USState
    case object DC extends USState
    case object DE extends USState
    case object FL extends USState
    case object GA extends USState
    case object HI extends USState
    case object IA extends USState
    case object ID extends USState
    case object IL extends USState
    case object IN extends USState
    case object KS extends USState
    case object KY extends USState
    case object LA extends USState
    case object MA extends USState
    case object MD extends USState
    case object ME extends USState
    case object MI extends USState
    case object MN extends USState
    case object MO extends USState
    case object MS extends USState
    case object MT extends USState
    case object NC extends USState
    case object ND extends USState
    case object NE extends USState
    case object NH extends USState
    case object NJ extends USState
    case object NM extends USState
    case object NV extends USState
    case object NY extends USState
    case object OH extends USState
    case object OK extends USState
    case object OR extends USState
    case object PA extends USState
    case object RI extends USState
    case object SC extends USState
    case object SD extends USState
    case object TN extends USState
    case object TX extends USState
    case object UT extends USState
    case object VA extends USState
    case object VT extends USState
    case object WA extends USState
    case object WI extends USState
    case object WV extends USState
    case object WY extends USState
}
