package org.continuumio.bokeh

import java.io.File
import play.api.libs.json.Json

abstract class Session

class HTMLFileSession(file: File) {

    def this(path: String) = this(new File(path))

    val title = "Bokeh Plots"

    def save(plots: Plot*) {
        // Json.toJson(plots.toList)
    }

    def renderScripts(scripts: List[File]) = {
        scripts.map { script =>
            <script type="text/javascript" src={ script.getAbsolutePath }></script>
        }
    }

    def renderStyles(styles: List[File]) = {
        styles.map { style =>
            <link rel="stylesheet" href={ style.getAbsolutePath } type="text/css" />
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
        val doctype = xml.dtd.DocType("html", xml.dtd.SystemID("about:legacy-compat"), Nil)
        xml.XML.write(writer, html, "UTF-8", xmlDecl=false, doctype=doctype)
        writer.toString
    }

    case class PlotSpec(models: String, modelId: String, modelType: String, elementId: String)

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
                {renderStyles(Nil)}
                {renderScripts(Nil)}
            </head>
            <body>
                { renderPlots(specs) }
            </body>
        </html>
    }
}
