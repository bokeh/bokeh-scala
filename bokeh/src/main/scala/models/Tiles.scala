package io.continuum.bokeh

@model abstract class TileSource extends Model {
    object url extends Field[String]
    object tile_size extends Field[Int](256)

    object min_zoom extends Field[Int](0)
    object max_zoom extends Field[Int](30)

    object extra_url_vars extends Field[Map[String, String]] // XXX: Any?
    object attribution extends Field[String]
}

@model abstract class MercatorTileSource extends TileSource {
    object x_origin_offset extends Field[Double](20037508.34)
    object y_origin_offset extends Field[Double](20037508.34)
    object initial_resolution extends Field[Double](156543.03392804097)
    object wrap_around extends Field[Boolean](true)
}

@model class TMSTileSource extends MercatorTileSource

@model class WMTSTileSource extends MercatorTileSource

@model class QUADKEYTileSource extends MercatorTileSource

@model class BBoxTileSource extends MercatorTileSource {
    object use_latlon extends Field[Boolean](false)
}
