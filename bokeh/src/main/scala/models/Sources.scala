package io.continuum.bokeh

import play.api.libs.json.Writes

case class Selected0d(indices: List[Int] = Nil, flag: Boolean = false)
case class Selected1d(indices: List[Int] = Nil)
case class Selected2d(indices: List[List[Int]] = Nil)

case class Selected(`0d`: Selected0d = Selected0d(),
                    `1d`: Selected1d = Selected1d(),
                    `2d`: Selected2d = Selected2d())

@model abstract class DataSource extends Model {
    object column_names extends Field[List[String]]
    object selected extends Field[Selected]
    object callback extends Field[Callback]
}

@model class ColumnDataSource extends DataSource { source =>
    final override val typeName = "ColumnDataSource"

    object data extends Field[Map[Symbol, Any]]

    class Column[M[_]: ArrayLike, T](val name: Symbol, _value: M[T]) {
        this := _value

        def value: M[T] = source.data.value(name).asInstanceOf[M[T]]
        def :=(value: M[T]): Unit = source.addColumn(name, value)
    }

    def column[M[_], T](value: M[T]): ColumnDataSource#Column[M, T] = macro ColumnMacro.columnImpl[M, T]

    def addColumn[M[_]: ArrayLike, T](name: Symbol, value: M[T]): SelfType = {
        data <<= (_ + (name -> value))
        this
    }
}

private[bokeh] object ColumnMacro {
    import scala.reflect.macros.Context

    def columnImpl[M[_], T](c: Context)(value: c.Expr[M[T]])
            (implicit ev1: c.WeakTypeTag[M[_]], ev2: c.WeakTypeTag[T]): c.Expr[ColumnDataSource#Column[M, T]] = {
        import c.universe._

        val name = definingValName(c).map(name => c.Expr[String](Literal(Constant(name)))) getOrElse {
            c.abort(c.enclosingPosition, "column must be directly assigned to a val, such as `val x1 = column(List(1.0, 2.0, 3.0))`")
        }

        c.Expr[ColumnDataSource#Column[M, T]](q"new Column(Symbol($name), $value)")
    }

    def definingValName(c: Context): Option[String] = {
        import c.universe._

        c.enclosingClass.collect {
            case ValDef(_, name, _, rhs) if rhs.pos == c.macroApplication.pos => name.encoded
        }.headOption
    }
}

@model abstract class RemoteSource extends DataSource {
    object data_url extends Field[String]
    object polling_interval extends Field[Int]
}

@model class AjaxDataSource extends RemoteSource {
    object method extends Field[HTTPMethod](HTTPMethod.POST)
}
