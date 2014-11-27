package io.continuum.bokeh
package widgets

@model abstract class CellFormatter extends PlotObject

@model abstract class CellEditor extends PlotObject

@model class StringFormatter extends CellFormatter {
    object font_style extends Field[FontStyle]
    object text_align extends Field[TextAlign]
    object text_color extends Field[Color]
}

@model class NumberFormatter extends StringFormatter {
    object format extends Field[String]("0,0")
    object language extends Field[Language]
}

@model class BooleanFormatter extends CellFormatter {
    object icon extends Field[Checkmark]
}

@model class StringEditor extends CellEditor {
    object completions extends Field[List[String]]
}

@model class TextEditor extends CellEditor

@model class SelectEditor extends CellEditor {
    object options extends Field[List[String]]
}

@model class PercentEditor extends CellEditor

@model class CheckboxEditor extends CellEditor

@model class IntEditor extends CellEditor {
    object step extends Field[Int](1)
}

@model class NumberEditor extends CellEditor {
    object step extends Field[Double](0.01)
}

@model class TimeEditor extends CellEditor

@model class DateEditor extends CellEditor

@model class TableColumn extends PlotObject {
    object field extends Field[Symbol]
    object title extends Field[String]
    object width extends Field[Int](300)          // px
    object formatter extends Field[CellFormatter] // lambda: StringFormatter())
    object editor extends Field[CellEditor]       // lambda: StringEditor())
    object sortable extends Field[Boolean](true)
    object default_sort extends Field[Sort]
}

@model abstract class TableWidget extends Widget {
    object source extends Field[DataSource]
}

@model class DataTable extends TableWidget {
    object columns extends Field[List[TableColumn]]
    object width extends Field[Int]                     // TODO: None       // px, optional
    object height extends Field[Int](400)               // TODO: Auto       // px, required, use "auto" only for small data
    object fit_columns extends Field[Boolean](true)
    object sortable extends Field[Boolean](true)
    object editable extends Field[Boolean](false)
    object selectable extends Field[Boolean](true)      // TODO: Enum("checkbox"))
    object row_headers extends Field[Boolean](true)
}
