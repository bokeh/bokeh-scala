package io.continuum.bokeh
package examples

import org.specs2.mutable._
import org.specs2.matcher.TerminationMatchers

class ExamplesSpec extends Specification with TerminationMatchers {
    sequential

    val argv = Array[String]("--quiet", "--dev")

    "examples.glyphs" should {
        "run Anscombe" in {
            glyphs.Anscombe.main(argv) must not(throwA[Throwable])
        }

        "run Calendars" in {
            glyphs.Calendars.main(argv) must not(throwA[Throwable])
        }

        "run Choropleth" in {
            glyphs.Choropleth.main(argv) must not(throwA[Throwable])
        }

        "run ColorSpec" in {
            glyphs.ColorSpec.main(argv) must not(throwA[Throwable])
        }

        "run Colors" in {
            glyphs.Colors.main(argv) must not(throwA[Throwable])
        }

        "run DataTables" in {
            glyphs.DataTables.main(argv) must not(throwA[Throwable])
        }

        "run DateAxis" in {
            glyphs.DateAxis.main(argv) must not(throwA[Throwable])
        }

        "run Daylight" in {
            glyphs.Daylight.main(argv) must not(throwA[Throwable])
        }

        "run Donut" in {
            glyphs.Donut.main(argv) must not(throwA[Throwable])
        }

        "run Gauges" in {
            glyphs.Gauges.main(argv) must not(throwA[Throwable])
        }

        "run Gears" in {
            glyphs.Gears.main(argv) must not(throwA[Throwable])
        }

        "run Glyph1" in {
            glyphs.Glyph1.main(argv) must not(throwA[Throwable])
        }

        "run Glyph2" in {
            glyphs.Glyph2.main(argv) must not(throwA[Throwable])
        }

        "run Grid" in {
            glyphs.Grid.main(argv) must not(throwA[Throwable])
        }

        "run Hover" in {
            glyphs.Hover.main(argv) must not(throwA[Throwable])
        }

        "run Image" in {
            glyphs.Image.main(argv) must not(throwA[Throwable])
        }

        "run Iris" in {
            glyphs.Iris.main(argv) must not(throwA[Throwable])
        }

        "run IrisSplom" in {
            glyphs.IrisSplom.main(argv) must not(throwA[Throwable])
        }

        "run Line" in {
            glyphs.Line.main(argv) must not(throwA[Throwable])
        }

        "run Maps" in {
            glyphs.Maps.main(argv) must not(throwA[Throwable])
        }

        "run Prim" in {
            glyphs.Prim.main(argv) must not(throwA[Throwable])
        }

        "run Sprint" in {
            glyphs.Sprint.main(argv) must not(throwA[Throwable])
        }

        "run TwinAxis" in {
            glyphs.TwinAxis.main(argv) must not(throwA[Throwable])
        }
    }
}
