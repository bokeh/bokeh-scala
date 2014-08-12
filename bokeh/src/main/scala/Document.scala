package io.continuum.bokeh

import java.io.File
import java.awt.Desktop

import scalax.io.JavaConverters._
import scalax.file.Path

import scala.collection.mutable.ListBuffer

class Document(objs: Widget*) {
    private val objects = ListBuffer[Widget](objs: _*)

    def add(objs: Widget*) {
        objects ++= objs
    }

    def save(file: File): HTMLFile = {
        val contexts = objects.toList.map(obj => new PlotContext().children(obj :: Nil))
        new HTMLFileWriter(contexts).write(file)
    }

    def save(path: String): HTMLFile = save(new File(path))
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

class HTMLFileWriter(contexts: List[PlotContext]) extends Serializer {

    val resources = Resources.AbsoluteDev

    def write(file: File): HTMLFile = {
        val html = stringify(renderHTML(specs()))
        Path(file).write(html)
        new HTMLFile(file)
    }

    case class PlotSpec(models: String, modelRef: Ref, elementId: String) {
        def modelId = modelRef.id
        def modelType = modelRef.`type`
    }

    def specs(): List[PlotSpec] = {
        contexts.map { context =>
            val models = serializeObjs(collectObjs(context))
            PlotSpec(models, context.getRef, Utils.uuid4())
        }
    }

    def stringify(html: xml.Node) = {
        val writer = new java.io.StringWriter()
        val doctype = "<!DOCTYPE html>"
        xml.XML.write(writer, html, "UTF-8", xmlDecl=false, doctype=null)
        s"$doctype\n${writer.toString}"
    }

    def renderHTML(specs: List[PlotSpec]): xml.Node = {
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                { resources.styles }
                { resources.scripts }
            </head>
            <body>
                { renderPlots(specs) }
            </body>
        </html>
    }

    def renderPlots(specs: List[PlotSpec]) = {
        specs.flatMap { spec =>
            <div>
                <div class="plotdiv" id={ spec.elementId }>Plots</div>
                { renderPlot(spec) }
            </div>
        }
    }

    def renderPlot(spec: PlotSpec): xml.Node = {
        val code = s"""
            |var models = ${spec.models};
            |var modelid = "${spec.modelId}";
            |var modeltype = "${spec.modelType}";
            |var elementid = "#${spec.elementId}";
            |console.log(modelid, modeltype, elementid);
            |Bokeh.load_models(models);
            |var model = Bokeh.Collections(modeltype).get(modelid);
            |var view = new model.default_view({model: model, el: elementid});
            """
        resources.wrap(code.stripMargin.trim).asScript
    }
}
