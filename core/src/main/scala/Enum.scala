package io.continuum.bokeh

import scala.reflect.macros.Context

trait EnumType {
    def name = toString.toLowerCase
}

trait Enum[T <: EnumType] {
    type ValueType = T

    def values: Set[T] = macro EnumImpl.valuesImpl[T]
    // def unapply(string: String): Option[T] = macro EnumImpl.unapplyImpl[T]
    def fromString: PartialFunction[String, T] = macro EnumImpl.fromStringImpl[T]
}

object EnumImpl {
    def valuesImpl[T <: EnumType : c.WeakTypeTag](c: Context): c.Expr[Set[T]] = {
        import c.universe._

        val tpe = weakTypeOf[T]
        val children = tpe.typeSymbol.asClass.knownDirectSubclasses

        if (children.isEmpty) {
            c.error(c.enclosingPosition, "no direct subclasses found")
        }

        val values = children.map { child =>
            c.Expr(Select(c.prefix.tree, newTermName(child.name.decoded)))
        }

        c.Expr[Set[T]](q"Set[$tpe](..$values)")
    }

    def unapplyImpl[T <: EnumType : c.WeakTypeTag](c: Context)(string: c.Expr[String]): c.Expr[Option[T]] = {
        import c.universe._

        val tpe = weakTypeOf[T]
        val children = tpe.typeSymbol.asClass.knownDirectSubclasses

        if (children.isEmpty) {
            c.error(c.enclosingPosition, "no direct subclasses found")
        }

        val cases = children.map { child =>
            val name = child.name.decoded
            val value = newTermName(name)
            cq"$name => Some(${c.prefix.tree}.$value)"
        }

        c.Expr[Option[T]](q"${string.tree} match { case ..$cases; case _ => None }")
    }

    def fromStringImpl[T <: EnumType : c.WeakTypeTag](c: Context): c.Expr[PartialFunction[String, T]] = {
        import c.universe._

        val tpe = weakTypeOf[T]
        val children = tpe.typeSymbol.asClass.knownDirectSubclasses

        if (children.isEmpty) {
            c.error(c.enclosingPosition, "no direct subclasses found")
        }

        val cases = children.map { child =>
            val name = child.name.decoded
            val ret = newTermName(name)
            cq"$name => ${c.prefix.tree}.$ret"
        }

        c.Expr[PartialFunction[String, T]](q"{ case ..$cases }")
    }
}
