package io.continuum.bokeh

import scala.reflect.macros.blackbox.Context

private [bokeh] object Macros {
    def definingValName(c: Context): Option[String] = {
        import c.universe._

        c.enclosingClass.collect {
            case ValDef(_, name, _, rhs) if rhs.pos == c.macroApplication.pos => name.encodedName.toString
        }.headOption
    }
}
