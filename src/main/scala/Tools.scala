package org.continuumio.bokeh

abstract class Tool extends PlotObject {
    //plot = Instance(Plot, has_ref=True)
}

class PanTool extends Tool {
    //dimensions = List(Enum(Dimension))
}

class WheelZoomTool extends Tool {
    //dimensions = List(Enum(Dimension))
}

class PreviewSaveTool extends Tool {
    //dimensions = List(Enum(Dimension))
    //dataranges = List(has_ref=True)
}

class EmbedTool extends Tool {
    //dimensions = List(Enum(Dimension))
    //dataranges = List(has_ref=True)
}

class ResetTool extends Tool

class ResizeTool extends Tool

class CrosshairTool extends Tool

class BoxZoomTool extends Tool {
    //renderers = List(has_ref=True)
    //select_every_mousemove = Bool(True)
}

class BoxSelectTool extends Tool {
    //renderers = List(has_ref=True)
    //select_every_mousemove = Bool(True)
}
