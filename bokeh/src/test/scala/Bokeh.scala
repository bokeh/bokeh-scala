package org.continuumio.bokeh

import org.specs2.mutable._

class BokehSpec extends Specification {
    sequential

    "glyphs" should {
        "support AnnularWedge" in {
            val obj = new AnnularWedge()
            obj.fieldsWithValues.length shouldEqual 22
        }

        "support Annulus" in {
            val obj = new Annulus()
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Arc" in {
            val obj = new Arc()
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Bezier" in {
            val obj = new Bezier()
            obj.fieldsWithValues.length shouldEqual 21
        }

        "support Image" in {
            val obj = new Image()
            obj.fieldsWithValues.length shouldEqual 13
        }

        "support ImageURL" in {
            val obj = new ImageURL()
            obj.fieldsWithValues.length shouldEqual 14
        }

        "support ImageRGBA" in {
            val obj = new ImageRGBA()
            obj.fieldsWithValues.length shouldEqual 12
        }

        "support Line" in {
            val obj = new Line()
            obj.fieldsWithValues.length shouldEqual 15
        }

        "support MultiLine" in {
            val obj = new MultiLine()
            obj.fieldsWithValues.length shouldEqual 15
        }

        "support Oval" in {
            val obj = new Oval()
            obj.fieldsWithValues.length shouldEqual 20
        }

        "support Patch" in {
            val obj = new Patch()
            obj.fieldsWithValues.length shouldEqual 17
        }

        "support Patches" in {
            val obj = new Patches()
            obj.fieldsWithValues.length shouldEqual 17
        }

        "support Quad" in {
            val obj = new Quad()
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Quadratic" in {
            val obj = new Quadratic()
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Ray" in {
            val obj = new Ray()
            obj.fieldsWithValues.length shouldEqual 17
        }

        "support Rect" in {
            val obj = new Rect()
            obj.fieldsWithValues.length shouldEqual 21
        }

        "support Segment" in {
            val obj = new Segment()
            obj.fieldsWithValues.length shouldEqual 17
        }

        "support Text" in {
            val obj = new Text()
            obj.fieldsWithValues.length shouldEqual 17
        }

        "support Wedge" in {
            val obj = new Wedge()
            obj.fieldsWithValues.length shouldEqual 21
        }
    }

    "markers" should {
        "support Circle" in {
            val obj = new Circle()
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Square" in {
            val obj = new Square()
            obj.fieldsWithValues.length shouldEqual 19
        }

        "support Triangle" in {
            val obj = new Triangle()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Cross" in {
            val obj = new Cross()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Xmarker" in {
            val obj = new X()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Diamond" in {
            val obj = new Diamond()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support InvertedTriangle" in {
            val obj = new InvertedTriangle()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support SquareX" in {
            val obj = new SquareX()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support Asterisk" in {
            val obj = new Asterisk()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support DiamondCross" in {
            val obj = new DiamondCross()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support CircleCross" in {
            val obj = new CircleCross()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support HexStar" in {
            val obj = new HexStar()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support SquareCross" in {
            val obj = new SquareCross()
            obj.fieldsWithValues.length shouldEqual 18
        }

        "support CircleX" in {
            val obj = new CircleX()
            obj.fieldsWithValues.length shouldEqual 18
        }
    }

    "renderers" should {
        "support Glyph renderer" in {
            val obj = new Glyph().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 8
        }
    }

    "ranges" should {
        "support Range1d" in {
            val obj = new Range1d().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 3
        }

        "support ColumnsRef" in {
            val obj = new ColumnsRef()
            obj.fieldsWithValues.length shouldEqual 2
        }

        "support DataRange1d" in {
            val obj = new DataRange1d().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 5
        }
    }

    "sources" should {
        "support ColumnDataSource" in {
            val obj = new ColumnDataSource().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 4
        }
    }

    "guides" should {
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
    }

    "tools" should {
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
    }

    "plots" should {
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
    }

    "objects" should {
        "support PlotContext" in {
            val obj = new PlotContext().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 2
        }
    }
}
