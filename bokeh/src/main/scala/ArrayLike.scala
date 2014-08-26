package io.continuum.bokeh

import annotation.implicitNotFound
import breeze.linalg.DenseVector

@implicitNotFound(msg="Can't find ArrayLike type class for type ${T}.")
class ArrayLike[T]

object ArrayLike {
    implicit def TraversableToArrayLike[T <: Traversable[_]] = new ArrayLike[T]
    implicit def DenseVectorToArrayLike[T] = new ArrayLike[DenseVector[T]]
    implicit def ArrayToArrayLike[T] = new ArrayLike[Array[T]]
}
