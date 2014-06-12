package org.continuumio.bokeh

import org.specs2.mutable._

class BokehSpec extends Specification {
    sequential

    "Bokeh" should {
        "support AnnularWedge glyph" in {
            val obj = new AnnularWedge().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 23
        }

        "support Annulus glyph" in {
            val obj = new Annulus().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Arc glyph" in {
            val obj = new Arc().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Bezier glyph" in {
            val obj = new Bezier().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 22
        }

        "support Image glyph" in {
            val obj = new Image().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 14
        }

        "support ImageURL glyph" in {
            val obj = new ImageURL().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 15
        }

        "support ImageRGBA glyph" in {
            val obj = new ImageRGBA().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 13
        }

        "support Line glyph" in {
            val obj = new Line().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 16
        }

        "support MultiLine glyph" in {
            val obj = new MultiLine().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 16
        }

        "support Oval glyph" in {
            val obj = new Oval().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 21
        }

        "support Patch glyph" in {
            val obj = new Patch().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Patches glyph" in {
            val obj = new Patches().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Quad glyph" in {
            val obj = new Quad().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Quadratic glyph" in {
            val obj = new Quadratic().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Ray glyph" in {
            val obj = new Ray().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Rect glyph" in {
            val obj = new Rect().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 22
        }

        "support Segment glyph" in {
            val obj = new Segment().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Text glyph" in {
            val obj = new Text().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Wedge glyph" in {
            val obj = new Wedge().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 22
        }

        "support Circle marker" in {
            val obj = new Circle().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Square marker" in {
            val obj = new Square().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Triangle marker" in {
            val obj = new Triangle().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Cross marker" in {
            val obj = new Cross().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Xmarker marker" in {
            val obj = new X().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Diamond marker" in {
            val obj = new Diamond().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support InvertedTriangle marker" in {
            val obj = new InvertedTriangle().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support SquareX marker" in {
            val obj = new SquareX().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Asterisk marker" in {
            val obj = new Asterisk().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support DiamondCross marker" in {
            val obj = new DiamondCross().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support CircleCross marker" in {
            val obj = new CircleCross().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support HexStar marker" in {
            val obj = new HexStar().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support SquareCross marker" in {
            val obj = new SquareCross().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support CircleX marker" in {
            val obj = new CircleX().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 19
        }


        "support Glyph renderer" in {
            val obj = new Glyph().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 8
        }


        "support Range1d" in {
            val obj = new Range1d().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 3
        }

        "support ColumnsRef" in {
            val obj = new ColumnsRef().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 3
        }

        "support DataRange1d" in {
            val obj = new DataRange1d().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 5
        }


        "support ColumnDataSource" in {
            val obj = new ColumnDataSource().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 4
        }


        "support LinearAxis" in {
            val obj = new LinearAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 12
        }

        "support CategoricalAxis" in {
            val obj = new CategoricalAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 12
        }

        "support DatetimeAxis" in {
            val obj = new DatetimeAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 16
        }

        "support Grid" in {
            val obj = new Grid().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 4
        }


        "support PanTool" in {
            val obj = new PanTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 3
        }

        "support WheelZoomTool" in {
            val obj = new WheelZoomTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 3
        }

        "support PreviewSaveTool" in {
            val obj = new PreviewSaveTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }

        "support EmbedTool" in {
            val obj = new EmbedTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }

        "support ResetTool" in {
            val obj = new ResetTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }

        "support ResizeTool" in {
            val obj = new ResizeTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }

        "support CrosshairTool" in {
            val obj = new CrosshairTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }

        "support BoxZoomTool" in {
            val obj = new BoxZoomTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 4
        }

        "support BoxSelectTool" in {
            val obj = new BoxSelectTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 4
        }


        "support Plot" in {
            val obj = new Plot().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 22
        }

        "support GridPlot" in {
            val obj = new GridPlot().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 24
        }


        "support PlotContext" in {
            val obj = new PlotContext().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }
    }
}
