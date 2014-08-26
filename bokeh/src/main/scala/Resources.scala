package io.continuum.bokeh

import java.io.File
import java.net.URL

import scalax.io.JavaConverters._
import scalax.file.Path

import play.api.libs.json.{Json,JsValue}

sealed trait Resources {
    def scripts: List[xml.Node]
    def styles: List[xml.Node]

    def wrap(code: String): String = {
        s"(function() {\n$code\n})();"
    }

    def stringify(value: JsValue): String = {
        Json.stringify(value)
    }

    protected def getResource(path: String): URL = {
        getClass.getClassLoader.getResource(path)
    }

    protected def loadResource(path: String): String = {
        getResource(path).asInput.chars.mkString
    }
}

object Resources {
    private val bokehjsVersion = "0.5.2"
    private def resource(ext: String, version: Boolean=false, minified: Boolean=false) =
        s"bokeh${if (version) "-" + bokehjsVersion else ""}${if (minified) ".min" else ""}.$ext"

    private val jsMin = resource("js", minified=true)
    private val jsUnMin = resource("js")

    private val cssMin = resource("css", minified=true)
    private val cssUnMin = resource("css")

    trait InlineResources extends Resources {
        def inlineJS(path: String): xml.Node = loadResource("js/" + path).asScript
        def inlineCSS(path: String): xml.Node = loadResource("css/" + path).asStyle
    }

    case object Inline extends InlineResources {
        def scripts = inlineJS(jsUnMin) :: Nil
        def styles = inlineCSS(cssUnMin) :: Nil
    }

    case object InlineMin extends InlineResources {
        def scripts = inlineJS(jsMin) :: Nil
        def styles = inlineCSS(cssMin) :: Nil
    }

    trait ExternalResources extends Resources {
        def includeJS(path: String): xml.Node
        def includeCSS(path: String): xml.Node
    }

    trait DevelopmentResources extends ExternalResources {
        def resolveFile(file: File): File

        def getFile(path: String): File = {
            val resource = getResource(path)
            resource.getProtocol match {
                case "file" => resolveFile(new File(resource.getPath))
                case protocol => sys.error(s"unable to load $path due to invalid protocol: $protocol")
            }
        }

        def baseJSDir: File = getFile("js")
        def baseCSSDir: File = getFile("css")

        def includeJS(path: String): xml.Node = new File(baseJSDir, path).asScript
        def includeCSS(path: String): xml.Node = new File(baseCSSDir, path).asStyle

        def requireConfig: xml.Node = {
            s"require.config({ baseUrl: 'file://$baseJSDir' });".asScript
        }

        def scripts =
            includeJS("vendor/requirejs/require.js") ::
            includeJS("config.js") ::
            requireConfig ::
            Nil

        def styles =
            includeCSS(cssUnMin) ::
            Nil

        override def wrap(code: String): String = {
            val wrapped = super.wrap(code)
            s"require(['jquery', 'main'], function($$, Bokeh) {\n$wrapped\n});"
        }

        override def stringify(value: JsValue): String = {
            Json.prettyPrint(value)
        }
    }

    /*
    case object RelativeDev extends DevelopmentResources {
        val rootDir = System.getProperty("user.dir")
        def resolveFile(file: File): File = file.relativize(rootDir)
    }
    */

    case object AbsoluteDev extends DevelopmentResources {
        def resolveFile(file: File): File = file
    }

    abstract class Remote(url: URL) extends ExternalResources {
        def includeJS(path: String): xml.Node = new URL(url, "/" + path).asScript
        def includeCSS(path: String): xml.Node = new URL(url, "/" + path).asStyle

        def scripts = includeJS(resource("js", true, true)) :: Nil
        def styles = includeCSS(resource("css", true, true)) :: Nil
    }

    case object CDN extends Remote(new URL("http://cdn.pydata.org"))

    private val fromStringPF: PartialFunction[String, Resources] = {
        case "cdn" => CDN
        case "inline" => Inline
        case "inline-min" => InlineMin
        // case "relative" => Relative
        // case "relative-min" => RelativeMin
        // case "relative-dev" => RelativeDev
        // case "absolute" => Absolute
        // case "absolute-min" => AbsoluteMin
        case "absolute-dev" => AbsoluteDev
    }

    def fromString(string: String): Option[Resources] = fromStringPF.lift(string)

    val default = InlineMin
}
