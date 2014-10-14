package io.continuum.bokeh

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

@model class TapTool extends Tool {
    object names extends Field[List[String]]
    object always_active extends Field[Boolean](true)
}

@model class CrosshairTool extends Tool

@model class BoxZoomTool extends Tool

@model class BoxSelectTool extends Tool {
    object renderers extends Field[List[Renderer]]
    object select_every_mousemove extends Field[Boolean](true)
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@model class HoverTool extends Tool {
    object names extends Field[List[String]]
    object renderers extends Field[List[Renderer]]
    object tooltips extends Field[Map[String, String]]
    object always_active extends Field[Boolean](true)
}

@model class ObjectExplorerTool extends Tool

@model class DataRangeBoxSelectTool extends Tool {
    object xselect extends Field[List[Range]]
    object yselect extends Field[List[Range]]
}
