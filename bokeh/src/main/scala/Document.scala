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

    def fragment(resources: Resources): HTMLFragment = HTMLFragmentWriter(objs.toList, resources).write()
    def fragment(): HTMLFragment = fragment(Resources.default)

    def save(file: File, resources: Resources): HTMLFile = HTMLFileWriter(objs.toList, resources).write(file)
    def save(file: File): HTMLFile = save(file, Resources.default)

    def save(path: String, resources: Resources): HTMLFile = save(new File(path), resources)
    def save(path: String): HTMLFile = save(new File(path))
}

class HTMLFragment(val html: NodeSeq, val styles: NodeSeq, val scripts: NodeSeq) {
    def head: NodeSeq = styles ++ scripts
}

object HTMLFragmentWriter {
    def apply(objs: List[Widget], resources: Resources = Resources.default): HTMLFragmentWriter = {
        val contexts = objs.map(obj => new PlotContext().children(obj :: Nil))
        new HTMLFragmentWriter(contexts, resources)
    }
}

class HTMLFragmentWriter(contexts: List[PlotContext], resources: Resources = Resources.default) {
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
                <div class="plotdiv" id={ spec.elementId }>Plots</div>
                { renderPlot(spec) }
            </div>
        }
    }

    protected def renderPlot(spec: PlotSpec): xml.Node = {
        val code = s"""
            |Bokeh.set_log_level("${resources.logLevel.name}")
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
    def apply(objs: List[Widget], resources: Resources = Resources.default): HTMLFileWriter = {
        val contexts = objs.map(obj => new PlotContext().children(obj :: Nil))
        new HTMLFileWriter(contexts, resources)
    }
}

class HTMLFileWriter(contexts: List[PlotContext], resources: Resources = Resources.default) extends HTMLFragmentWriter(contexts, resources) {
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

    protected def renderFile(fragment: HTMLFragment): Node = {
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                { fragment.styles }
                { fragment.scripts }
            </head>
            <body>
                { fragment.html }
            </body>
        </html>
    }
}
