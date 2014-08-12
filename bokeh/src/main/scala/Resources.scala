package io.continuum.bokeh

import java.io.File
import java.net.URL

import scalax.io.JavaConverters._
import scalax.file.Path

sealed trait Resources {
    def scripts: List[xml.Node]
    def styles: List[xml.Node]

    def wrap(code: String): String = {
        s"(function() {\n$code\n})();"
    }

    protected def getResource(path: String): URL = {
        getClass.getClassLoader.getResource(path)
    }

    protected def loadResource(path: String): String = {
        getResource(path).asInput.chars.mkString
    }
}

object Resources {
    private val jsMin = "bokeh.min.js"
    private val jsUnMin = "bokeh.js"

    private val cssMin = "bokeh.min.css"
    private val cssUnMin = "bokeh.css"

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

    private val fromStringPF: PartialFunction[String, Resources] = {
        // case "cdn" => CDN
        // case "cdn-min" => CDNMin
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
