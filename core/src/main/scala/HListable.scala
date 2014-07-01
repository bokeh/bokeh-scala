package org.continuumio.bokeh.core

import scala.reflect.macros.Context

import shapeless.HList

trait HListable {
    trait HField

    def fieldsHList[A <: HListable]: HList = macro HListable.fieldsHListImpl[A]
}

object HListable {

    def fieldsHListImpl[A <: HListable: c.WeakTypeTag](c: Context): c.Expr[HList] = {
        import c.universe._

        val members = weakTypeOf[A].members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< weakTypeOf[HListable#HField])

        val hlist = members.foldRight(q"shapeless.HNil": Tree) {
            case (member, acc) =>
                val field = Select(c.prefix.tree, member.name)
                q"(${member.name.decoded}, $field) :: $acc"
        }

        c.Expr[HList](hlist)
    }
}
