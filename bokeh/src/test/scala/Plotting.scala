package io.continuum.bokeh
package tests

import org.specs2.mutable._

class PlottingSpec extends Specification {
    "bokeh.plotting API" should {
        "support markers" in {
            val fig = new Figure()

            object source extends ColumnDataSource {
                val x = column(List(1.0, 2.0, 3.0))
                val y = column(List(2.0, 3.0, 4.0))
            }

            fig.circle(List(2.0, 3.0, 4.0)).size(5.0)
            fig.circle(1.0, 2.0).size(5.0)
            fig.circle(List(1.0, 2.0, 3.0), 2.0).size(5.0)
            fig.circle(1.0, List(2.0, 3.0, 4.0)).size(5.0)
            fig.circle(List(1.0, 2.0, 3.0), List(2.0, 3.0, 4.0)).size(5.0)
            fig.circle(List((1.0, 2.0), (2.0, 3.0), (3.0, 4.0))).size(5.0)
            fig.circle(source.x, source.y)
            fig.circle(source)(_.x, _.y)

            1 === 1 // TODO: write actuall tests
        }
    }
}
