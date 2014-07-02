package org.continuumio.bokeh

trait ToolsField extends AbstractField { self: Plot#Field[List[Tool]] =>
    abstract override def set(value: Option[List[Tool]]) {
        super.set(value.map(_.map(_.plot(owner))))
    }
}

trait ReactiveField[A] extends AbstractField { self: HasFields#Field[A] =>
    abstract override def set(value: Option[A]) {
        super.set(value)
        value.map(value => reactors.foreach(_(value)))
    }

    val reactors: List[A => Unit]
}
