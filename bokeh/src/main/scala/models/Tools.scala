package io.continuum.bokeh

import play.api.libs.json.JsObject

@model class ToolEvents extends Model {
    object geometries extends Field[List[JsObject]]
}

@model sealed abstract class Tool extends Model {
    object plot extends Field[Plot]
}

@model class PanTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@model class WheelZoomTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@model class PreviewSaveTool extends Tool

@model class UndoTool extends Tool

@model class RedoTool extends Tool

@model class ResetTool extends Tool

@model class ResizeTool extends Tool

trait InspectTool { self: Tool =>
    object active extends Field[Boolean](true)
}

@model class CrosshairTool extends Tool with InspectTool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))

    object line_color extends Field[Color](Color.Black)
    object line_width extends Field[Double](1)
    object line_alpha extends Field[Double](1.0)
}

@model class BoxZoomTool extends Tool

@model abstract class TransientSelectTool extends Tool {
    object names extends Field[List[String]]
    object renderers extends Field[List[Renderer]]
}

@model abstract class SelectTool extends TransientSelectTool

@model class BoxSelectTool extends SelectTool {
    object select_every_mousemove extends Field[Boolean](true)
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@model class LassoSelectTool extends SelectTool {
    object select_every_mousemove extends Field[Boolean](true)
}

@model class PolySelectTool extends SelectTool

@model class TapTool extends SelectTool {
    object callback extends Field[Callback]
}

@model class HoverTool extends TransientSelectTool with InspectTool {
    object tooltips extends Field[Tooltip]
    object callback extends Field[Callback]
    object mode extends Field[HoverMode]
    object point_policy extends Field[PointPolicy]
    object line_policy extends Field[LinePolicy]
}

@model class HelpTool extends Tool {
    object help_tooltip extends Field[String]
    object redirect extends Field[String]
}
