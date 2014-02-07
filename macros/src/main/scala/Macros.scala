package org.continuumio.bokeh.macros

import scala.reflect.macros.Context

object Macros {
    def membersImpl[A: c.WeakTypeTag](c: Context): c.Expr[List[String]] = {
        import c.universe._
        val tpe = weakTypeOf[A]
        val members = tpe.declarations.map(_.name.decoded).toList.distinct
        val literals = members.map(member => Literal(Constant(member)))
        c.Expr[List[String]](Apply(reify(List).tree, literals))
    }

    def members[A] = macro membersImpl[A]
}
