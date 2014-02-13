package org.continuumio.bokeh

sealed abstract class Range extends PlotObject

class Range1d extends Range {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)
}

class ColumnsRef extends PlotObject with NoRefs {
    object ref extends Field[this.type, DataSource](this)
    object columns extends Field[this.type, List[String]](this)
}

trait DataRange { self: Range => }

class DataRange1d extends Range with DataRange {
    object start extends Field[this.type, Double](this)
    object end extends Field[this.type, Double](this)

    object sources extends Field[this.type, List[ColumnsRef]](this)
    object rangepadding extends Field[this.type, Double](this, 0.1)
}
