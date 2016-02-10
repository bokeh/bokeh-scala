package io.continuum.bokeh

import java.io.File
import java.net.URI

trait TagImplicits {
    import scalatags.Text.short._
    import scalatags.Text.tags2.style

    implicit class StringToTag(str: String) {
        def asScript: Tag = script(raw(str))
        def asStyle: Tag = style(raw(str))
    }

    implicit class FileToTag(file: File) {
        def asScript: Tag = script(*.src:=file.getPath)
        def asStyle: Tag = link(*.rel:="stylesheet", *.href:=file.getPath)
    }

    implicit class URIToTag(url: URI) {
        def asScript: Tag = script(*.src:=url.toString)
        def asStyle: Tag = link(*.rel:="stylesheet", *.href:=url.toString)
    }
}
