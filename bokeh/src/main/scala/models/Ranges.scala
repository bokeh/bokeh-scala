package io.continuum.bokeh

@model abstract class Range extends Model {
    object callback extends Field[Callback]
}

@model class Range1d extends Range {
    object start extends Field[Double]
    object end extends Field[Double]

    // TODO: bounds = MinMaxBounds(accept_datetime=True, default=None)
}

@model abstract class DataRange extends Range {
    object names extends Field[List[String]]
    object renderers extends Field[List[Renderer]]
}

@model class DataRange1d extends DataRange {
    object range_padding extends Field[Double](0.1)

    object start extends Field[Double]
    object end extends Field[Double]

    // TODO: bounds = MinMaxBounds(accept_datetime=False, default=None)

    object flipped extends Field[Boolean](false)
    object follow extends Field[Option[StartEnd]]
    object follow_interval extends Field[Option[Double]]
    object default_span extends Field[Double](2.0)
}

@model class FactorRange extends Range {
    object offset extends Field[Double](0)
    object factors extends Field[List[String]] // TODO: also List[Int]

    // TODO: bounds = Either(Auto, List(String), List(Int), default=None)
}
