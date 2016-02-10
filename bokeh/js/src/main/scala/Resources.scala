package io.continuum.bokeh

object Res extends KnownResources {
    case object CDN    extends CDNResources
    case object CDNDev extends CDNResources with DevResources

    val fromStringPF: PartialFunction[String, Resources] = {
        case "cdn"          => CDN
        case "cdn-dev"      => CDNDev
    }

    val default = CDN
}
