package io.continuum.bokeh
package tests

import org.specs2.mutable._

class BokehSpec extends Specification {
    sequential

    "glyphs" should {
        "support AnnularWedge" in {
            val obj1 = new AnnularWedge()
            val obj2 = obj1
                .x('x)
                .y('y)
                .inner_radius('inner_radius)
                .outer_radius('outer_radius)
                .start_angle('start_angle)
                .end_angle('end_angle)
                .direction(Direction.Clock)

            obj1.fields.length shouldEqual 18
        }

        "support Annulus" in {
            val obj1 = new Annulus()
            val obj2 = obj1
                .x('x)
                .y('y)
                .inner_radius('inner_radius)
                .outer_radius('outer_radius)

            obj1.fields.length shouldEqual 15
        }

        "support Arc" in {
            val obj1 = new Arc()
            val obj2 = obj1
                .x('x)
                .y('y)
                .radius('radius)
                .start_angle('start_angle)
                .end_angle('end_angle)
                .direction(Direction.Clock)

            obj1.fields.length shouldEqual 15
        }

        "support Bezier" in {
            val obj1 = new Bezier()
            val obj2 = obj1
                .x0('x0)
                .y0('y0)
                .x1('x1)
                .y1('y1)
                .cx0('cx0)
                .cy0('cy0)
                .cx1('cx1)
                .cy1('cy1)

            obj1.fields.length shouldEqual 17
        }

        "support ImageRGBA" in {
            val obj1 = new ImageRGBA()
            val obj2 = obj1
                .image('image)
                .cols('cols)
                .rows('rows)
                .x('x)
                .y('y)
                .dw('dw)
                .dh('dh)
                .dilate(false)

            obj1.fields.length shouldEqual 10
        }

        "support Image" in {
            val obj1 = new Image()
            val obj2 = obj1
                .image('image)
                .cols('cols)
                .rows('rows)
                .x('x)
                .y('y)
                .dw('dw)
                .dh('dh)
                .dilate(false)
                .color_mapper(new LinearColorMapper().palette(Palette.Spectral11))

            obj1.fields.length shouldEqual 11
        }

        "support ImageURL" in {
            val obj1 = new ImageURL()
            val obj2 = obj1
                .url('url)
                .x('x)
                .y('y)
                .w('w)
                .h('h)
                .angle('angle)
                .dilate(false)
                .anchor(Anchor.TopRight)

            obj1.fields.length shouldEqual 11
        }

        "support Line" in {
            val obj1 = new Line()
            val obj2 = obj1
                .x('x)
                .y('y)

            obj1.fields.length shouldEqual 11
        }

        "support MultiLine" in {
            val obj1 = new MultiLine()
            val obj2 = obj1
                .xs('xs)
                .ys('ys)

            obj1.fields.length shouldEqual 11
        }

        "support Oval" in {
            val obj1 = new Oval()
            val obj2 = obj1
                .x('x)
                .y('y)
                .width('width)
                .height('height)
                .angle('angle)

            obj1.fields.length shouldEqual 16
        }

        "support Patch" in {
            val obj1 = new Patch()
            val obj2 = obj1
                .x('x)
                .y('y)

            obj1.fields.length shouldEqual 13
        }

        "support Patches" in {
            val obj1 = new Patches()
            val obj2 = obj1
                .xs('xs)
                .ys('ys)

            obj1.fields.length shouldEqual 13
        }

        "support Quad" in {
            val obj1 = new Quad()
            val obj2 = obj1
                .left('left)
                .right('right)
                .bottom('bottom)
                .top('top)

            obj1.fields.length shouldEqual 15
        }

        "support Quadratic" in {
            val obj1 = new Quadratic()
            val obj2 = obj1
                .x0('x0)
                .y0('y0)
                .x1('x1)
                .y1('y1)
                .cx('cx)
                .cy('cy)

            obj1.fields.length shouldEqual 15
        }

        "support Ray" in {
            val obj1 = new Ray()
            val obj2 = obj1
                .x('x)
                .y('y)
                .angle('angle)
                .length('length)

            obj1.fields.length shouldEqual 13
        }

        "support Rect" in {
            val obj1 = new Rect()
            val obj2 = obj1
                .x('x)
                .y('y)
                .width('width)
                .height('height)
                .angle('angle)
                .dilate(false)

            obj1.fields.length shouldEqual 17
        }

        "support Segment" in {
            val obj1 = new Segment()
            val obj2 = obj1
                .x0('x0)
                .y0('y0)
                .x1('x1)
                .y1('y1)

            obj1.fields.length shouldEqual 13
        }

        "support Text" in {
            val obj1 = new Text()
            val obj2 = obj1
                .x('x)
                .y('y)
                .text('text)
                .angle('angle)

            obj1.fields.length shouldEqual 15
        }

        "support Wedge" in {
            val obj1 = new Wedge()
            val obj2 = obj1
                .x('x)
                .y('y)
                .radius('radius)
                .start_angle('start_angle)
                .end_angle('end_angle)
                .direction(Direction.Clock)

            obj1.fields.length shouldEqual 17
        }

        "support Gear" in {
            val obj1 = new Gear()
            val obj2 = obj1
                .x('x)
                .y('y)
                .angle('angle)
                .module('module)
                .teeth('teeth)
                .pressure_angle('pressure_angle)
                .shaft_size('shaft_size)
                .internal('internal)

            obj1.fields.length shouldEqual 19
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
            val obj = new GlyphRenderer().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 8
        }

        "support Legend" in {
            val obj = new Legend().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 32
        }

        "support BoxSelectionOverlay" in {
            val obj = new BoxSelectionOverlay().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }
    }

    "ranges" should {
        "support Range1d" in {
            val obj = new Range1d().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support DataRange1d" in {
            val obj = new DataRange1d().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support FactorRange" in {
            val obj = new FactorRange().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }
    }

    "sources" should {
        "support ColumnDataSource" in {
            val obj = new ColumnDataSource().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support ColumnsRef" in {
            val obj = new ColumnsRef()
            obj.fields.length shouldEqual 2
        }
    }

    "guides" should {
        "support LinearAxis" in {
            val obj = new LinearAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 52
        }

        "support CategoricalAxis" in {
            val obj = new CategoricalAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 52
        }

        "support DatetimeAxis" in {
            val obj = new DatetimeAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 56
        }

        "support LogAxis" in {
            val obj = new LogAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 52
        }

        "support Grid" in {
            val obj = new Grid().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 23
        }
    }

    "tools" should {
        "support PanTool" in {
            val obj = new PanTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support WheelZoomTool" in {
            val obj = new WheelZoomTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support PreviewSaveTool" in {
            val obj = new PreviewSaveTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support ResetTool" in {
            val obj = new ResetTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support ResizeTool" in {
            val obj = new ResizeTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support TapTool" in {
            val obj = new TapTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 6
        }

        "support CrosshairTool" in {
            val obj = new CrosshairTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support BoxZoomTool" in {
            val obj = new BoxZoomTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support BoxSelectTool" in {
            val obj = new BoxSelectTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 6
        }

        "support LassoSelectTool" in {
            val obj = new LassoSelectTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support PolySelectTool" in {
            val obj = new PolySelectTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support HoverTool" in {
            val obj = new HoverTool().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 10
        }
    }

    "plots" should {
        "support Plot" in {
            val obj = new Plot().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 49
        }

        "support GMapOptions" in {
            val obj = new GMapOptions()
            obj.fields.length shouldEqual 5
        }

        "support GMapPlot" in {
            val obj = new GMapPlot().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 50
        }

        "support GeoJSOptions" in {
            val obj = new GeoJSOptions()
            obj.fields.length shouldEqual 3
        }

        "support GeoJSPlot" in {
            val obj = new GeoJSPlot().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 50
        }

        "support GridPlot" in {
            val obj = new GridPlot().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 51
        }
    }

    "objects" should {
        "support PlotContext" in {
            val obj = new PlotContext().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }
    }

    "mappers" should {
        "support LinearColorMapper" in {
            val obj = new LinearColorMapper().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 6
        }
    }

    "tickers" should {
        "support AdaptiveTicker" in {
            val obj = new AdaptiveTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support CompositeTicker" in {
            val obj = new CompositeTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support SingleIntervalTicker" in {
            val obj = new SingleIntervalTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support DaysTicker" in {
            val obj = new DaysTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support MonthsTicker" in {
            val obj = new MonthsTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support YearsTicker" in {
            val obj = new YearsTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support BasicTicker" in {
            val obj = new BasicTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support LogTicker" in {
            val obj = new LogTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support CategoricalTicker" in {
            val obj = new CategoricalTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support DatetimeTicker" in {
            val obj = new DatetimeTicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }
    }

    "tick formatters" should {
        "support BasicTickFormatter" in {
            val obj = new BasicTickFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support LogTickFormatter" in {
            val obj = new LogTickFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support CategoricalTickFormatter" in {
            val obj = new CategoricalTickFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support DatetimeTickFormatter" in {
            val obj = new DatetimeTickFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }
    }

    "widgets" should {
        "support Button" in {
            val obj = new widgets.Button().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support Toggle" in {
            val obj = new widgets.Toggle().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support Dropdown" in {
            val obj = new widgets.Dropdown().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 9
        }

        "support Dialog" in {
            val obj = new widgets.Dialog().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support CheckboxGroup" in {
            val obj = new widgets.CheckboxGroup().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support RadioGroup" in {
            val obj = new widgets.RadioGroup().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support CheckboxButtonGroup" in {
            val obj = new widgets.CheckboxButtonGroup().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support RadioButtonGroup" in {
            val obj = new widgets.RadioButtonGroup().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support Icon" in {
            val obj = new widgets.Icon().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 6
        }

        "support TextInput" in {
            val obj = new widgets.TextInput().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support Select" in {
            val obj = new widgets.Select().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 6
        }

        "support Slider" in {
            val obj = new widgets.Slider().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 9
        }

        "support DateRangeSlider" in {
            val obj = new widgets.DateRangeSlider().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 8
        }

        "support DatePicker" in {
            val obj = new widgets.DatePicker().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support HBox" in {
            val obj = new widgets.HBox().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support VBox" in {
            val obj = new widgets.VBox().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support Paragraph" in {
            val obj = new widgets.Paragraph().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support PreText" in {
            val obj = new widgets.PreText().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 3
        }

        "support Panel" in {
            val obj = new widgets.Panel().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 5
        }

        "support Tabs" in {
            val obj = new widgets.Tabs().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support StringFormatter" in {
            val obj = new widgets.StringFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 4
        }

        "support NumberFormatter" in {
            val obj = new widgets.NumberFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 7
        }

        "support BooleanFormatter" in {
            val obj = new widgets.BooleanFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support DateFormatter" in {
            val obj = new widgets.DateFormatter().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support StringEditor" in {
            val obj = new widgets.StringEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support TextEditor" in {
            val obj = new widgets.TextEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support SelectEditor" in {
            val obj = new widgets.SelectEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support PercentEditor" in {
            val obj = new widgets.PercentEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support CheckboxEditor" in {
            val obj = new widgets.CheckboxEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support IntEditor" in {
            val obj = new widgets.IntEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support NumberEditor" in {
            val obj = new widgets.NumberEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 2
        }

        "support TimeEditor" in {
            val obj = new widgets.TimeEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support DateEditor" in {
            val obj = new widgets.DateEditor().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 1
        }

        "support TableColumn" in {
            val obj = new widgets.TableColumn().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 8
        }

        "support DataTable" in {
            val obj = new widgets.DataTable().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fields.length shouldEqual 11
        }
    }
}
