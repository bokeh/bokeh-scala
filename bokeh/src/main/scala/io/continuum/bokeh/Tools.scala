package io.continuum.bokeh

@model class ToolEvents extends PlotObject {
    object geometries extends Field[List[Map[Symbol, Any]]]
}

@model sealed abstract class Tool extends PlotObject {
    object plot extends Field[Plot]
}

@model class PanTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@model class WheelZoomTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@model class PreviewSaveTool extends Tool

@model class ResetTool extends Tool

@model class ResizeTool extends Tool

@model class CrosshairTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
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
    object always_active extends Field[Boolean](true)
}

@model class HoverTool extends TransientSelectTool {
    object tooltips extends Field[Tooltip]
    object callback extends Field[Callback]
    object always_active extends Field[Boolean](true)
    object mode extends Field[HoverMode]
    object point_policy extends Field[PointPolicy]
    object line_policy extends Field[LinePolicy]
}

@model class HelpTool extends Tool {
    object help_tooltip extends Field[String]
    object redirect extends Field[String]
}
