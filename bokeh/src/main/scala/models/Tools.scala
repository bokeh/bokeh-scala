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

@model class CrosshairTool extends Tool

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
    object action extends Field[Action]
    object always_active extends Field[Boolean](true)
}

sealed trait Tooltip
case class StringTooltip(string: String) extends Tooltip
case class HTMLTooltip(html: xml.NodeSeq) extends Tooltip
case class TabularTooltip(rows: List[(String, String)]) extends Tooltip
object Tooltip {
    def apply(string: String) = StringTooltip(string)
    def apply(html: xml.NodeSeq) = HTMLTooltip(html)
    def apply(rows: (String, String)*) = TabularTooltip(rows.toList)
    def apply(rows: List[(String, String)]) = TabularTooltip(rows)
}

@model class HoverTool extends TransientSelectTool {
    object tooltips extends Field[Tooltip]
    object always_active extends Field[Boolean](true)
    object snap_to_data extends Field[Boolean](true)
}
