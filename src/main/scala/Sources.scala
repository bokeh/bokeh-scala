package org.continuumio.bokeh

import breeze.linalg.DenseVector

sealed abstract class DataSource extends PlotObject {
    object column_names extends Field[this.type, List[String]](this)
    object selected extends Field[this.type, List[String]](this)
}

class ColumnDataSource extends DataSource {
    type ArrayLike = DenseVector[Double]
    object data extends Field[this.type, Map[String, ArrayLike]](this)
}
