package io.continuum.bokeh

import java.io.File
import java.net.URL

trait NodeUtils {
    implicit class StringNode(script: String) {
        def asScript: xml.Node = {
            <script type="text/javascript">{xml.Unparsed(s"""
            // <![CDATA[
            $script
            // ]]>
            """)}</script>
        }

        def asStyle: xml.Node = {
            <style>{xml.Unparsed(s"""
            $script
            """)}</style>
        }
    }

    implicit class FileNode(file: File) {
        def asScript: xml.Node = {
            <script type="text/javascript" src={file.getPath}></script>
        }

        def asStyle: xml.Node = {
            <link rel="stylesheet" href={file.getPath}></link>
        }
    }

    implicit class URLNode(url: URL) {
        def asScript: xml.Node = {
            <script type="text/javascript" src={url.toString}></script>
        }

        def asStyle: xml.Node = {
            <link rel="stylesheet" href={url.toString}></link>
        }
    }
}
