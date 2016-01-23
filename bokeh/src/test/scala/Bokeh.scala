package io.continuum.bokeh
package tests

import org.specs2.mutable._

class BokehSpec extends Specification {
    sequential

    "glyphs" should {
        "support AnnularWedge" in {
            val obj = new AnnularWedge()
            obj.fields.length shouldEqual 18
        }

        "support Annulus" in {
            val obj = new Annulus()
            obj.fields.length shouldEqual 15
        }

        "support Arc" in {
            val obj = new Arc()
            obj.fields.length shouldEqual 15
        }

        "support Bezier" in {
            val obj = new Bezier()
            obj.fields.length shouldEqual 17
        }

        "support ImageRGBA" in {
            val obj = new ImageRGBA()
            obj.fields.length shouldEqual 10
        }

        "support Image" in {
            val obj = new Image()
            obj.fields.length shouldEqual 11
        }

        "support ImageURL" in {
            val obj = new ImageURL()
            obj.fields.length shouldEqual 13
        }

        "support Line" in {
            val obj = new Line()
            obj.fields.length shouldEqual 11
        }

        "support MultiLine" in {
            val obj = new MultiLine()
            obj.fields.length shouldEqual 11
        }

        "support Oval" in {
            val obj = new Oval()
            obj.fields.length shouldEqual 16
        }

        "support Patch" in {
            val obj = new Patch()
            obj.fields.length shouldEqual 13
        }

        "support Patches" in {
            val obj = new Patches()
            obj.fields.length shouldEqual 13
        }

        "support Quad" in {
            val obj = new Quad()
            obj.fields.length shouldEqual 15
        }

        "support Quadratic" in {
            val obj = new Quadratic()
            obj.fields.length shouldEqual 15
        }

        "support Ray" in {
            val obj = new Ray()
            obj.fields.length shouldEqual 13
        }

        "support Rect" in {
            val obj = new Rect()
            obj.fields.length shouldEqual 17
        }

        "support Segment" in {
            val obj = new Segment()
            obj.fields.length shouldEqual 13
        }

        "support Text" in {
            val obj = new Text()
            obj.fields.length shouldEqual 15
        }

        "support Wedge" in {
            val obj = new Wedge()
            obj.fields.length shouldEqual 17
        }

        "support Gear" in {
            val obj = new Gear()
            obj.fields.length shouldEqual 19
        }
    }

    "markers" should {
        "support Circle" in {
            val obj = new Circle()
            obj.fields.length shouldEqual 17
        }

        "support Square" in {
            val obj = new Square()
            obj.fields.length shouldEqual 15
        }

        "support Triangle" in {
            val obj = new Triangle()
            obj.fields.length shouldEqual 15
        }

        "support Cross" in {
            val obj = new Cross()
            obj.fields.length shouldEqual 15
        }

        "support PlainX" in {
            val obj = new PlainX()
            obj.fields.length shouldEqual 15
        }

        "support Diamond" in {
            val obj = new Diamond()
            obj.fields.length shouldEqual 15
        }

        "support InvertedTriangle" in {
            val obj = new InvertedTriangle()
            obj.fields.length shouldEqual 15
        }

        "support SquareX" in {
            val obj = new SquareX()
            obj.fields.length shouldEqual 15
        }

        "support Asterisk" in {
            val obj = new Asterisk()
            obj.fields.length shouldEqual 15
        }

        "support DiamondCross" in {
            val obj = new DiamondCross()
            obj.fields.length shouldEqual 15
        }

        "support CircleCross" in {
            val obj = new CircleCross()
            obj.fields.length shouldEqual 15
        }

        "support SquareCross" in {
            val obj = new SquareCross()
            obj.fields.length shouldEqual 15
        }

        "support CircleX" in {
            val obj = new CircleX()
            obj.fields.length shouldEqual 15
        }
    }

    "renderers" should {
        "support GlyphRenderer" in {
            val obj = new GlyphRenderer()
            obj.fields.length shouldEqual 9
        }

        "support Legend" in {
            val obj = new Legend()
            obj.fields.length shouldEqual 33
        }
    }

    "ranges" should {
        "support Range1d" in {
            val obj = new Range1d()
            obj.fields.length shouldEqual 4
        }

        "support DataRange1d" in {
            val obj = new DataRange1d()
            obj.fields.length shouldEqual 7
        }

        "support FactorRange" in {
            val obj = new FactorRange()
            obj.fields.length shouldEqual 4
        }
    }

