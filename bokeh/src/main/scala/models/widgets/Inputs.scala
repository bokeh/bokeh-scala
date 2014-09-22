package io.continuum.bokeh
package widgets

import org.joda.time.{LocalDate=>Date}
import play.api.libs.json.Writes

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
