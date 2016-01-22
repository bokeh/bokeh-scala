package io.continuum.bokeh

@model class Grid extends GuideRenderer {
    object dimension extends Field[Int](0)
    object ticker extends Field[Ticker]

    def axis(axis: Axis): SelfType = {
        axis.ticker.valueOpt.foreach(this.ticker := _)
        this
    }

    grid = include[LineProps]
    minor_grid = include[LineProps]
    band = include[FillProps]
}
