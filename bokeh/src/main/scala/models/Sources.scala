package io.continuum.bokeh

import play.api.libs.json.Writes

@model class ColumnsRef extends HasFields {
    object source extends Field[DataSource]
    object columns extends Field[List[Symbol]]
}

@model sealed abstract class DataSource extends PlotObject {
    object column_names extends Field[List[String]]
    object selected extends Field[List[Int]]

    def columns(columns: Symbol*): ColumnsRef =
        new ColumnsRef().source(this).columns(columns.toList)
}

@model class ColumnDataSource extends DataSource {
    final override val typeName = "ColumnDataSource"

    object data extends Field[Map[Symbol, Any]]

    def column[M[_], T](value: M[T]): Column[M, T] = macro ColumnMacro.columnImpl[M, T]

    def addColumn[A:ArrayLike:Writes](name: Symbol, array: A): SelfType = {
        data := data.value + (name -> array)
        this
    }
}
