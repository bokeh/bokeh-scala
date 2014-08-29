package io.continuum.bokeh

@fields sealed abstract class Tool extends PlotObject {
    object plot extends Field[Plot]
}

@fields class PanTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@fields class WheelZoomTool extends Tool {
    object dimensions extends Field[List[Dimension]](List(Dimension.Width, Dimension.Height))
}

@fields class PreviewSaveTool extends Tool

@fields class ResetTool extends Tool

@fields class ResizeTool extends Tool

@fields class ClickTool extends Tool {
    object names extends Field[List[String]]
    object always_active extends Field[Boolean](true)
}

@fields class CrosshairTool extends Tool

@fields class BoxZoomTool extends Tool

@fields class BoxSelectTool extends Tool {
    object renderers extends Field[List[Renderer]]
    object select_every_mousemove extends Field[Boolean](true)
    object select_x extends Field[Boolean](true)
    object select_y extends Field[Boolean](true)
}

@fields class HoverTool extends Tool {
    object renderers extends Field[List[Renderer]]
    object tooltips extends Field[Map[String, String]]
    object always_active extends Field[Boolean](true)
}

@fields class ObjectExplorerTool extends Tool

@fields class DataRangeBoxSelectTool extends Tool {
    object xselect extends Field[List[Range]]
    object yselect extends Field[List[Range]]
}
