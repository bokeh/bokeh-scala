package io.continuum.bokeh

import java.io.{File,IOException}
import java.net.URL
import java.util.Properties

import scalax.io.JavaConverters._
import scalax.file.Path

sealed abstract class ResourceComponent(val name: String) {
    val js = true
    val css = true
}
case object BokehCore extends ResourceComponent("bokeh")
case object BokehWidgets extends ResourceComponent("bokeh-widgets")
case object BokehCompiler extends ResourceComponent("bokeh-compiler") {
    override val css = false
}

case class Bundle(scripts: xml.NodeSeq, styles: xml.NodeSeq)

sealed trait Resources {
    def minified: Boolean = true

    def logLevel: LogLevel = LogLevel.Info

    val indent = 0

    def stringify[T:Json.Writer](obj: T): String = {
        Json.write(obj, indent=indent)
    }

    def wrap(code: String): String = s"Bokeh.$$(function() {\n$code\n});"

    protected def logLevelScript: xml.Node = {
        s"Bokeh.set_log_level('$logLevel');".asScript
    }

    protected def getResource(path: String): URL = {
        val res = getClass.getClassLoader.getResource(path)
        if (res != null) res else throw new IOException(s"resource '$path' not found")
    }

    protected def loadResource(path: String): String = {
        getResource(path).asInput.chars.mkString
    }

    def bundle(refs: List[Model]): Bundle = {
        val components = (Some(BokehCore) :: useWidgets(refs) :: useCompiler(refs) :: Nil).flatten

        val scripts = components.filter(_.js == true).map(resolveScript) ++ List(logLevelScript)
        val styles = components.filter(_.css == true).map(resolveStyle)

        def separate(nodes: List[xml.Node]): xml.NodeSeq = {
            nodes.flatMap(_ ++ xml.Text("\n"))
        }

        Bundle(separate(scripts), separate(styles))
    }

    def useWidgets(refs: List[Model]): Option[ResourceComponent] = {
        refs.collectFirst { case ref: widgets.Widget => ref }
            .map { _ => BokehWidgets }
    }

    def useCompiler(refs: List[Model]): Option[ResourceComponent] = {
        refs.collect { case ref: CustomModel => ref.implementation }
            .collectFirst { case impl@CoffeeScript(_) => impl }
            .map { _ => BokehCompiler }
    }

    protected def resolveScript(component: ResourceComponent): xml.Node
    protected def resolveStyle(component: ResourceComponent): xml.Node

    protected def resourceName(component: ResourceComponent, ext: String, version: Boolean=false) = {
        val ver = if (version) s"-${Resources.version}" else ""
        val min = if (minified) ".min" else ""
        s"${component.name}$ver$min.$ext"
    }
}

trait DevResources { self: Resources =>
    override val minified = false

    override val logLevel = LogLevel.Debug

    override val indent = 2
}

trait InlineResources extends Resources {
    def resolveScript(component: ResourceComponent): xml.Node = {
        loadResource("js/" + resourceName(component, "js")).asScript
    }

    def resolveStyle(component: ResourceComponent): xml.Node = {
        loadResource("css/" + resourceName(component, "css")).asStyle
    }
}

trait ExternalResources extends Resources

trait LocalResources extends ExternalResources {
    protected def resolveFile(file: File): File

    protected def getFile(path: String): File = {
        val resource = getResource(path)
        resource.getProtocol match {
            case "file"   => resolveFile(new File(resource.getPath))
            case protocol => sys.error(s"unable to load $path due to invalid protocol: $protocol")
        }
    }

    def resolveScript(component: ResourceComponent): xml.Node =  {
        new File(getFile("js"), resourceName(component, "js")).asScript
    }

    def resolveStyle(component: ResourceComponent): xml.Node = {
        new File(getFile("css"), resourceName(component, "css")).asStyle
    }
}

trait RelativeResources extends LocalResources {
    def resolveFile(file: File): File = {
        val rootDir = new File(System.getProperty("user.dir"))
        new File(rootDir.toURI.relativize(file.toURI).getPath)
    }
}

trait AbsoluteResources extends LocalResources {
    def resolveFile(file: File): File = file.getAbsoluteFile()
}

abstract class RemoteResources(url: URL) extends ExternalResources {
    def resolveScript(component: ResourceComponent): xml.Node = {
        new URL(url, "./" + resourceName(component, "js", true)).asScript
    }

    def resolveStyle(component: ResourceComponent): xml.Node = {
        new URL(url, "./" + resourceName(component, "css", true)).asStyle
    }
}

abstract class CDNResources extends RemoteResources(new URL("http://cdn.pydata.org/bokeh/release/"))

object Resources {
    final val version: String = {
        val stream = getClass.getClassLoader.getResourceAsStream("bokehjs.properties")
        try {
            val props = new java.util.Properties()
            props.load(stream)
            props.getProperty("bokehjs.version")
        } finally {
            stream.close()
        }
    }

    case object CDN    extends CDNResources
    case object CDNDev extends CDNResources with DevResources

    case object Inline    extends InlineResources
    case object InlineDev extends InlineResources with DevResources

    case object Relative    extends RelativeResources
    case object RelativeDev extends RelativeResources with DevResources

    case object Absolute    extends AbsoluteResources
    case object AbsoluteDev extends AbsoluteResources with DevResources

    private val fromStringPF: PartialFunction[String, Resources] = {
        case "cdn"          => CDN
        case "cdn-dev"      => CDNDev
        case "inline"       => Inline
        case "inline-dev"   => InlineDev
        case "relative"     => Relative
        case "relative-dev" => RelativeDev
        case "absolute"     => Absolute
        case "absolute-dev" => AbsoluteDev
    }

    def fromString(string: String): Option[Resources] = fromStringPF.lift(string)

    val default = CDN
}
