package io.continuum.bokeh

import play.api.libs.json.JsArray

@model abstract class MapOptions extends HasFields {
    object lat extends Field[Double]
    object lng extends Field[Double]
    object zoom extends Field[Int](12)
}

@model abstract class MapPlot extends Plot {
    // TODO: object map_options extends Field[MapOptions]
    // and later override this in subclasses
}

@model class GMapOptions extends MapOptions {
    object map_type extends Field[MapType]
    object styles extends Field[JsArray]
}

@model class GMapPlot extends MapPlot {
    object map_options extends Field[GMapOptions]
}

@model class GeoJSOptions extends MapOptions

@model class GeoJSPlot extends MapPlot {
    object map_options extends Field[GeoJSOptions]
}

