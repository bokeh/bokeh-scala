package io.continuum.bokeh

import java.io.File
import java.awt.Desktop

import scalax.io.JavaConverters._
import scalax.file.Path

import scala.collection.mutable.ListBuffer
import scala.xml.{Node,NodeSeq,XML}

class Document(objs: Widget*) {
    private val objects = ListBuffer[Widget](objs: _*)

    def add(objs: Widget*) {
        objects ++= objs
    }

    def fragment(resources: Resources): HTMLFragment = HTMLFragmentWriter(objects.toList, resources).write()
    def fragment(): HTMLFragment = fragment(Resources.default)

    def save(file: File, resources: Resources): HTMLFile = HTMLFileWriter(objects.toList, resources).write(file)
    def save(file: File): HTMLFile = save(file, Resources.default)

    def save(path: String, resources: Resources): HTMLFile = save(new File(path), resources)
    def save(path: String): HTMLFile = save(new File(path))
}

class HTMLFragment(val html: NodeSeq, val styles: NodeSeq, val scripts: NodeSeq) {
    def head: NodeSeq = styles ++ scripts
    def logo: NodeSeq = {
        <div>
            <a href="http://bokeh.pydata.org" target="_blank" class="bk-logo bk-logo-small bk-logo-notebook"></a>
            <span>BokehJS successfully loaded.</span>
        </div>
    }
    def preamble: NodeSeq = head ++ logo
}

object HTMLFragmentWriter {
    def apply(obj: Widget): HTMLFragmentWriter = apply(obj, Resources.default)

    def apply(obj: Widget, resources: Resources): HTMLFragmentWriter = apply(obj :: Nil, resources)

    def apply(objs: List[Widget]): HTMLFragmentWriter = apply(objs, Resources.default)

    def apply(objs: List[Widget], resources: Resources): HTMLFragmentWriter = {
        val contexts = objs.map(obj => new PlotContext().children(obj :: Nil))
        new HTMLFragmentWriter(contexts, resources)
    }
}

class HTMLFragmentWriter(contexts: List[PlotContext], resources: Resources) {
    protected val serializer = new JSONSerializer(resources.stringify _)

    def write(): HTMLFragment = {
        new HTMLFragment(renderPlots(plotSpecs), resources.styles, resources.scripts)
    }

    protected case class PlotSpec(models: String, modelRef: Ref, elementId: String) {
        def modelId = modelRef.id
        def modelType = modelRef.`type`
    }

    protected def plotSpecs: List[PlotSpec] = {
        contexts.map { context =>
            val models = serializer.stringify(context)
            PlotSpec(models, context.getRef, IdGenerator.next())
        }
    }

    protected def renderPlots(specs: List[PlotSpec]): NodeSeq = {
        specs.flatMap { spec =>
            <div>
                <div class="plotdiv" id={ spec.elementId }></div>
                { renderPlot(spec) }
            </div>
        }
    }

    protected def renderPlot(spec: PlotSpec): xml.Node = {
        val code = s"""
            |Bokeh.set_log_level("${resources.logLevel.name}");
            |var models = ${spec.models};
            |var modelid = "${spec.modelId}";
            |var modeltype = "${spec.modelType}";
            |var elementid = "#${spec.elementId}";
            |Bokeh.logger.info("Realizing plot:")
            |Bokeh.logger.info(" - modeltype: " + modeltype);
            |Bokeh.logger.info(" - modelid:   " + modelid);
            |Bokeh.logger.info(" - elementid: " + elementid);
            |Bokeh.load_models(models);
            |var model = Bokeh.Collections(modeltype).get(modelid);
            |var view = new model.default_view({model: model, el: elementid});
            |Bokeh.index[modelid] = view;
            """
        resources.wrap(code.stripMargin.trim).asScript
    }
}

class HTMLFile(val file: File) {
    def url: String = {
        val uri = file.toURI
        s"${uri.getScheme}://${uri.getSchemeSpecificPart}"
    }

    def view() {
        if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop.browse(file.toURI)
    }
}

object HTMLFileWriter {
    def apply(obj: Widget): HTMLFileWriter = apply(obj, Resources.default)

    def apply(obj: Widget, resources: Resources): HTMLFileWriter = apply(obj :: Nil, resources)

    def apply(objs: List[Widget]): HTMLFileWriter = apply(objs, Resources.default)

    def apply(objs: List[Widget], resources: Resources): HTMLFileWriter = {
        val contexts = objs.map(obj => new PlotContext().children(obj :: Nil))
        new HTMLFileWriter(contexts, resources)
    }
}

class HTMLFileWriter(contexts: List[PlotContext], resources: Resources) extends HTMLFragmentWriter(contexts, resources) {
    def write(file: File): HTMLFile = {
        val html = stringify(renderFile(write()))
        Path(file).write(html)
        new HTMLFile(file)
    }

    protected def stringify(html: Node) = {
        val writer = new java.io.StringWriter()
        val doctype = "<!DOCTYPE html>"
        XML.write(writer, html, "UTF-8", xmlDecl=false, doctype=null)
        s"$doctype\n${writer.toString}"
    }

    protected def renderTitle: Option[Node] = {
        contexts.flatMap(_.children.value)
                .collectFirst { case plot: Plot => plot.title.value }
                .map { title => <title>{title}</title> }
    }

    protected def renderFile(fragment: HTMLFragment): Node = {
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                { renderTitle orNull }
                { fragment.styles }
                { fragment.scripts }
            </head>
            <body>
                { fragment.html }
            </body>
        </html>
    }
}
