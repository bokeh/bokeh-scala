package org.continuumio.bokeh

import java.io.File
import java.net.URL
import java.awt.Desktop

import scala.sys.process.{Process,BasicIO}
import java.lang.StringBuffer

import scalax.io.JavaConverters._
import scalax.file.Path

object FileLocator {
    lazy val bokehPath: Option[Path] = {
        val cmd = "python" :: "-c" :: "import bokeh; print(bokeh.__file__)" :: Nil
        val out = new StringBuffer
        val proc = Process(cmd).run(BasicIO(false, out, None))
        if (proc.exitValue == 0)
            Path.fromString(out.toString.trim).parent
        else
            None
    }
}

class FileLocator(minified: Boolean) {
    val cdnUrl = new URL("http://cdn.pydata.org")

    val cdnVersion = Some("0.4")
    val localVersion = None

    def template(name: String, version: Option[String], minified: Boolean, extension: String) = {
        val ver = version.map("-" + _) getOrElse ""
        val min = if (minified) ".min" else ""
        s"$name$ver$min.$extension"
    }

    def file(extension: String, minified: Boolean) = {
        val (context, version) = FileLocator.bokehPath
            .map(_ / "server" / "static" / extension)
            .map(_.toURL)
            .map(_ -> localVersion)
            .getOrElse(cdnUrl -> cdnVersion)
        new URL(context, template("bokeh", version, minified, extension))
    }

    val scripts: List[URL] = file("js",  minified) :: Nil
    val styles: List[URL]  = file("css", minified) :: Nil
}

abstract class Session extends Serializer {
    def save(plots: Plot*)
    def view()
}

class HTMLFileSession(val file: File) extends Session {

    def this(path: String) = this(new File(path))

    def url: String = {
        val uri = file.toURI
        s"${uri.getScheme}://${uri.getSchemeSpecificPart}"
    }

    val title = "Bokeh Plots"

    def save(plots: Plot*) {
        val context = new PlotContext().children(plots.toList)
        val models = serializeObjs(collectObjs(context))
        val spec = PlotSpec(models, context.getRef, uuid4())
        val html = stringify(renderHTML(spec :: Nil))
        Path(file).write(html)
    }

    def view() {
        if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop.browse(file.toURI)
    }

    protected lazy val files = new FileLocator(minified=true)

    protected def renderScripts(scripts: List[URL] = files.scripts) = {
        scripts.map { script =>
            <script type="text/javascript" src={ script.toString }></script>
        }
    }

    protected def renderStyles(styles: List[URL] = files.styles) = {
        styles.map { style =>
            <link rel="stylesheet" type="text/css" href={ style.toString }/>
        }
    }

    def asScript(script: String): xml.Node =
<script type="text/javascript">{xml.Unparsed(s"""
// <![CDATA[
$script
// ]]>
""")}</script>

    def stringify(html: xml.Node) = {
        val writer = new java.io.StringWriter()
        val doctype = xml.dtd.DocType("html", xml.dtd.SystemID(""), Nil)
        xml.XML.write(writer, html, "UTF-8", xmlDecl=false, doctype=doctype)
        writer.toString
    }

    case class PlotSpec(models: String, modelRef: Ref, elementId: String) {
        def modelId = modelRef.id
        def modelType = modelRef.`type`
    }

    def renderPlot(spec: PlotSpec) = {
        s"""
        (function() {
            var models = ${spec.models};
            var modelid = "${spec.modelId}";
            var modeltype = "${spec.modelType}";
            var elementid = "#${spec.elementId}";
            console.log(modelid, modeltype, elementid);
            Bokeh.load_models(models);
            var model = Bokeh.Collections(modeltype).get(modelid);
            var view = new model.default_view({model: model, el: elementid});
        })();
        """
    }

    def renderPlots(specs: List[PlotSpec]) = {
        specs.flatMap { spec =>
            <div>
                <div class="plotdiv" id={ spec.elementId }>Plots</div>
                { asScript(renderPlot(spec)) }
            </div>
        }
    }

    def renderHTML(specs: List[PlotSpec]): xml.Node = {
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                <title>{ title }</title>
                { renderStyles() }
                { renderScripts() }
            </head>
            <body>
                { renderPlots(specs) }
            </body>
        </html>
    }
}
