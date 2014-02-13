package org.continuumio.bokeh

sealed abstract class Tool extends PlotObject {
    object plot extends Field[this.type, Plot](this)
}

class PanTool extends Tool {
    object dimensions extends Field[this.type, List[Dimension]](this)
}

class WheelZoomTool extends Tool {
    object dimensions extends Field[this.type, List[Dimension]](this)
}

class PreviewSaveTool extends Tool {
    object dimensions extends Field[this.type, List[Dimension]](this)
    object dataranges extends Field[this.type, List[Range with DataRange]](this)
}

class EmbedTool extends Tool {
    object dimensions extends Field[this.type, List[Dimension]](this)
    object dataranges extends Field[this.type, List[Range with DataRange]](this)
}

class ResetTool extends Tool

class ResizeTool extends Tool

class CrosshairTool extends Tool

class BoxZoomTool extends Tool {
    object renderers extends Field[this.type, List[Renderer]](this)
    object select_every_mousemove extends Field[this.type, Boolean](this, true)
}

class BoxSelectTool extends Tool {
    object renderers extends Field[this.type, List[Renderer]](this)
    object select_every_mousemove extends Field[this.type, Boolean](this, true)
}
