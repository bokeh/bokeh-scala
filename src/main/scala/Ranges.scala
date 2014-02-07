package org.continuumio.bokeh

abstract class PlotRange extends PlotObject

class Range1d extends PlotRange {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
}

abstract class DataRange extends PlotRange

class DataRange1d extends DataRange {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
    //object sources extends Field[List[ColumnsRef]]
    object rangepadding extends Field[this.type, Double](this, 0.1)
}
