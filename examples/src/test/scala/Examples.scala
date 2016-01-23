package io.continuum.bokeh
package examples

import org.specs2.mutable._
import org.specs2.matcher.TerminationMatchers

class ExamplesSpec extends Specification with TerminationMatchers {
    sequential

    val argv = Array[String]("--quiet", "--dev")

    "examples.models" should {
        "run Anscombe" in {
            models.Anscombe.main(argv) must not(throwA[Throwable])
        }

        "run Calendars" in {
            models.Calendars.main(argv) must not(throwA[Throwable])
        }

        "run Choropleth" in {
            models.Choropleth.main(argv) must not(throwA[Throwable])
        }

        "run ColorSpec" in {
            models.ColorSpec.main(argv) must not(throwA[Throwable])
        }

        "run Colors" in {
            models.Colors.main(argv) must not(throwA[Throwable])
        }

        "run DataTables" in {
            models.DataTables.main(argv) must not(throwA[Throwable])
        }

        "run DateAxis" in {
            models.DateAxis.main(argv) must not(throwA[Throwable])
        }

        "run Daylight" in {
            models.Daylight.main(argv) must not(throwA[Throwable])
        }

        "run Donut" in {
            models.Donut.main(argv) must not(throwA[Throwable])
        }

        "run Gauges" in {
            models.Gauges.main(argv) must not(throwA[Throwable])
        }

        "run Gears" in {
            models.Gears.main(argv) must not(throwA[Throwable])
        }

        "run Glyph1" in {
            models.Glyph1.main(argv) must not(throwA[Throwable])
        }

        "run Glyph2" in {
            models.Glyph2.main(argv) must not(throwA[Throwable])
        }

        "run Grid" in {
            models.Grid.main(argv) must not(throwA[Throwable])
        }

        "run Hover" in {
            models.Hover.main(argv) must not(throwA[Throwable])
        }

        "run Image" in {
            models.Image.main(argv) must not(throwA[Throwable])
        }

        "run ImageURL" in {
            models.ImageURL.main(argv) must not(throwA[Throwable])
        }

        "run Iris" in {
            models.Iris.main(argv) must not(throwA[Throwable])
        }

        "run IrisSplom" in {
            models.IrisSplom.main(argv) must not(throwA[Throwable])
        }

        "run Line" in {
            models.Line.main(argv) must not(throwA[Throwable])
        }

        "run Maps" in {
            models.Maps.main(argv) must not(throwA[Throwable])
        }

        "run Prim" in {
            models.Prim.main(argv) must not(throwA[Throwable])
        }

        "run Sprint" in {
            models.Sprint.main(argv) must not(throwA[Throwable])
        }

        "run Trail" in {
            models.Trail.main(argv) must not(throwA[Throwable])
        }

        "run TwinAxis" in {
            models.TwinAxis.main(argv) must not(throwA[Throwable])
        }
    }
}
