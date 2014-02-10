package org.continuumio.bokeh

import java.io.File

import scalax.io.JavaConverters._
import scalax.file.Path

import play.api.libs.json.{Json,JsValue,JsArray,JsObject,JsNull}

abstract class Session

class HTMLFileSession(file: File) {

    def this(path: String) = this(new File(path))

    val title = "Bokeh Plots"

    val userHome = Path.fromString(System.getProperty("user.home"))
    val sitePath = userHome / ".local" / "lib" / "python2.7" / "site-packages"
    val basePath = sitePath  / "bokeh" / "server" / "static"

    val jsFiles: List[String] = List(basePath / "js" / "bokeh.min.js").map(_.path)
    val cssFiles: List[String] = List(basePath / "css" / "bokeh.min.css")map(_.path)

    def save(plots: Plot*) {
        val context = new PlotContext().children(plots.toList)
        val objs = collectObjs(context)
        val models = objs.map(getModel)
        val array = JsArray(models.map(_.toJson))
        val json = Json.stringify(array)
        val ref = context.getRef
        val spec = PlotSpec(json, ref.id, ref.`type`, uuid4())
        val html = stringify(renderHTML(spec :: Nil))
        Path(file).write(html)
    }

    case class Model(id: String, `type`: String, attributes: Map[String, Any], doc: Option[String]) {
        def toJson: JsValue =
            Json.obj("id" -> Json.toJson(id),
                     "type" -> Json.toJson(`type`),
                     "attributes" -> anyToJson(attributes),
                     "doc" -> Json.toJson(doc))
    }

    def anyToJson(obj: Any): JsValue = {
        import Json.toJson
        import Writers._
        obj match {
            case obj: Boolean => toJson(obj)
            case obj: Int => toJson(obj)
            case obj: Double => toJson(obj)
            case obj: String => toJson(obj)
            case obj: Enum => obj match {
                case obj: LineJoin => toJson(obj)
                case obj: LineDash => toJson(obj)
                case obj: LineCap => toJson(obj)
                case obj: FontStyle => toJson(obj)
                case obj: TextAlign => toJson(obj)
                case obj: Baseline => toJson(obj)
                case obj: Direction => toJson(obj)
                case obj: Orientation => toJson(obj)
                case obj: Units => toJson(obj)
                case obj: AngleUnits => toJson(obj)
                case obj: Dimension => toJson(obj)
                case obj: Location => toJson(obj)
                case obj: Color => toJson(obj)
            }
            case obj: Percent => toJson(obj)
            case obj: breeze.linalg.DenseVector[Double] => toJson(obj)
            case obj: List[_] => JsArray(obj.map(anyToJson))
            case obj: Map[String, _] => JsObject(obj.mapValues(anyToJson).toList)
            case Ref(id, tp) => toJson(Map("id" -> id, "type" -> tp))
            case Some(obj) => anyToJson(obj)
            case None => JsNull
            case obj => throw new IllegalArgumentException(obj.toString)
        }
    }

    def collectObjs(plot: PlotObject): List[PlotObject] = {
        val ids = collection.mutable.HashSet[String]()
        val objs = collection.mutable.ListBuffer[PlotObject]()

        def descend(obj: Any) {
            obj match {
                case obj: PlotObject if !ids.contains(obj.id.value) =>
                    ids += obj.id.value
                    obj.fieldsWithValues.foreach {
                        case (_, Some(obj)) => descend(obj)
                        case _ =>
                    }
                    obj match {
                        case _: NoRefs =>
                        case _ => objs += obj
                    }
                case obj: List[_] => obj.map(descend)
                case _ =>
            }
        }

        descend(plot)
        objs.toList
    }

    def getModel(obj: PlotObject): Model = {
        val Ref(id, tp) = obj.getRef
        Model(id, tp, replaceWithRefs(obj.fieldsWithValues), None)
    }

    def replaceWithRefs(fields: List[(String, Any)]): Map[String, Any] = {
        fields.map {
            case (name, Some(value)) => (name, _replaceWithRefs(value))
            case field => field
        } toMap
    }

    def _replaceWithRefs(obj: Any): Any = obj match {
        case obj: PlotObject with NoRefs => replaceWithRefs(obj.fieldsWithValues)
        case obj: PlotObject => obj.getRef
        case obj: List[_] => obj.map(_replaceWithRefs)
        case obj => obj
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
                {renderStyles()}
                {renderScripts()}
            </head>
            <body>
                { renderPlots(specs) }
            </body>
        </html>
    }
}
