package org.continuumio.bokeh

sealed abstract class Marker extends BaseGlyph with FillProps with LineProps

class Circle extends Marker
class Square extends Marker
class Triangle extends Marker
class Cross extends Marker
class Xmarker extends Marker
class Diamond extends Marker
class InvertedTriangle extends Marker
class SquareX extends Marker
class Asterisk extends Marker
class DiamondCross extends Marker
class CircleCross extends Marker
class HexStar extends Marker
class SquareCross extends Marker
class CircleX extends Marker
