package io.continuum.bokeh

import java.io.{File,IOException}
import java.net.URL
import java.util.Properties

import scalax.io.JavaConverters._
import scalax.file.Path

import play.api.libs.json.{Json,JsValue}

sealed trait Resources {
    def scripts: List[xml.Node]
    def styles: List[xml.Node]

    def wrap(code: String): String = {
        s"Bokeh.$$(function() {\n$code\n});"
    }

    def stringify(value: JsValue): String = {
        Json.stringify(value)
    }

    def logLevel: LogLevel = LogLevel.Info

    protected def getResource(path: String): URL = {
        val res = getClass.getClassLoader.getResource(path)
        if (res != null) res else throw new IOException(s"resource '$path' not found")
    }

    protected def loadResource(path: String): String = {
        getResource(path).asInput.chars.mkString
    }
}

object Resources {
    val bokehjsVersion: String = {
        val stream = getClass.getClassLoader.getResourceAsStream("bokehjs.properties")
        try {
            val props = new java.util.Properties()
            props.load(stream)
            props.getProperty("bokehjs.version")
        } finally {
            stream.close()
        }
    }

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

    trait ExternalFileResources extends ExternalResources {
        def resolveFile(file: File): File

        def getFile(path: String): File = {
            val resource = getResource(path)
            resource.getProtocol match {
                case "file"   => resolveFile(new File(resource.getPath))
                case protocol => sys.error(s"unable to load $path due to invalid protocol: $protocol")
            }
        }

        def baseJSDir: File = getFile("js")
        def baseCSSDir: File = getFile("css")

        def includeJS(path: String): xml.Node = new File(baseJSDir, path).asScript
        def includeCSS(path: String): xml.Node = new File(baseCSSDir, path).asStyle
    }

    trait RelativeResources { self: ExternalFileResources =>
        private val rootDir = new File(System.getProperty("user.dir"))
        def resolveFile(file: File): File = new File(rootDir.toURI.relativize(file.toURI).getPath)
    }

    trait AbsoluteResources { self: ExternalFileResources =>
        def resolveFile(file: File): File = file
    }

    case object Relative extends ExternalFileResources with RelativeResources {
        def scripts = includeJS(jsUnMin) :: Nil
        def styles = includeCSS(cssUnMin) :: Nil
    }

    case object RelativeMin extends ExternalFileResources with RelativeResources {
        def scripts = includeJS(jsMin) :: Nil
        def styles = includeCSS(cssMin) :: Nil
    }

    case object Absolute extends ExternalFileResources with AbsoluteResources {
        def scripts = includeJS(jsUnMin) :: Nil
        def styles = includeCSS(cssUnMin) :: Nil
    }

    case object AbsoluteMin extends ExternalFileResources with AbsoluteResources {
        def scripts = includeJS(jsMin) :: Nil
        def styles = includeCSS(cssMin) :: Nil
    }

    trait DevelopmentResources extends ExternalFileResources {
        def scripts = includeJS(jsUnMin) :: Nil
        def styles = includeCSS(cssUnMin) :: Nil

        override def logLevel: LogLevel = LogLevel.Debug

        override def stringify(value: JsValue): String = {
            Json.prettyPrint(value)
        }
    }

    case object RelativeDev extends DevelopmentResources with RelativeResources
    case object AbsoluteDev extends DevelopmentResources with AbsoluteResources

    abstract class Remote(url: URL) extends ExternalResources {
        def includeJS(path: String): xml.Node = new URL(url, "./" + path).asScript
        def includeCSS(path: String): xml.Node = new URL(url, "./" + path).asStyle

        def scripts = includeJS(resource("js", true, true)) :: Nil
        def styles = includeCSS(resource("css", true, true)) :: Nil
    }

    case object CDN extends Remote(new URL("http://cdn.pydata.org/bokeh/release/"))

    private val fromStringPF: PartialFunction[String, Resources] = {
        case "cdn"          => CDN
        case "inline"       => Inline
        case "inline-min"   => InlineMin
        case "relative"     => Relative
        case "relative-min" => RelativeMin
        case "relative-dev" => RelativeDev
        case "absolute"     => Absolute
        case "absolute-min" => AbsoluteMin
        case "absolute-dev" => AbsoluteDev
    }

    def fromString(string: String): Option[Resources] = fromStringPF.lift(string)

    val default = InlineMin
}
