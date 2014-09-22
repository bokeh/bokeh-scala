package io.continuum.bokeh
package widgets

@model abstract class TableWidget extends Widget {
    object source extends Field[DataSource]
}

@model class TableColumn extends Widget {
    object field extends Field[String]
    object header extends Field[String]
    object `type` extends Field[ColumnType]
    object format extends Field[String]
    object source extends Field[List[String]]
    object strict extends Field[Boolean](false)
    object checked extends Field[String]("true")
    object unchecked extends Field[String]("false")
}

@model class HandsonTable extends TableWidget {
    object columns extends Field[List[TableColumn]]
    object sorting extends Field[Boolean](true)
}
