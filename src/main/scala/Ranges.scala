package org.continuumio.bokeh

abstract class Range extends PlotObject

class Range1d extends Range {
    object start extends Field[Double]
    object end extends Field[Double]
}

abstract class DataRange extends Range

class ColumnsRef extends PlotObject with NoRefs {
    object ref extends Field[DataSource]
    object columns extends Field[List[Symbol]]
}

class DataRange1d extends DataRange {
    object start extends Field[Double]
    object end extends Field[Double]

    object sources extends Field[List[ColumnsRef]]
    object rangepadding extends Field[Double](0.1)
}
