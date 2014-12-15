package io.continuum.bokeh

import annotation.implicitNotFound
import breeze.linalg.{DenseVector,DenseMatrix}

@implicitNotFound(msg="Can't find ArrayLike type class for type ${T}.")
class ArrayLike[T]

object ArrayLike {
    implicit def TraversableToArrayLike[T <: Traversable[_]] = new ArrayLike[T]
    implicit def DenseVectorToArrayLike[T] = new ArrayLike[DenseVector[T]]
    implicit def ArrayToArrayLike[T] = new ArrayLike[Array[T]]
}

@implicitNotFound(msg="Can't find MatrixLike type class for type ${T}.")
abstract class MatrixLike[T[_]] {
    def data[A](value: T[A]): (Array[A], Int, Int)
}

object MatrixLike {
    implicit object DenseMatrixToMatrixLike extends MatrixLike[DenseMatrix] {
        def data[A](value: DenseMatrix[A]) = (value.t.toArray, value.rows, value.cols)
    }
}
