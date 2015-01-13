package io.continuum.bokeh

import scala.reflect.macros.Context

case class Column[M[_], T](name: String, value: M[T]) {
    def field: Symbol = Symbol(name)
}

object ColumnMacro {
    def columnImpl[M[_], T](c: Context)(value: c.Expr[M[T]])
            (implicit ev1: c.WeakTypeTag[M[_]], ev2: c.WeakTypeTag[T]): c.Expr[Column[M, T]] = {
        import c.universe._

        val name = definingValName(c).map(name => c.Expr[String](Literal(Constant(name)))) getOrElse {
            c.abort(c.enclosingPosition, "column must be directly assigned to a val, such as `val x1 = column(List(1.0, 2.0, 3.0))`")
        }

        c.Expr[Column[M, T]](q"""
            val column = Column($name, $value)
            data := data.value + (column.field -> column.value)
            column
        """)
    }

    def definingValName(c: Context): Option[String] = {
        import c.universe._

        c.enclosingClass.collect {
            case ValDef(_, name, _, rhs) if rhs.pos == c.macroApplication.pos => name.encoded
        }.headOption
    }
}
