package org.continuumio.bokeh

import breeze.linalg.DenseVector

abstract class DataSource extends PlotObject {
    object column_names extends Field[this.type, List[String]](this)
    object selected extends Field[this.type, List[String]](this)
}

class ColumnDataSource extends DataSource {
    object data extends Field[this.type, Map[String, DenseVector[Double]]](this)
    // object cont_ranges extends Field[Dict[]]
    // object discrete_ranges extends Field[Dict[]]
}
