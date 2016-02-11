package io.continuum.bokeh
package examples

import scala.util.{Try,Success,Failure}

import org.specs2.mutable._
import org.specs2.matcher.{Matcher,Expectable}

class ExamplesSpec extends Specification with RunMatchers {
    sequential

    val run = new Run("--quiet", "--dev")

    "examples.models" should {
        "run Anscombe"   in { models.Anscombe   must run }
        "run Calendars"  in { models.Calendars  must run }
        "run Choropleth" in { models.Choropleth must run }
        "run ColorSpec"  in { models.ColorSpec  must run }
        "run Colors"     in { models.Colors     must run }
        "run DataTables" in { models.DataTables must run }
        "run DateAxis"   in { models.DateAxis   must run }
        "run Daylight"   in { models.Daylight   must run }
        "run Donut"      in { models.Donut      must run }
        "run Gauges"     in { models.Gauges     must run }
        "run Gears"      in { models.Gears      must run }
        "run Glyph1"     in { models.Glyph1     must run }
        "run Glyph2"     in { models.Glyph2     must run }
        "run Grid"       in { models.Grid       must run }
        "run Hover"      in { models.Hover      must run }
        "run Image"      in { models.Image      must run }
        "run ImageURL"   in { models.ImageURL   must run }
        "run Iris"       in { models.Iris       must run }
        "run IrisSplom"  in { models.IrisSplom  must run }
        "run Line"       in { models.Line       must run }
        "run Maps"       in { models.Maps       must run }
        "run MapsCities" in { models.MapsCities must run }
        "run Prim"       in { models.Prim       must run }
        "run Sprint"     in { models.Sprint     must run }
        "run Trail"      in { models.Trail      must run }
        "run TwinAxis"   in { models.TwinAxis   must run }
    }
}

trait RunMatchers {
    class Run(argv: String*) extends Matcher[App] {
        def apply[T <: App](app: Expectable[T]) = {
            val ret = Try(app.value.main(argv.toArray))
            val msg = ret match {
                case Success(_)   => ""
                case Failure(exc) => exc.toString
            }
            result(ret.isSuccess,
                s"${app.description} did run",
                s"${app.description} failed to run and failed with $msg",
                app)
        }
    }
}
