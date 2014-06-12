package org.continuumio.bokeh
package sampledata

import java.net.URL

import scalax.io.JavaConverters._
import scalax.file.Path

import au.com.bytecode.opencsv.CSVReader
import java.io.FileReader

import scala.collection.JavaConverters._

object SampleData {
    lazy val dataPath: Path = {
        val home = Path.fromString(System.getProperty("user.home"))
        val path = home / ".bokeh" / "data"
        if (!path.exists) path.createDirectory()
        path
    }

    def load(fileName: String): List[Array[String]] = {
        val file = dataPath / fileName
        file.size match {
            case Some(0) | None => download(fileName)
            case _              =>
        }
        val reader = new CSVReader(new FileReader(file.path), ',', '"', '\\', 1)
        reader.readAll().asScala.toList
    }


    val dataUrl = new URL("https://s3.amazonaws.com/bokeh_data/")

    def download(fileName: String) {
        val input = new URL(dataUrl, fileName)
        val output = dataPath / fileName

        println(s"Downloading $input to ${output.path} ...")

        val bytes = input.asInput.bytes
        bytes.size
        output.write(bytes)
    }
}

trait SampleData {
    def load(fileName: String): List[Array[String]] = SampleData.load(fileName)
}
