package io.continuum.bokeh
package sampledata
package webbrowsers

import scalax.io.JavaConverters._
import javax.xml.bind.DatatypeConverter

object WebBrowsers extends CSVSampleData {
    val Version = """^(.+)\s+([^\s]+)$""".r

    def load(fileName: String): WebBrowsersData = {
        val List(browser_version, share) = loadRows(fileName).transpose
        val (browser, version) = browser_version.map(_.trim).map {
            case Version(browser, version) => (browser, version)
            case browser                   => (browser, "0")
        }.unzip

        WebBrowsersData(browser, version, share.map(_.toDouble))
    }
}

case class WebBrowsersData(browser: List[String], version: List[String], share: List[Double])

object WebBrowserIcons {
    private def load(browser: String): String = {
        val path = s"icons/${browser.toLowerCase}_32x32.png"
        val data = SampleData.getStream(path).asInput.bytes.toArray
        s"data:image/png;base64,${DatatypeConverter.printBase64Binary(data)}"
    }

    val Chrome  = load("Chrome")
    val Firefox = load("Firefox")
    val Safari  = load("Safari")
    val Opera   = load("Opera")
    val IE      = load("IE")
}
