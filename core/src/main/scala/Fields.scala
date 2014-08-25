package io.continuum.bokeh

import scala.annotation.StaticAnnotation
import scala.reflect.macros.Context

import play.api.libs.json.{Writes,JsValue}

trait AbstractField {
    type ValueType

    def valueOpt: Option[ValueType]
    def value: ValueType

    def set(value: Option[ValueType])
}

trait Refs[Ref] {
    def getRef: Ref
    def id: AbstractField { type ValueType = String }
}

object Fields {
    def values[T](obj: T): List[(String, Option[JsValue])] = macro valuesImpl[T]

    def valuesImpl[T: c.WeakTypeTag](c: Context)(obj: c.Expr[T]): c.Expr[List[(String, Option[JsValue])]] = {
        import c.universe._

        val values = weakTypeOf[T].members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< typeOf[AbstractField])
            .map { member =>
                val field = q"$obj.${member.name.toTermName}"
                q"($field.fieldName.getOrElse(${member.name.decoded}), $field.toJson)"
            }

        c.Expr[List[(String, Option[JsValue])]](q"List(..$values)")
    }

    def macroTransformImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
        import c.universe._

        annottees.map(_.tree) match {
            case ClassDef(mods, name, tparams, tpl @ Template(parents, sf, body)) :: companion =>
                val method = q"""
                    override def values: List[(String, Option[play.api.libs.json.JsValue])] = {
                        io.continuum.bokeh.Fields.values(this)
                    }
                """
                val decl = ClassDef(mods, name, tparams, Template(parents, sf, body :+ method))
                c.Expr[Any](Block(decl :: companion, Literal(Constant(()))))
            case _ => c.abort(c.enclosingPosition, "expected a class")
        }
    }
}

class fields extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro Fields.macroTransformImpl
}
