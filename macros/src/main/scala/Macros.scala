package org.continuumio.bokeh.macros

import scala.reflect.macros.Context
import play.api.libs.json.{Reads,Writes,Format}

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

object JsonImpl {
    def writesImpl[T: c.WeakTypeTag](c: Context): c.Expr[Writes[T]] = {
        import c.universe._

        val tpe = weakTypeOf[T]
        val tpeSymbol = tpe.typeSymbol

        if (!tpeSymbol.isClass || tpeSymbol.asClass.isAbstractClass)
            c.abort(c.enclosingPosition, "expected a non-abstract class")

        val fieldNames = tpeSymbol
            .asClass
            .typeSignature
            .members
            .toList
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.moduleClass
                     .asClass
                     .baseClasses
                     .map(_.fullName)
                     .contains("org.continuumio.bokeh.Field"))
            .map(_.name.decoded)

        val fieldDefs = fieldNames.map { fieldName =>
            val fieldTerm = newTermName(fieldName)
            q"($fieldName, play.api.libs.json.Json.toJson(obj.$fieldTerm.valueOpt))"
        }

        c.Expr[Writes[T]](
            q"""
            new play.api.libs.json.Writes[$tpeSymbol] {
                def writes(obj: $tpeSymbol) =
                    play.api.libs.json.JsObject(List(..$fieldDefs))
            }
            """)
    }
}
