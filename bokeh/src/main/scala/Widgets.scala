package org.continuumio.bokeh

import java.util.Date

class Panel extends Widget {
    object title extends Field[String]
    object child extends Field[Widget]
    object closable extends Field[Boolean](false)
}

class Tabs extends Widget {
    object tabs extends Field[List[Panel]]
    object active extends Field[Int](0)
}

class Dialog extends Widget {
    object visible extends Field[Boolean](false)
    object closable extends Field[Boolean](true)
    object title extends Field[String]
    object content extends Field[String]
    object buttons extends Field[List[String]]
}

abstract class Layout extends Widget

class HBox extends Layout {
    object children extends Field[List[Widget]]
}

class VBox extends Layout {
    object children extends Field[List[Widget]]
}

class Paragraph extends Widget {
    object text extends Field[String]
}

class PreText extends Paragraph

abstract class InputWidget[T:DefaultValue] extends Widget {
    object title extends Field[String]
    object name extends Field[String]
    object value extends Field[T]
}

class TextInput extends InputWidget[String]

class Select extends InputWidget[String] {
    object options extends Field[List[String]] // List[Either[String, Dict[String, String]]]
}

class Slider extends InputWidget[Double] {
    object start extends Field[Double]
    object end extends Field[Double]
    object step extends Field[Int]
    object orientation extends Field[Orientation]
}

class DateRangeSlider extends InputWidget[(Date, Date)] {
    object bounds extends Field[(Date, Date)]
    // object range extends Field[(RelativeDelta, RelativeDelta)]
    // object step extends Field[RelativeDelta
    // object formatter extends Field[Either[String, Function[Date]]]
    // object scales extends Field[DateRangeSliderScales] ... first, next, stop, label, format
    object enabled extends Field[Boolean](true)
    object arrows extends Field[Boolean](true)
    // object value_labels extends Field[] // Enum("show", "hide", "change")
    // object wheel_mode extends OptionalField[] // Enum("scroll", "zoom", default=None)
}

class DatePicker extends InputWidget[Date] {
    // object min_date extends OptionalField[Date]
    // object max_date extends OptionalField[Date]
}

class TableWidget extends Widget

class TableColumn extends Widget {
    object `type` extends Field[ColumnType]
    object data extends Field[String]
    object header extends Field[String]

    object source extends Field[List[String]]    // only 'autocomplete'
    object strict extends Field[Boolean](true)   // only 'autocomplete'
}

class HandsonTable extends TableWidget {
    object source extends Field[DataSource]
    object columns extends Field[List[TableColumn]]
}

class ObjectExplorer extends Widget {
    object data_widget extends Field[TableWidget]
}

class DataTable extends Widget {
    object source extends Field[DataSource]
    object sort extends Field[List[String]]
    object group extends Field[List[String]]
    object offset extends Field[Int](0)
    object length extends Field[Int](100)
    object maxlength extends Field[Int]
    object totallength extends Field[Int]
    object tabledata extends Field[Map[String, Any]]
    object filterselected extends Field[Boolean]
}

class PivotTable extends Widget {
    object source extends Field[DataSource]
    object title extends Field[String]("Pivot Table")
    object description extends Field[String]
    object data extends Field[Map[String, Any]]
    object fields extends Field[List[Any]]
    object rows extends Field[List[Any]]
    object columns extends Field[List[Any]]
    object values extends Field[List[Any]]
    object filters extends Field[List[Any]]
    object manual_update extends Field[Boolean](true)
}
