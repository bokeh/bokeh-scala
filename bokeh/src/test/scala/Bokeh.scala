package io.continuum.bokeh

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

            obj1.fieldsWithValues.length shouldEqual 22
        }

        "support Annulus" in {
            val obj1 = new Annulus()
            val obj2 = obj1
                .x('x)
                .y('y)
                .inner_radius('inner_radius)
                .outer_radius('outer_radius)

            obj1.fieldsWithValues.length shouldEqual 19
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

            obj1.fieldsWithValues.length shouldEqual 19
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

            obj1.fieldsWithValues.length shouldEqual 21
        }

        "support Image" in {
            val obj1 = new Image()
            val obj2 = obj1
                .image('image)
                .x('x)
                .y('y)
                .dw('dw)
                .dh('dh)
                .palette('palette)
                .dilate(false)

            obj1.fieldsWithValues.length shouldEqual 13
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

            obj1.fieldsWithValues.length shouldEqual 14
        }

        "support ImageRGBA" in {
            val obj1 = new ImageRGBA()
            val obj2 = obj1
                .image('image)
                .x('x)
                .y('y)
                .dw('dw)
                .dh('dh)
                .dilate(false)

            obj1.fieldsWithValues.length shouldEqual 12
        }

        "support Line" in {
            val obj1 = new Line()
            val obj2 = obj1
                .x('x)
                .y('y)

            obj1.fieldsWithValues.length shouldEqual 15
        }

        "support MultiLine" in {
            val obj1 = new MultiLine()
            val obj2 = obj1
                .xs('xs)
                .ys('ys)

            obj1.fieldsWithValues.length shouldEqual 15
        }

        "support Oval" in {
            val obj1 = new Oval()
            val obj2 = obj1
                .x('x)
                .y('y)
                .width('width)
                .height('height)
                .angle('angle)

            obj1.fieldsWithValues.length shouldEqual 20
        }

        "support Patch" in {
            val obj1 = new Patch()
            val obj2 = obj1
                .x('x)
                .y('y)

            obj1.fieldsWithValues.length shouldEqual 17
        }

        "support Patches" in {
            val obj1 = new Patches()
            val obj2 = obj1
                .xs('xs)
                .ys('ys)

            obj1.fieldsWithValues.length shouldEqual 17
        }

        "support Quad" in {
            val obj1 = new Quad()
            val obj2 = obj1
                .left('left)
                .right('right)
                .bottom('bottom)
                .top('top)

            obj1.fieldsWithValues.length shouldEqual 19
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

            obj1.fieldsWithValues.length shouldEqual 19
        }

        "support Ray" in {
            val obj1 = new Ray()
            val obj2 = obj1
                .x('x)
                .y('y)
                .angle('angle)
                .length('length)

            obj1.fieldsWithValues.length shouldEqual 17
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

            obj1.fieldsWithValues.length shouldEqual 21
        }

        "support Segment" in {
            val obj1 = new Segment()
            val obj2 = obj1
                .x0('x0)
                .y0('y0)
                .x1('x1)
                .y1('y1)

            obj1.fieldsWithValues.length shouldEqual 17
        }

        "support Text" in {
            val obj1 = new Text()
            val obj2 = obj1
                .x('x)
                .y('y)
                .text('text)
                .angle('angle)

            obj1.fieldsWithValues.length shouldEqual 17
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

            obj1.fieldsWithValues.length shouldEqual 21
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

            obj1.fieldsWithValues.length shouldEqual 23
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
            obj.fieldsWithValues.length shouldEqual 13
        }

        "support CategoricalAxis" in {
            val obj = new CategoricalAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 13
        }

        "support DatetimeAxis" in {
            val obj = new DatetimeAxis().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 17
        }

        "support Grid" in {
            val obj = new Grid().id("xyz")
            obj.id.value shouldEqual "xyz"
            obj.fieldsWithValues.length shouldEqual 5
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
