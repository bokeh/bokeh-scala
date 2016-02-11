package io.continuum.bokeh
package sampledata

import java.io.{File,InputStream,FileInputStream,InputStreamReader,FileNotFoundException}
import java.util.zip.{ZipInputStream,GZIPInputStream}
import java.net.URL

import scala.collection.JavaConverters._

import scalax.io.JavaConverters._
import scalax.file.Path

import au.com.bytecode.opencsv.CSVReader

import net.fortuna.ical4j.model.{Calendar,Component}
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.data.CalendarBuilder

object FileName {
    implicit def stringToFileName(fileName: String): FileName = Simple(fileName)
}
sealed trait FileName {
    val name: String
    def realName: String = name
}
case class Simple(name: String) extends FileName
case class GZip(name: String) extends FileName {
    override def realName = name + ".gz"
}
case class Zip(name: String) extends FileName {
    override def realName = name.substring(0, name.lastIndexOf(".")) + ".zip"
}

object SampleData {
    lazy val dataPath: Path = {
        val home = Path.fromString(System.getProperty("user.home"))
        val path = home / ".bokeh" / "data"
        if (!path.exists) path.createDirectory()
        path
    }

    def getStreamFromResources(fileName: FileName): Option[InputStream] = {
        Option(getClass.getClassLoader.getResourceAsStream(fileName.realName))
    }

    def getStreamFromFile(fileName: FileName): Option[InputStream] = {
        val filePath = dataPath / fileName.realName
        val fileOption = if (filePath.exists) filePath.fileOption else download(fileName)
        fileOption.map(new FileInputStream(_))
    }

    def getFileStream(fileName: FileName): Option[InputStream] = {
        getStreamFromResources(fileName) orElse getStreamFromFile(fileName)
    }

    def getGZipStream(fileName: FileName): Option[InputStream] = {
        getFileStream(fileName).map(new GZIPInputStream(_))
    }

    def getZipStream(fileName: FileName): Option[InputStream] = {
        getFileStream(fileName).flatMap { stream =>
            val zip = new ZipInputStream(stream)
            var entry = zip.getNextEntry()
            var found = false
            while (entry != null) {
                found = !entry.isDirectory && entry.getName == fileName.name
                if (found) {
                    entry = null
                } else {
                    zip.closeEntry()
                    entry = zip.getNextEntry()
                }
            }
            if (found) {
                Some(zip)
            } else {
                zip.close()
                None
            }
        }
    }

    def getStream(fileName: FileName): InputStream = {
        val streamOpt = fileName match {
            case Simple(_) => getFileStream(fileName)
            case GZip(_) => getGZipStream(fileName)
            case Zip(_) => getZipStream(fileName)
        }
        streamOpt getOrElse {
            throw new FileNotFoundException(s"can't locate ${fileName.name} in resources, .bokeh/data or S3")
        }
    }

    val dataUrl = new URL("https://s3.amazonaws.com/bokeh_data/")

    def download(fileName: FileName): Option[File] = {
        val url = new URL(dataUrl, fileName.realName)

        val input = url.asInput
        val output = dataPath / fileName.realName

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
    protected def loadRows(fileName: FileName): List[List[String]] = {
        val input = new InputStreamReader(SampleData.getStream(fileName))
        val reader = new CSVReader(input, ',', '"', '\\', 1)
        reader.readAll().asScala.map(_.map(_.trim).toList).toList
    }
}

trait ICalSampleData {
    protected def loadEvents(fileName: FileName): List[VEvent] = {
        val input = SampleData.getStream(fileName)
        val builder = new CalendarBuilder()
        val calendar = builder.build(input)
        val components = calendar.getComponents(Component.VEVENT)
        components.asScala.toList.collect { case event: VEvent => event }
    }
}
