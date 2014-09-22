package io.continuum.bokeh
package sampledata

object AutoMPG extends SampleData {
    def load(): AutoMPGData = {
        val List(index, manufacturer, model, displ, year, cyl, trans, drv, cty, hwy, _, cls) =
            loadRows("autompg.csv").transpose

        def capitalizeWords(string: String) = {
            string.split(" ").map(_.capitalize).mkString(" ")
        }

        AutoMPGData(
            index = index.map(_.toInt),
            manufacturer = manufacturer.map(capitalizeWords),
            model = model.map(capitalizeWords),
            displ = displ.map(_.toDouble),
            year = year.map(_.toInt),
            cyl = cyl.map(_.toInt),
            trans = trans,
            drv = drv.map {
                case "f" => "front"
                case "r" => "rear"
                case "4" => "4x4"
            },
            cls = cls,
            cty = cty.map(_.toDouble),
            hwy = hwy.map(_.toDouble))
    }
}

case class AutoMPGData(
    index: List[Int],
    manufacturer: List[String],
    model: List[String],
    displ: List[Double],
    year: List[Int],
    cyl: List[Int],
    trans: List[String],
    drv: List[String],
    cls: List[String],
    cty: List[Double],
    hwy: List[Double])
