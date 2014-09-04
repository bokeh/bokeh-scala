package io.continuum.bokeh
package widgets

import org.joda.time.{LocalDate=>Date}
import play.api.libs.json.Writes

@fields class Panel extends Widget {
    object title extends Field[String]
    object child extends Field[Widget]
    object closable extends Field[Boolean](false)
}

@fields class Tabs extends Widget {
    object tabs extends Field[List[Panel]]
    object active extends Field[Int](0)
}

@fields class Dialog extends Widget {
    object visible extends Field[Boolean](false)
    object closable extends Field[Boolean](true)
    object title extends Field[String]
    object content extends Field[String]
    object buttons extends Field[List[String]]
}

@fields abstract class Layout extends Widget {
    object width extends Field[Int]
    object height extends Field[Int]
}

@fields class HBox extends Layout {
    object children extends Field[List[Widget]]
}

@fields class VBox extends Layout {
    object children extends Field[List[Widget]]
}

@fields class Paragraph extends Widget {
    object text extends Field[String]
}

@fields class PreText extends Paragraph

@fields abstract class InputWidget[T:DefaultValue:Writes] extends Widget {
    object title extends Field[String]
    object name extends Field[String]
    object value extends Field[T]
}

@fields class TextInput extends InputWidget[String]

@fields class Select extends InputWidget[String] {
    object options extends Field[List[String]]
}

@fields class Slider extends InputWidget[Double] {
    object start extends Field[Double]
    object end extends Field[Double]
    object step extends Field[Int]
    object orientation extends Field[Orientation]
}

@fields class DateRangeSlider extends InputWidget[(Date, Date)] {
    object bounds extends Field[(Date, Date)]
    // TODO: object range extends Field[(RelativeDelta, RelativeDelta)]
    // TODO: object step extends Field[RelativeDelta
    // TODO: object formatter extends Field[Either[String, Function[Date]]]
    // TODO: object scales extends Field[DateRangeSliderScales] ... first, next, stop, label, format
    object enabled extends Field[Boolean](true)
    object arrows extends Field[Boolean](true)
    // TODO: object value_labels extends Field[] // Enum("show", "hide", "change")
    // TODO: object wheel_mode extends OptionalField[] // Enum("scroll", "zoom", default=None)
}

@fields class DatePicker extends InputWidget[Date] {
    // TODO: object min_date extends OptionalField[Date]
    // TODO: object max_date extends OptionalField[Date]
}

@fields class TableWidget extends Widget

@fields class TableColumn extends Widget {
    object field extends Field[String]
    object header extends Field[String]
    object `type` extends Field[ColumnType]
    object format extends Field[String]
    object source extends Field[List[String]]
    object strict extends Field[Boolean](false)
    object checked extends Field[String]("true")
    object unchecked extends Field[String]("false")
}

@fields class HandsonTable extends TableWidget {
    object source extends Field[DataSource]
    object columns extends Field[List[TableColumn]]
    object sorting extends Field[Boolean](true)
}

@fields class ObjectExplorer extends Widget {
    object data_widget extends Field[TableWidget]
}
