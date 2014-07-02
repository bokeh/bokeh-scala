package io.continuum.bokeh

case class DefaultTools(tools: List[DefaultTool]) {
    def |(other: DefaultTool) = new DefaultTools(tools :+ other)
    def toTools: List[Tool] = tools.map(_.toTool)
}

sealed abstract class DefaultTool {
    def |(other: DefaultTool) = new DefaultTools(List(this, other))
    def toTool: Tool
}

trait DefaultToolsImpl {
    case object Pan extends DefaultTool                { def toTool = new PanTool()                }
    case object WheelZoom extends DefaultTool          { def toTool = new WheelZoomTool()          }
    case object PreviewSave extends DefaultTool        { def toTool = new PreviewSaveTool()        }
    case object Embed extends DefaultTool              { def toTool = new EmbedTool()              }
    case object Reset extends DefaultTool              { def toTool = new ResetTool()              }
    case object Resize extends DefaultTool             { def toTool = new ResizeTool()             }
    case object Crosshair extends DefaultTool          { def toTool = new CrosshairTool()          }
    case object BoxZoom extends DefaultTool            { def toTool = new BoxZoomTool()            }
    case object BoxSelect extends DefaultTool          { def toTool = new BoxSelectTool()          }
    case object Hover extends DefaultTool              { def toTool = new HoverTool()              }
    case object ObjectExplorer extends DefaultTool     { def toTool = new ObjectExplorerTool()     }
    case object DataRangeBoxSelect extends DefaultTool { def toTool = new DataRangeBoxSelectTool() }
}

object DefaultTools extends DefaultToolsImpl {
    implicit def DefaultToolsToListTool(tools: DefaultTools): List[Tool] = tools.toTools
}
