package io.continuum.bokeh

@fields class Canvas extends PlotObject {
    object botton_bar extends Field[Boolean](true)
    object canvas_height extends Field[Int](600)
    object canvas_width extends Field[Int](600)
    object map extends Field[Boolean](false)
    object use_hdpi extends Field[Boolean](true)
}
