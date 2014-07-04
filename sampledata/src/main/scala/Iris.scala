package io.continuum.bokeh
package sampledata
package iris

object Flowers extends SampleData {
    def load(): FlowersData = {
        val List(petal_length, petal_width, sepal_length, sepal_width, species) = loadRows("iris.csv").transpose
        FlowersData(petal_length.map(_.toDouble),
                    petal_width.map(_.toDouble),
                    sepal_length.map(_.toDouble),
                    sepal_width.map(_.toDouble),
                    species)
    }
}

case class FlowersData(
    petal_length: List[Double],
    petal_width: List[Double],
    sepal_length: List[Double],
    sepal_width: List[Double],
    species: List[String])
