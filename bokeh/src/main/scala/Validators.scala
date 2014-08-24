package io.continuum.bokeh

trait NonNegative extends ValidableField { self: HasFields#Field[Double] =>
    abstract override def validators = {
        Validator[Double](_ >= 0, "value must be non-negative") :: super.validators
    }
}
