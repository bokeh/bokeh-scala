package io.continuum.bokeh

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
}
