package io.continuum.bokeh

import scala.reflect.macros.Context

import play.api.libs.json.JsValue

trait AbstractField {
    type ValueType

    def valueOpt: Option[ValueType]
    def value: ValueType

    def set(value: Option[ValueType])

    def toJson: Option[JsValue]
}

case class FieldRef(name: String, field: AbstractField)

object Fields {
    def fields[T](obj: T): List[FieldRef] = macro fieldsImpl[T]

    def fieldsImpl[T: c.WeakTypeTag](c: Context)(obj: c.Expr[T]): c.Expr[List[FieldRef]] = {
        import c.universe._

        val refs = weakTypeOf[T].members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< typeOf[AbstractField])
            .map { member =>
                q"FieldRef(${member.name.decoded}, $obj.${member.name.toTermName})"
            }

        c.Expr[List[FieldRef]](q"List(..$refs)")
    }
}
