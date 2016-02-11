package io.continuum.bokeh
package sampledata

object WorldCities extends CSVSampleData {
    def load(): List[WorldCity] = {
        loadRows(Zip("world_cities.csv")).map {
            case List(name, lat, lng) => WorldCity(name, lat.toDouble, lng.toDouble)
        }
    }
}

case class WorldCity(name: String, lat: Double, lng: Double)
