package io.continuum.bokeh
package widgets

import org.joda.time.{LocalDate=>Date}
import play.api.libs.json.Writes

@model class AbstractIcon extends Widget

@model class Icon extends AbstractIcon {
    object name extends Field[NamedIcon]
    object size extends Field[Double] with NonNegative
    object flip extends Field[Flip]
    object spin extends Field[Boolean](false)
}

@model class AbstractButton extends Widget {
    object label extends Field[String]("Button")
    object icon extends Field[AbstractIcon]
    object `type` extends Field[ButtonType]
}

@model class Button extends AbstractButton {
    object clicks extends Field[Int](0)
}

@model class Toggle extends AbstractButton {
    object active extends Field[Boolean](false)
}

@model class Dropdown extends AbstractButton {
    object action extends Field[String]
    object default_action extends Field[String]
    object menu extends Field[List[(String, String)]]
}

@model class AbstractGroup extends Widget {
    object labels extends Field[List[String]]
}

@model class Group extends AbstractGroup {
    object inline extends Field[Boolean](false)
}

@model class ButtonGroup extends AbstractGroup {
    object `type` extends Field[ButtonType]
}

@model class CheckboxGroup extends Group {
    object active extends Field[List[Int]]
}

@model class RadioGroup extends Group {
    object active extends Field[Int]
}

@model class CheckboxButtonGroup extends ButtonGroup {
    object active extends Field[List[Int]]
}

@model class RadioButtonGroup extends ButtonGroup {
    object active extends Field[Int]
}

@model class Panel extends Widget {
    object title extends Field[String]
    object child extends Field[Widget]
    object closable extends Field[Boolean](false)
}

@model class Tabs extends Widget {
    object tabs extends Field[List[Panel]]
    object active extends Field[Int](0)
}

@model class Dialog extends Widget {
    object visible extends Field[Boolean](false)
    object closable extends Field[Boolean](true)
    object title extends Field[String]
    object content extends Field[String]
    object buttons extends Field[List[String]]
}

@model abstract class Layout extends Widget {
    object width extends Field[Int]
    object height extends Field[Int]
}

@model class HBox extends Layout {
    object children extends Field[List[Widget]]
}

@model class VBox extends Layout {
    object children extends Field[List[Widget]]
}

@model class Paragraph extends Widget {
    object text extends Field[String]
}

@model class PreText extends Paragraph

@model abstract class InputWidget[T:DefaultValue:Writes] extends Widget {
    object title extends Field[String]
    object name extends Field[String]
    object value extends Field[T]
}

@model class TextInput extends InputWidget[String]

@model class Select extends InputWidget[String] {
    object options extends Field[List[String]]
}

@model class Slider extends InputWidget[Double] {
    object start extends Field[Double]
    object end extends Field[Double]
    object step extends Field[Int]
    object orientation extends Field[Orientation]
}

@model class DateRangeSlider extends InputWidget[(Date, Date)] {
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

@model class DatePicker extends InputWidget[Date] {
    // TODO: object min_date extends OptionalField[Date]
    // TODO: object max_date extends OptionalField[Date]
}

@model class TableWidget extends Widget

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
    object source extends Field[DataSource]
    object columns extends Field[List[TableColumn]]
    object sorting extends Field[Boolean](true)
}

@model class ObjectExplorer extends Widget {
    object data_widget extends Field[TableWidget]
}
