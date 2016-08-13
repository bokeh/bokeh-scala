package io.continuum.bokeh

import scala.annotation.StaticAnnotation
import scala.reflect.macros.blackbox.Context

private object ModelImpl {
    def macroTransformImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
        import c.universe._

        annottees.map(_.tree) match {
            case ClassDef(mods, name, tparams, tpl @ Template(parents, sf, body)) :: companion =>
                val bokeh = q"io.continuum.bokeh"

                def expandInclude(prefix: Option[Tree], mixin: Tree) = {
                    val fields =
                       c.typecheck(tq"$mixin", c.TYPEmode)
                        .tpe
                        .members
                        .filter(_.isModule)
                        .map(_.asModule)
                        .filter(_.typeSignature <:< typeOf[AbstractField])

                    fields.map { field =>
                        val name = prefix match {
                            case Some(prefix) => TermName(s"${prefix}_${field.name}")
                            case None         => field.name
                        }
                        val sig = field.typeSignature
                        val tpe = sig.member(TypeName("ValueType")).typeSignatureIn(sig)
                        // TODO: add support for precise field type (Vectorized, NonNegative, etc.)
                        q"object $name extends this.Field[$tpe]"
                    }
                }

                val expandedBody = body.flatMap {
                    case q"include[$mixin]" =>
                        expandInclude(None, mixin)
                    case q"$prefix = include[$mixin]" =>
                        expandInclude(Some(prefix), mixin)
                    case field => field :: Nil
                }

                val newMethods = List(q"""override def fields: scala.List[$bokeh.FieldRef] = $bokeh.Fields.fields(this)""")
                val decl = ClassDef(mods, name, tparams, Template(parents, sf, expandedBody ++ newMethods))

                c.Expr[Any](Block(decl :: companion, Literal(Constant(()))))
            case _ => c.abort(c.enclosingPosition, "expected a class")
        }
    }
}

class model extends StaticAnnotation {
    def macroTransform(annottees: Any*): Any = macro ModelImpl.macroTransformImpl
}
