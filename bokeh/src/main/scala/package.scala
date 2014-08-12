package io.continuum

import java.io.File

package object bokeh {
    implicit class IntPercent(value: Int) {
        def %% : Percent = Percent(value.toDouble/100)
    }

    implicit def DoubleToPercent(value: Double): Percent = Percent(value)

    implicit class BooleanOps(val bool: Boolean) extends AnyVal {
        final def option[A](value: => A): Option[A] = if (bool) Some(value) else None
    }

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
            // <![CDATA[
            $script
            // ]]>
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
}