    "sources" should {
        "support ColumnDataSource" in {
            val obj = new ColumnDataSource()
            obj.fields.length shouldEqual 5
        }

        "support ColumnsRef" in {
            val obj = new ColumnsRef()
            obj.fields.length shouldEqual 2
        }
    }

    "guides" should {
        "support LinearAxis" in {
            val obj = new LinearAxis()
            obj.fields.length shouldEqual 52
        }

        "support CategoricalAxis" in {
            val obj = new CategoricalAxis()
            obj.fields.length shouldEqual 52
        }

        "support DatetimeAxis" in {
            val obj = new DatetimeAxis()
            obj.fields.length shouldEqual 56
        }

        "support LogAxis" in {
            val obj = new LogAxis()
            obj.fields.length shouldEqual 52
        }

        "support Grid" in {
            val obj = new Grid()
            obj.fields.length shouldEqual 23
        }
    }

    "tools" should {
        "support PanTool" in {
            val obj = new PanTool()
            obj.fields.length shouldEqual 3
        }

        "support WheelZoomTool" in {
            val obj = new WheelZoomTool()
            obj.fields.length shouldEqual 3
        }

        "support PreviewSaveTool" in {
            val obj = new PreviewSaveTool()
            obj.fields.length shouldEqual 2
        }

        "support ResetTool" in {
            val obj = new ResetTool()
            obj.fields.length shouldEqual 2
        }

        "support ResizeTool" in {
            val obj = new ResizeTool()
            obj.fields.length shouldEqual 2
        }

        "support TapTool" in {
            val obj = new TapTool()
            obj.fields.length shouldEqual 6
        }

        "support CrosshairTool" in {
            val obj = new CrosshairTool()
            obj.fields.length shouldEqual 6
        }

        "support BoxZoomTool" in {
            val obj = new BoxZoomTool()
            obj.fields.length shouldEqual 2
        }

        "support BoxSelectTool" in {
            val obj = new BoxSelectTool()
            obj.fields.length shouldEqual 6
        }

        "support LassoSelectTool" in {
            val obj = new LassoSelectTool()
            obj.fields.length shouldEqual 5
        }

        "support PolySelectTool" in {
            val obj = new PolySelectTool()
            obj.fields.length shouldEqual 4
        }

        "support HoverTool" in {
            val obj = new HoverTool()
            obj.fields.length shouldEqual 10
        }
    }

    "plots" should {
        "support Plot" in {
            val obj = new Plot()
            obj.fields.length shouldEqual 49
        }

        "support GMapOptions" in {
            val obj = new GMapOptions()
            obj.fields.length shouldEqual 5
        }

        "support GMapPlot" in {
            val obj = new GMapPlot()
            obj.fields.length shouldEqual 50
        }

        "support GridPlot" in {
            val obj = new GridPlot()
            obj.fields.length shouldEqual 51
        }
    }

    "mappers" should {
        "support LinearColorMapper" in {
            val obj = new LinearColorMapper()
            obj.fields.length shouldEqual 6
        }
    }

    "tickers" should {
        "support AdaptiveTicker" in {
            val obj = new AdaptiveTicker()
            obj.fields.length shouldEqual 7
        }

        "support CompositeTicker" in {
            val obj = new CompositeTicker()
            obj.fields.length shouldEqual 4
        }

        "support SingleIntervalTicker" in {
            val obj = new SingleIntervalTicker()
            obj.fields.length shouldEqual 4
        }

        "support DaysTicker" in {
            val obj = new DaysTicker()
            obj.fields.length shouldEqual 5
        }

        "support MonthsTicker" in {
            val obj = new MonthsTicker()
            obj.fields.length shouldEqual 5
        }

        "support YearsTicker" in {
            val obj = new YearsTicker()
            obj.fields.length shouldEqual 4
        }

        "support BasicTicker" in {
            val obj = new BasicTicker()
            obj.fields.length shouldEqual 3
        }

        "support LogTicker" in {
            val obj = new LogTicker()
            obj.fields.length shouldEqual 7
        }

        "support CategoricalTicker" in {
            val obj = new CategoricalTicker()
            obj.fields.length shouldEqual 3
        }

        "support DatetimeTicker" in {
            val obj = new DatetimeTicker()
            obj.fields.length shouldEqual 3
        }
    }

    "tick formatters" should {
        "support BasicTickFormatter" in {
            val obj = new BasicTickFormatter()
            obj.fields.length shouldEqual 4
        }

        "support LogTickFormatter" in {
            val obj = new LogTickFormatter()
            obj.fields.length shouldEqual 1
        }

        "support CategoricalTickFormatter" in {
            val obj = new CategoricalTickFormatter()
            obj.fields.length shouldEqual 1
        }

        "support DatetimeTickFormatter" in {
            val obj = new DatetimeTickFormatter()
            obj.fields.length shouldEqual 2
        }
    }

