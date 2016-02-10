package io.continuum.bokeh

import java.io.{File,IOException}
import java.nio.file.{Paths,Files}
import java.nio.charset.StandardCharsets.UTF_8
import java.net.URL
import java.util.Properties

trait ResolvableResources extends Resources {
    protected def getResource(path: String): URL = {
        val res = getClass.getClassLoader.getResource(path)
        if (res != null) res else throw new IOException(s"resource '$path' not found")
    }

    protected def loadResource(path: String): String = {
        new String(Files.readAllBytes(Paths.get(getResource(path).toURI)), UTF_8)
    }
}

trait InlineResources extends ResolvableResources {
    def resolveScript(component: ResourceComponent) = {
        loadResource("js/" + resourceName(component, "js")).asScript
    }

    def resolveStyle(component: ResourceComponent) = {
        loadResource("css/" + resourceName(component, "css")).asStyle
    }
}

trait LocalResources extends ResolvableResources {
    protected def resolveFile(file: File): File

    protected def getFile(path: String): File = {
        val resource = getResource(path)
        resource.getProtocol match {
            case "file"   => resolveFile(new File(resource.getPath))
            case protocol => sys.error(s"unable to load $path due to invalid protocol: $protocol")
        }
    }

    def resolveScript(component: ResourceComponent) = {
        new File(getFile("js"), resourceName(component, "js")).asScript
    }

    def resolveStyle(component: ResourceComponent) = {
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

object Res extends KnownResources {
    case object CDN    extends CDNResources
    case object CDNDev extends CDNResources with DevResources

    case object Inline    extends InlineResources
    case object InlineDev extends InlineResources with DevResources

    case object Relative    extends RelativeResources
    case object RelativeDev extends RelativeResources with DevResources

    case object Absolute    extends AbsoluteResources
    case object AbsoluteDev extends AbsoluteResources with DevResources

    val fromStringPF: PartialFunction[String, Resources] = {
        case "cdn"          => CDN
        case "cdn-dev"      => CDNDev
        case "inline"       => Inline
        case "inline-dev"   => InlineDev
        case "relative"     => Relative
        case "relative-dev" => RelativeDev
        case "absolute"     => Absolute
        case "absolute-dev" => AbsoluteDev
    }

    val default = CDN
}
