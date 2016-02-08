package io.continuum.bokeh

import annotation.implicitNotFound

@implicitNotFound(msg="Can't find ArrayLike type class for type ${T}.")
class ArrayLike[T[_]]

object ArrayLike {
    implicit def TraversableOnceArrayLike[T[_] <: TraversableOnce[_]] = new ArrayLike[T]
    implicit val ArrayArrayLike = new ArrayLike[Array]
}

@implicitNotFound(msg="Can't find MatrixLike type class for type ${T}.")
abstract class MatrixLike[T[_]] {
    def data[A](value: T[A]): (Array[A], Int, Int)
}

object MatrixLike
