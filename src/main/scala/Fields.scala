package org.continuumio.bokeh

trait ToolsField extends AbstractField { self: Plot#Field[List[Tool]] =>
    abstract override def set(value: Option[List[Tool]]) {
        super.set(value.map(_.map(_.plot(owner))))
    }
}
