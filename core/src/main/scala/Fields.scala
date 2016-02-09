package io.continuum.bokeh

import scala.reflect.macros.blackbox.Context

import upickle.Js

trait AbstractField {
    type ValueType

    def valueOpt: Option[ValueType]
    def value: ValueType

    def set(value: Option[ValueType])
    def isDirty: Boolean

    def toJson: Js.Value
}

case class FieldRef(name: String, field: AbstractField)

object Fields {
    def fields[T](obj: T): List[FieldRef] = macro fieldsImpl[T]

    def fieldsImpl[T: c.WeakTypeTag](c: Context)(obj: c.Expr[T]): c.Expr[List[FieldRef]] = {
        import c.universe._

        val bokeh = q"io.continuum.bokeh"

        val refs = weakTypeOf[T].members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< typeOf[AbstractField])
            .map { member =>
                q"$bokeh.FieldRef(${member.name.decodedName.toString}, $obj.${member.name.toTermName})"
            }

        c.Expr[List[FieldRef]](q"scala.List(..$refs)")
    }
}
