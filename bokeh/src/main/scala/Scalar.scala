package io.continuum.bokeh

import scala.annotation.implicitNotFound

@implicitNotFound(msg="Can't find Scalar type class for type ${T}.")
class Scalar[T]

object Scalar {
    implicit val IntScalar = new Scalar[Int]
    implicit val DoubleScalar = new Scalar[Double]
}
