package org.continuumio.bokeh

sealed abstract class Range extends PlotObject

class Range1d extends Range {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
}

trait DataRange { self: Range => }

class DataRange1d extends Range with DataRange {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
    //object sources extends Field[List[ColumnsRef]]
    object rangepadding extends Field[this.type, Double](this, 0.1)
}
