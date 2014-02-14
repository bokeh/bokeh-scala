package org.continuumio.bokeh.sampledata

object USStates extends SampleData {
    type Value = Map[USState, USStateData]

    def load(): Value = {
        load("US_States.csv").collect {
            case Array(region, name, code, geom, _) =>
                val coords = xml.XML.loadString(geom) \\ "outerBoundaryIs" \ "LinearRing" \ "coordinates"
                val Array(lats, lons, _) = coords.head.text.split(" ").map(_.split(",").map(_.toDouble)).transpose
                USState.fromString(code) -> USStateData(name, region, lats.toList, lons.toList)
        } toMap
    }
}

case class USStateData(name: String, region: String, lats: List[Double], lons: List[Double])

sealed trait USState
object USState {
    def fromString: PartialFunction[String, USState] = {
        case "AK" => AK
        case "AL" => AL
        case "AR" => AR
        case "AZ" => AZ
        case "CA" => CA
        case "CO" => CO
        case "CT" => CT
        case "DC" => DC
        case "DE" => DE
        case "FL" => FL
        case "GA" => GA
        case "HI" => HI
        case "IA" => IA
        case "ID" => ID
        case "IL" => IL
        case "IN" => IN
        case "KS" => KS
        case "KY" => KY
        case "LA" => LA
        case "MA" => MA
        case "MD" => MD
        case "ME" => ME
        case "MI" => MI
        case "MN" => MN
        case "MO" => MO
        case "MS" => MS
        case "MT" => MT
        case "NC" => NC
        case "ND" => ND
        case "NE" => NE
        case "NH" => NH
        case "NJ" => NJ
        case "NM" => NM
        case "NV" => NV
        case "NY" => NY
        case "OH" => OH
        case "OK" => OK
        case "OR" => OR
        case "PA" => PA
        case "RI" => RI
        case "SC" => SC
        case "SD" => SD
        case "TN" => TN
        case "TX" => TX
        case "UT" => UT
        case "VA" => VA
        case "VT" => VT
        case "WA" => WA
        case "WI" => WI
        case "WV" => WV
        case "WY" => WY
    }

    object AK extends USState
    object AL extends USState
    object AR extends USState
    object AZ extends USState
    object CA extends USState
    object CO extends USState
    object CT extends USState
    object DC extends USState
    object DE extends USState
    object FL extends USState
    object GA extends USState
    object HI extends USState
    object IA extends USState
    object ID extends USState
    object IL extends USState
    object IN extends USState
    object KS extends USState
    object KY extends USState
    object LA extends USState
    object MA extends USState
    object MD extends USState
    object ME extends USState
    object MI extends USState
    object MN extends USState
    object MO extends USState
    object MS extends USState
    object MT extends USState
    object NC extends USState
    object ND extends USState
    object NE extends USState
    object NH extends USState
    object NJ extends USState
    object NM extends USState
    object NV extends USState
    object NY extends USState
    object OH extends USState
    object OK extends USState
    object OR extends USState
    object PA extends USState
    object RI extends USState
    object SC extends USState
    object SD extends USState
    object TN extends USState
    object TX extends USState
    object UT extends USState
    object VA extends USState
    object VT extends USState
    object WA extends USState
    object WI extends USState
    object WV extends USState
    object WY extends USState
}
