package io.continuum.bokeh
package sampledata

import java.io.{File,InputStream,FileInputStream,InputStreamReader,FileNotFoundException}
import java.util.zip.GZIPInputStream
import java.net.URL

import scala.collection.JavaConverters._

import scalax.io.JavaConverters._
import scalax.file.Path

import au.com.bytecode.opencsv.CSVReader

import net.fortuna.ical4j.model.{Calendar,Component}
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.data.CalendarBuilder

object SampleData {
    lazy val dataPath: Path = {
        val home = Path.fromString(System.getProperty("user.home"))
        val path = home / ".bokeh" / "data"
        if (!path.exists) path.createDirectory()
        path
    }

    def getStreamFromResources(fileName: String): Option[InputStream] = {
        Option(getClass.getClassLoader.getResourceAsStream(fileName))
    }

    def getStreamFromFile(fileName: String): Option[InputStream] = {
        val filePath = dataPath / fileName
        val fileOption = if (filePath.exists) filePath.fileOption else download(fileName)
        fileOption.map(new FileInputStream(_))
    }

    def getFileStream(fileName: String): Option[InputStream] = {
        getStreamFromResources(fileName) orElse getStreamFromFile(fileName)
    }

    def getGZipStream(fileName: String): Option[InputStream] = {
        getFileStream(fileName + ".gz").map(new GZIPInputStream(_))
    }

    def getStream(fileName: String): InputStream = {
        getFileStream(fileName) orElse getGZipStream(fileName) getOrElse {
            throw new FileNotFoundException(s"can't locate $fileName(.gz) in resources, .bokeh/data or S3")
        }
    }

    val dataUrl = new URL("https://s3.amazonaws.com/bokeh_data/")

    def download(fileName: String): Option[File] = {
        val url = new URL(dataUrl, fileName)

        val input = url.asInput
        val output = dataPath / fileName

        input.size match {
            case Some(size) =>
                println(s"Downloading $url to ${output.path} (${size} bytes) ...")
                output.write(input.bytes)
                output.fileOption
            case None =>
                None
        }
    }
}

trait SampleData

trait CSVSampleData extends SampleData {
    protected def loadRows(fileName: String): List[List[String]] = {
        val input = new InputStreamReader(SampleData.getStream(fileName))
        val reader = new CSVReader(input, ',', '"', '\\', 1)
        reader.readAll().asScala.map(_.map(_.trim).toList).toList
    }
}

trait ICalSampleData {
    protected def loadEvents(fileName: String): List[VEvent] = {
        val input = SampleData.getStream(fileName)
        val builder = new CalendarBuilder()
        val calendar = builder.build(input)
        val components = calendar.getComponents(Component.VEVENT)
        components.asScala.toList.collect { case event: VEvent => event }
    }
}
