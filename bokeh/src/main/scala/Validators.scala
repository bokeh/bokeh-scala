package io.continuum.bokeh

trait NonNegative extends AbstractField { self: HasFields#Field[Double] =>
    abstract override def validators = {
        Validator[Double](_ >= 0, "value must be non-negative") :: super.validators
    }
}
