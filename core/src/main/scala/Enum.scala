package io.continuum.bokeh

import scala.annotation.StaticAnnotation
import scala.reflect.macros.blackbox.Context

trait EnumType {
    protected def nameFn(name: String): String = name

    val name: String = nameFn(Utils.getClassName(this))

    final override def toString = name
}

trait LowerCase { self: EnumType =>
    override def nameFn(name: String) = name.toLowerCase
}

trait UpperCase { self: EnumType =>
    override def nameFn(name: String) = name.toUpperCase
}

trait SnakeCase { self: EnumType =>
    override def nameFn(name: String) = Utils.snakify(name)
}

trait DashCase { self: EnumType =>
    override def nameFn(name: String) = Utils.snakify(name, '-')
}

trait Enumerated[T <: EnumType] {
    type ValueType = T

    val values: List[ValueType]
    val fromString: PartialFunction[String, ValueType]

    final def unapply(name: String): Option[ValueType] = fromString.lift(name)

    override def toString: String = {
        s"${Utils.getClassName(this)}(${values.map(_.name).mkString(", ")})"
    }
}

class enum extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro EnumImpl.enumTransformImpl
}

private object EnumImpl {
    def enumTransformImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
        import c.universe._

        annottees.map(_.tree) match {
            case ModuleDef(mods, name, tpl @ Template(parents, sf, body)) :: Nil =>
                val enumImpl = reify { EnumImpl }
                val methods = List(
                    q"final override val values: List[ValueType] = $enumImpl.values(this)",
                    q"final override val fromString: PartialFunction[String, ValueType] = $enumImpl.fromString(this)")
                val module = ModuleDef(mods, name, Template(parents, sf, body ++ methods))
                c.Expr[Any](Block(module :: Nil, Literal(Constant(()))))
            case _ => c.abort(c.enclosingPosition, "@enum annotation can only be applied to an object")
        }
    }

    def values[E <: Enumerated[_]](enum: E): List[enum.ValueType] = macro EnumImpl.valuesImpl[E]

    def valuesImpl[E <: Enumerated[_] : c.WeakTypeTag]
            (c: Context)(enum: c.Expr[E]): c.Expr[List[enum.value.ValueType]] = {
        import c.universe._

        val refs = members(c)(enum).map(_.name.toTermName)
        c.Expr[List[enum.value.ValueType]](q"scala.List(..$refs)")
    }

    def fromString[E <: Enumerated[_]](enum: E): PartialFunction[String, enum.ValueType] = macro EnumImpl.fromStringImpl[E]

    def fromStringImpl[E <: Enumerated[_] : c.WeakTypeTag]
            (c: Context)(enum: c.Expr[E]): c.Expr[PartialFunction[String, enum.value.ValueType]] = {
        import c.universe._

        val cases = members(c)(enum).map(_.name.toTermName).map { child => cq"$child.name => $child" }
        c.Expr[PartialFunction[String, enum.value.ValueType]](q"{ case ..$cases }: PartialFunction[String, ValueType]")
    }

    private def members[E <: Enumerated[_] : c.WeakTypeTag]
            (c: Context)(enum: c.Expr[E]): List[c.universe.Symbol] = {
        import c.universe._

        weakTypeOf[E]
            .members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< weakTypeOf[enum.value.ValueType])
            .toList
            .reverse // XXX: why?
    }
}
