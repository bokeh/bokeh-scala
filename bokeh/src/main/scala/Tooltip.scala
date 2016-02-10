package io.continuum.bokeh

import scalatags.Text.short.Tag

sealed trait Tooltip
case class StringTooltip(string: String) extends Tooltip
case class HTMLTooltip(html: Tag) extends Tooltip
case class TabularTooltip(rows: List[(String, String)]) extends Tooltip
object Tooltip {
    def apply(string: String) = StringTooltip(string)
    def apply(html: Tag) = HTMLTooltip(html)
    def apply(rows: (String, String)*) = TabularTooltip(rows.toList)
    def apply(rows: List[(String, String)]) = TabularTooltip(rows)
}
