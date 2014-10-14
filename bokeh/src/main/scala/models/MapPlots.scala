package io.continuum.bokeh

@model abstract class MapOptions extends HasFields {
    object lat extends Field[Double]
    object lng extends Field[Double]
    object zoom extends Field[Int](12)
}

@model abstract class MapPlot extends Plot

@model class GMapOptions extends MapOptions {
    object map_type extends Field[MapType]
}

@model class GMapPlot extends MapPlot {
    object map_options extends Field[GMapOptions]
}

@model class GeoJSOptions extends MapOptions

@model class GeoJSPlot extends MapPlot {
    object map_options extends Field[GeoJSOptions]
}