    "widgets" should {
        "support Button" in {
            val obj = new widgets.Button()
            obj.fields.length shouldEqual 7
        }

        "support Toggle" in {
            val obj = new widgets.Toggle()
            obj.fields.length shouldEqual 7
        }

        "support Dropdown" in {
            val obj = new widgets.Dropdown()
            obj.fields.length shouldEqual 9
        }

        "support Dialog" in {
            val obj = new widgets.Dialog()
            obj.fields.length shouldEqual 7
        }

        "support CheckboxGroup" in {
            val obj = new widgets.CheckboxGroup()
            obj.fields.length shouldEqual 5
        }

        "support RadioGroup" in {
            val obj = new widgets.RadioGroup()
            obj.fields.length shouldEqual 5
        }

        "support CheckboxButtonGroup" in {
            val obj = new widgets.CheckboxButtonGroup()
            obj.fields.length shouldEqual 5
        }

        "support RadioButtonGroup" in {
            val obj = new widgets.RadioButtonGroup()
            obj.fields.length shouldEqual 5
        }

        "support Icon" in {
            val obj = new widgets.Icon()
            obj.fields.length shouldEqual 6
        }

        "support TextInput" in {
            val obj = new widgets.TextInput()
            obj.fields.length shouldEqual 5
        }

        "support Select" in {
            val obj = new widgets.Select()
            obj.fields.length shouldEqual 6
        }

        "support Slider" in {
            val obj = new widgets.Slider()
            obj.fields.length shouldEqual 9
        }

        "support DateRangeSlider" in {
            val obj = new widgets.DateRangeSlider()
            obj.fields.length shouldEqual 8
        }

        "support DatePicker" in {
            val obj = new widgets.DatePicker()
            obj.fields.length shouldEqual 5
        }

        "support HBox" in {
            val obj = new widgets.HBox()
            obj.fields.length shouldEqual 5
        }

        "support VBox" in {
            val obj = new widgets.VBox()
            obj.fields.length shouldEqual 5
        }

        "support Paragraph" in {
            val obj = new widgets.Paragraph()
            obj.fields.length shouldEqual 3
        }

        "support PreText" in {
            val obj = new widgets.PreText()
            obj.fields.length shouldEqual 3
        }

        "support Panel" in {
            val obj = new widgets.Panel()
            obj.fields.length shouldEqual 5
        }

        "support Tabs" in {
            val obj = new widgets.Tabs()
            obj.fields.length shouldEqual 4
        }

        "support StringFormatter" in {
            val obj = new widgets.StringFormatter()
            obj.fields.length shouldEqual 4
        }

        "support NumberFormatter" in {
            val obj = new widgets.NumberFormatter()
            obj.fields.length shouldEqual 7
        }

        "support BooleanFormatter" in {
            val obj = new widgets.BooleanFormatter()
            obj.fields.length shouldEqual 2
        }

        "support DateFormatter" in {
            val obj = new widgets.DateFormatter()
            obj.fields.length shouldEqual 2
        }

        "support StringEditor" in {
            val obj = new widgets.StringEditor()
            obj.fields.length shouldEqual 2
        }

        "support TextEditor" in {
            val obj = new widgets.TextEditor()
            obj.fields.length shouldEqual 1
        }

        "support SelectEditor" in {
            val obj = new widgets.SelectEditor()
            obj.fields.length shouldEqual 2
        }

        "support PercentEditor" in {
            val obj = new widgets.PercentEditor()
            obj.fields.length shouldEqual 1
        }

        "support CheckboxEditor" in {
            val obj = new widgets.CheckboxEditor()
            obj.fields.length shouldEqual 1
        }

        "support IntEditor" in {
            val obj = new widgets.IntEditor()
            obj.fields.length shouldEqual 2
        }

        "support NumberEditor" in {
            val obj = new widgets.NumberEditor()
            obj.fields.length shouldEqual 2
        }

        "support TimeEditor" in {
            val obj = new widgets.TimeEditor()
            obj.fields.length shouldEqual 1
        }

        "support DateEditor" in {
            val obj = new widgets.DateEditor()
            obj.fields.length shouldEqual 1
        }

        "support TableColumn" in {
            val obj = new widgets.TableColumn()
            obj.fields.length shouldEqual 8
        }

        "support DataTable" in {
            val obj = new widgets.DataTable()
            obj.fields.length shouldEqual 12
        }
    }
}
