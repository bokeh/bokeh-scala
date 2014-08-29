package io.continuum.bokeh

trait Toolset { toolset =>
    protected val tools: List[DefaultTool]
    def |(other: DefaultTool) = new Toolset { val tools = toolset.tools :+ other }
    def toList: List[Tool] = tools.map(_.tool)
}

sealed abstract class DefaultTool extends Toolset {
    protected val tools = this :: Nil
    def tool: Tool
}

trait Tools {
    case object Pan extends DefaultTool                { def tool = new PanTool()                }
    case object WheelZoom extends DefaultTool          { def tool = new WheelZoomTool()          }
    case object PreviewSave extends DefaultTool        { def tool = new PreviewSaveTool()        }
    case object Reset extends DefaultTool              { def tool = new ResetTool()              }
    case object Resize extends DefaultTool             { def tool = new ResizeTool()             }
    case object Click extends DefaultTool              { def tool = new ClickTool()              }
    case object Crosshair extends DefaultTool          { def tool = new CrosshairTool()          }
    case object BoxZoom extends DefaultTool            { def tool = new BoxZoomTool()            }
    case object BoxSelect extends DefaultTool          { def tool = new BoxSelectTool()          }
    case object Hover extends DefaultTool              { def tool = new HoverTool()              }
    case object ObjectExplorer extends DefaultTool     { def tool = new ObjectExplorerTool()     }
    case object DataRangeBoxSelect extends DefaultTool { def tool = new DataRangeBoxSelectTool() }

    implicit def ToolsetToList(tools: Toolset): List[Tool] = tools.toList
}

object Tools extends Tools
