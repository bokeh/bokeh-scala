package org.continuumio.bokeh.core

import scala.reflect.macros.Context
import play.api.libs.json.Writes

object Macros {
    def membersImpl[A: c.WeakTypeTag](c: Context): c.Expr[List[String]] = {
        import c.universe._
        val tpe = weakTypeOf[A]
        val members = tpe.members.map(_.name.decoded).toList.distinct
        val literals = members.map(member => Literal(Constant(member)))
        c.Expr[List[String]](Apply(reify(List).tree, literals))
    }

    def members[A] = macro membersImpl[A]

    def fieldsImpl[A: c.WeakTypeTag](c: Context): c.Expr[List[String]] = {
        import c.universe._

        val tpe = weakTypeOf[A]
        val tpeSymbol = tpe.typeSymbol

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

        val fields = fieldNames.map { fieldName =>
            Literal(Constant(fieldName))
        }

        c.Expr[List[String]](
            Apply(reify(List).tree, fields)
        )
    }

    def fields[A] = macro fieldsImpl[A]
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

    def sealedWritesImpl[T: c.WeakTypeTag](c: Context): c.Expr[Writes[T]] = {
        import c.universe._

        val tpe = weakTypeOf[T]
        val symbol = tpe.typeSymbol

        if (!symbol.isClass) {
            c.abort(c.enclosingPosition, "expected a class or trait")
        }

        val cls = symbol.asClass

        if (!cls.isSealed) {
            c.abort(c.enclosingPosition, "expected a sealed trait")
        } else {
            val children = cls.knownDirectSubclasses.toList

            if (children.isEmpty) {
                c.abort(c.enclosingPosition, "trait has no subclasses")
            } else {
                val named = children.map { child =>
                    (child, newTermName("$writes$" + child.name.toString))
                }

                val valDefs = named.map { case (child, name) =>
                    q"val $name = org.continuumio.bokeh.BokehJson.writes[$child]"
                }

                val caseDefs = named.map { case (child, name) =>
                    CaseDef(
                        Bind(newTermName("o"), Typed(Ident(nme.WILDCARD),
                             Ident(child))),
                        EmptyTree,
                        q"$name.writes(o)")
                }

                val names = children.flatMap(
                    _.typeSignature
                     .declaration(nme.CONSTRUCTOR)
                     .asMethod
                     .paramss(0)
                     .map(_.name.toString)
                 ).toSet

                val fieldNames = cls.typeSignature
                   .declarations
                   .toList
                   .filter(_.isMethod)
                   .map(_.asMethod)
                   .filter(_.isStable)
                   .filter(_.isPublic)
                   .map(_.name.toString)
                   .filterNot(names contains _)

                val fieldDefs = fieldNames.map { fieldName =>
                    val name = newTermName(fieldName)
                    q"($fieldName, play.api.libs.json.Json.toJson(obj.$name))"
                }

                val matchDef = Match(q"obj", caseDefs)

                c.Expr[Writes[T]](
                    q"""
                    new Writes[$symbol] {
                        ..$valDefs

                        def writes(obj: $symbol) =
                            $matchDef ++ play.api.libs.json.JsObject(List(..$fieldDefs))
                    }
                    """)
            }
        }
    }
}
