package org.continuumio.bokeh

import breeze.linalg.DenseVector

sealed abstract class DataSource extends PlotObject {
    object column_names extends Field[List[String]]
    object selected extends Field[List[String]]

    def columns(columns: String*): ColumnsRef =
        new ColumnsRef().ref(this).columns(columns.toList)
}

class ColumnDataSource extends DataSource {
    type ArrayLike = DenseVector[Double]
    object data extends Field[Map[String, ArrayLike]]
}
