package org.continuumio.bokeh.macros

import scala.reflect.macros.Context
import shapeless._

trait HListable {
    trait HField
}

object HListable {
    // http://meta.plasm.us/posts/2013/06/21/macro-methods-and-subtypes/

    implicit class HListThisThing[A <: HListable](val self: A) extends AnyVal {
        def fields: HList = macro HListable.fieldsImpl[A]
    }

    def fieldsImpl[A <: HListable: c.WeakTypeTag](c: Context): c.Expr[HList] = {
        import c.universe._

        val self = Select(c.prefix.tree, newTermName("self"))

        val members = weakTypeOf[A].members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< weakTypeOf[HListable#HField])

        members.foldRight(reify(HNil: HList)) {
            case (member, acc) =>
                val field = c.Expr(Literal(Constant(member.name.decoded)))
                val value = c.Expr(Select(self, member.name))
                reify((field.splice, value.splice) :: acc.splice)
        }
    }
}
