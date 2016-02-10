package io.continuum.bokeh

trait PrettyTypedTag {
    import scalatags.{Text,Escaping}
    import scalatags.text.Builder

    private def prettyWriteTo(t: Text.TypedTag[String], strb: StringBuilder, depth: Int, step: Int): Unit = {
        val indent = " "*depth*step

        val builder = new Builder()
        t.build(builder)

        val attrs = builder.attrs.take(builder.attrIndex)
        val children = builder.children.take(builder.childIndex).toList

        def escape(s: String): String = {
            val sb = new StringBuilder
            Escaping.escape(s, sb)
            sb.toString()
        }

        strb ++= indent += '<' ++= t.tag
        strb ++= attrs.map(a => " " + a._1 + "=\"" + escape(a._2) + "\"").mkString

        if (children.isEmpty && t.void) {
            strb ++= " />"
        } else {
            strb ++= ">"

            for (child <- children) child match {
                case tag: Text.TypedTag[String] =>
                    strb ++= "\n"
                    prettyWriteTo(tag, strb, depth + 1, step)
                case _ =>
                    val indent = " " * (depth + 1) * step

                    val sb = new StringBuilder
                    child.writeTo(sb)

                    for (line <- sb.toString.split("\n")) {
                        strb += '\n'

                        if (!line.trim.isEmpty) {
                            strb ++= indent
                        }

                        strb ++= line
                    }
            }

            if (!children.isEmpty) {
                strb ++= "\n" ++= indent
            }

            strb ++= "</" ++= t.tag += '>'
        }
    }

    implicit class TypedTagOps(t: Text.TypedTag[String]) {
        def pretty(step: Int = 4): String = {
            val strb = new StringBuilder
            prettyWriteTo(t, strb, 0, step)
            strb.toString()
        }

        def pretty: String = pretty()
    }
}
