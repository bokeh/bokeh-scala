package io.continuum.bokeh

case class Validator[T](fn: T => Boolean, message: String)

trait ValidableField { self: AbstractField =>
    def validators: List[Validator[ValueType]] = Nil

    def validate(value: ValueType): List[String] = {
        validators.filterNot(_.fn(value)).map(_.message)
    }

    def validates(value: ValueType) {
        validate(value) match {
            case error :: _ => throw new ValueError(error)
            case Nil =>
        }
    }
}
