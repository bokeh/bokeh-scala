package org.continuumio.bokeh

abstract class BaseGlyph extends PlotObject

class AnnularWedge extends BaseGlyph with FillProps with LineProps {
    //x = DataSpec
    //y = DataSpec
    //inner_radius = DataSpec(min_value=0)
    //outer_radius = DataSpec(min_value=0)
    //start_angle = DataSpec
    //end_angle = DataSpec
    //direction = Enum('clock', 'anticlock')
}

class Annulus extends BaseGlyph with FillProps with LineProps {
    //x = DataSpec
    //y = DataSpec
    //inner_radius = DataSpec(min_value=0)
    //outer_radius = DataSpec(min_value=0)
}

class Arc extends BaseGlyph with LineProps {
    //x = DataSpec
    //y = DataSpec
    //radius = DataSpec(min_value=0)
    //start_angle = DataSpec
    //end_angle = DataSpec
    //direction = Enum('clock', 'anticlock')
}

class Bezier extends BaseGlyph with LineProps {
    //x0 = DataSpec
    //y0 = DataSpec
    //x1 = DataSpec
    //y1 = DataSpec
    //cx0 = DataSpec
    //cy0 = DataSpec
    //cx1 = DataSpec
    //cy1 = DataSpec
}

class Image extends BaseGlyph {
    //image = DataSpec
    //x = DataSpec
    //y = DataSpec
    //dw = DataSpec
    //dh = DataSpec
    //palette = DataSpec
}

class ImageURI extends BaseGlyph {
    //x = DataSpec
    //y = DataSpec
    //angle = DataSpec
}

class ImageRGBA extends BaseGlyph {
    //image = DataSpec
    //x = DataSpec
    //y = DataSpec
    //dw = DataSpec
    //dh = DataSpec
}

class Line extends BaseGlyph with LineProps {
    //x = DataSpec
    //y = DataSpec
}

class MultiLine extends BaseGlyph with LineProps {
    //xs = DataSpec
    //ys = DataSpec
}

class Oval extends BaseGlyph with FillProps with LineProps {
    //width = DataSpec
    //height = DataSpec
    //angle = DataSpec
}

class Patch extends BaseGlyph with FillProps with LineProps {
    //x = DataSpec
    //y = DataSpec
}

class Patches extends BaseGlyph with LineProps with FillProps {
    //xs = DataSpec
    //ys = DataSpec
}

class Quad extends BaseGlyph with FillProps with LineProps {
    //left = DataSpec
    //right = DataSpec
    //bottom = DataSpec
    //top = DataSpec
}

class Quadratic extends BaseGlyph with LineProps {
    //x0 = DataSpec
    //y0 = DataSpec
    //x1 = DataSpec
    //y1 = DataSpec
    //cx = DataSpec
    //cy = DataSpec
}

class Ray extends BaseGlyph with LineProps {
    //x = DataSpec
    //y = DataSpec
    //angle = DataSpec
    //length = DataSpec
}

class Rect extends BaseGlyph with FillProps with LineProps {
    //x = DataSpec
    //y = DataSpec
    //width = DataSpec
    //height = DataSpec
    //angle = DataSpec
}

class Segment extends BaseGlyph with LineProps {
    //x0 = DataSpec
    //y0 = DataSpec
    //x1 = DataSpec
    //y1 = DataSpec
}

class Text extends BaseGlyph with TextProps {
    //x = DataSpec
    //y = DataSpec
    //text = DataSpec
    //angle = DataSpec
}

class Wedge extends BaseGlyph with FillProps with LineProps {
    //x = DataSpec
    //y = DataSpec
    //radius = DataSpec(min_value=0)
    //start_angle = DataSpec
    //end_angle = DataSpec
    //direction = Enum('clock', 'anticlock')
}
