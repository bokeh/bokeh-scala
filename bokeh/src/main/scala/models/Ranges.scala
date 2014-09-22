package io.continuum.bokeh

@model abstract class Range extends PlotObject

@model class Range1d extends Range {
    object start extends Field[Double]
    object end extends Field[Double]
}

@model abstract class DataRange extends Range {
    object sources extends Field[List[ColumnsRef]]
}

@model class DataRange1d extends DataRange {
    object rangepadding extends Field[Double](0.1)

    object start extends Field[Double]
    object end extends Field[Double]
}

@model class FactorRange extends Range {
    // XXX: technically List[Any] but only List[String] is really supported
    object factors extends Field[List[String]]
}
