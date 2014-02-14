package org.continuumio.bokeh

import org.specs2.mutable._

class BokehSpec extends Specification {
    sequential

    "Bokeh" should {
        "support AnnularWedge glyph" in {
            val obj = new AnnularWedge().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Annulus glyph" in {
            val obj = new Annulus().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Arc glyph" in {
            val obj = new Arc().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Bezier glyph" in {
            val obj = new Bezier().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Image glyph" in {
            val obj = new Image().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support ImageURI glyph" in {
            val obj = new ImageURI().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support ImageRGBA glyph" in {
            val obj = new ImageRGBA().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Line glyph" in {
            val obj = new Line().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support MultiLine glyph" in {
            val obj = new MultiLine().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Oval glyph" in {
            val obj = new Oval().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Patch glyph" in {
            val obj = new Patch().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Patches glyph" in {
            val obj = new Patches().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Quad glyph" in {
            val obj = new Quad().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Quadratic glyph" in {
            val obj = new Quadratic().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Ray glyph" in {
            val obj = new Ray().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Rect glyph" in {
            val obj = new Rect().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Segment glyph" in {
            val obj = new Segment().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Text glyph" in {
            val obj = new Text().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Wedge glyph" in {
            val obj = new Wedge().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Circle marker" in {
            val obj = new Circle().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Square marker" in {
            val obj = new Square().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Triangle marker" in {
            val obj = new Triangle().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Cross marker" in {
            val obj = new Cross().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Xmarker marker" in {
            val obj = new Xmarker().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Diamond marker" in {
            val obj = new Diamond().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support InvertedTriangle marker" in {
            val obj = new InvertedTriangle().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support SquareX marker" in {
            val obj = new SquareX().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Asterisk marker" in {
            val obj = new Asterisk().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support DiamondCross marker" in {
            val obj = new DiamondCross().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support CircleCross marker" in {
            val obj = new CircleCross().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support HexStar marker" in {
            val obj = new HexStar().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support SquareCross marker" in {
            val obj = new SquareCross().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support CircleX marker" in {
            val obj = new CircleX().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support Glyph renderer" in {
            val obj = new Glyph().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support Range1d" in {
            val obj = new Range1d().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support ColumnsRef" in {
            val obj = new ColumnsRef().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support DataRange1d" in {
            val obj = new DataRange1d().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support ColumnDataSource" in {
            val obj = new ColumnDataSource().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support LinearAxis" in {
            val obj = new LinearAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support CategoricalAxis" in {
            val obj = new CategoricalAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support DatetimeAxis" in {
            val obj = new DatetimeAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support Grid" in {
            val obj = new Grid().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support PanTool" in {
            val obj = new PanTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support WheelZoomTool" in {
            val obj = new WheelZoomTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support PreviewSaveTool" in {
            val obj = new PreviewSaveTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support EmbedTool" in {
            val obj = new EmbedTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support ResetTool" in {
            val obj = new ResetTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support ResizeTool" in {
            val obj = new ResizeTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support CrosshairTool" in {
            val obj = new CrosshairTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support BoxZoomTool" in {
            val obj = new BoxZoomTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support BoxSelectTool" in {
            val obj = new BoxSelectTool().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support Plot" in {
            val obj = new Plot().id("xyz")
            obj.id.value shouldEqual "xyz"
        }

        "support GridPlot" in {
            val obj = new GridPlot().id("xyz")
            obj.id.value shouldEqual "xyz"
        }


        "support PlotContext" in {
            val obj = new PlotContext().id("xyz")
            obj.id.value shouldEqual "xyz"
        }
    }
}
