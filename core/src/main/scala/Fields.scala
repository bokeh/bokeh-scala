package io.continuum.bokeh

import scala.annotation.StaticAnnotation
import scala.reflect.macros.Context

import play.api.libs.json.{Writes,JsValue,JsObject}

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
    def toJson[T](obj: T): JsValue = macro toJsonImpl[T]

    def toJsonImpl[T: c.WeakTypeTag](c: Context)(obj: c.Expr[T]): c.Expr[JsValue] = {
        import c.universe._

        case class Member(member: ModuleSymbol, name: String, valueType: Type, neededImplicit: Tree)

        val members = weakTypeOf[T].members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< typeOf[AbstractField])
            .map { member =>
                val memberType = member.typeSignature
                val valueType = memberType.member(newTypeName("ValueType")).typeSignatureIn(memberType)
                val writesType = appliedType(typeOf[Writes[_]].typeConstructor, valueType)
                val neededImplicit = c.inferImplicitValue(writesType)
                Member(member, member.name.decoded, valueType, neededImplicit)
            }

        val missingWrites = members.collect {
            case Member(_, _, valueType, EmptyTree) => valueType
        }

        if (missingWrites.nonEmpty) {
            val types = missingWrites.toList.distinct.mkString(", ")
            c.abort(c.enclosingPosition, s"No implicit writes for $types available.")
        }

        val play = q"play.api.libs.json"

        val values = members.map { case Member(member, name, _, neededImplicit) =>
            val valueOpt = q"$obj.${member.name}.valueOpt"
            q"($name, $valueOpt.map($neededImplicit.writes _).getOrElse($play.JsNull))"
        }

        c.Expr[JsValue](q"$play.JsObject(List(..$values))")
    }

    def macroTransformImpl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
        import c.universe._

        annottees.map(_.tree) match {
            case ClassDef(mods, name, tparams, tpl @ Template(parents, sf, body)) :: companion =>
                val method = q"""
                    override def toJson: play.api.libs.json.JsValue = {
                        import io.continuum.bokeh.Formats._
                        io.continuum.bokeh.Fields.toJson(this)
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
