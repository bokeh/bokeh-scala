package org.continuumio.bokeh

import java.io.File
import java.awt.Desktop

import scala.sys.process.{Process,BasicIO}
import java.lang.StringBuffer

import scalax.io.JavaConverters._
import scalax.file.Path

object Session {
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

abstract class Session extends Serializer

class HTMLFileSession(val file: File) extends Session {

    def this(path: String) = this(new File(path))

    val title = "Bokeh Plots"

    def url: String = {
        val uri = file.toURI
        s"${uri.getScheme}://${uri.getSchemeSpecificPart}"
    }

    val staticPath = (_: Path) / "server" / "static"
    val basePath = Session.bokehPath.map(staticPath) getOrElse Path(".")

    val _jsFiles: List[Path => Path] = List(_ / "js" / "bokeh.js")
    val _cssFiles: List[Path => Path] = List(_ / "css" / "bokeh.css")

    def genPaths(files: List[Path => Path]) =
        files.map(_(basePath)).map(_.path)

    val jsFiles: List[String] = genPaths(_jsFiles)
    val cssFiles: List[String] = genPaths(_cssFiles)

    def view() {
        if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop.browse(file.toURI)
    }

    def save(plots: Plot*) {
        val context = new PlotContext().children(plots.toList)
        val models = serializeObjs(collectObjs(context))
        val spec = PlotSpec(models, context.getRef, uuid4())
        val html = stringify(renderHTML(spec :: Nil))
        Path(file).write(html)
    }

    def renderScripts(scripts: List[String] = jsFiles) = {
        scripts.map { script =>
            <script type="text/javascript" src={ script }></script>
        }
    }

    def renderStyles(styles: List[String] = cssFiles) = {
        styles.map { style =>
            <link rel="stylesheet" href={ style } type="text/css" />
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
            <div class="plotdiv" id={ spec.elementId }>Plots</div> ++ asScript(renderPlot(spec))
        }
    }

    def renderHTML(specs: List[PlotSpec]): xml.Node = {
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                <title>{ title }</title>
                {renderStyles()}
                {renderScripts()}
            </head>
            <body>
                { renderPlots(specs) }
            </body>
        </html>
    }
}
