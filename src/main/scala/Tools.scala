package org.continuumio.bokeh

sealed abstract class Tool extends PlotObject {
    object plot extends Field[Plot]
}

class PanTool extends Tool {
    object dimensions extends Field[List[Dimension]]
}

class WheelZoomTool extends Tool {
    object dimensions extends Field[List[Dimension]]
}

class PreviewSaveTool extends Tool {
    object dimensions extends Field[List[Dimension]]
    object dataranges extends Field[List[Range with DataRange]]
}

class EmbedTool extends Tool {
    object dimensions extends Field[List[Dimension]]
    object dataranges extends Field[List[Range with DataRange]]
}

class ResetTool extends Tool

class ResizeTool extends Tool

class CrosshairTool extends Tool

class BoxZoomTool extends Tool {
    object renderers extends Field[List[Renderer]]
    object select_every_mousemove extends Field[Boolean](true)
}

class BoxSelectTool extends Tool {
    object renderers extends Field[List[Renderer]]
    object select_every_mousemove extends Field[Boolean](true)
}

class HoverTool extends Tool {
    object renderers extends Field[List[Renderer]]
    object tooltips extends Field[Map[String, String]]
}
