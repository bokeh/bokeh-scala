package org.continuumio.bokeh.sampledata

import scalax.io.JavaConverters._
import scalax.file.Path

import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader

import scala.collection.JavaConverters._

trait SampleData {
    protected def load(fileName: String): List[Array[String]] = {
        val home = Path.fromString(System.getProperty("user.home"))
        val file = home / ".bokeh" / "data" / fileName
        val reader = new CSVReader(new FileReader(file.path), ',', '"', '\\', 1)
        reader.readAll().asScala.toList
    }
}
