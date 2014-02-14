package org.continuumio.bokeh

sealed abstract class Range extends PlotObject

class Range1d extends Range {
    object start extends Field[Double]
    object end extends Field[Double]
}

class ColumnsRef extends PlotObject with NoRefs {
    object ref extends Field[DataSource]
    object columns extends Field[List[String]]
}

trait DataRange { self: Range => }

class DataRange1d extends Range with DataRange {
    object start extends Field[Double]
    object end extends Field[Double]

    object sources extends Field[List[ColumnsRef]]
    object rangepadding extends Field[Double](0.1)
}
