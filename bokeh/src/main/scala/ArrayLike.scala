package io.continuum.bokeh

import annotation.implicitNotFound
import breeze.linalg.{DenseVector,DenseMatrix}

@implicitNotFound(msg="Can't find ArrayLike type class for type ${T}.")
class ArrayLike[T[_]]

object ArrayLike {
    implicit def TraversableArrayLike[T[_] <: Traversable[_]] = new ArrayLike[T]
    implicit val DenseVectorArrayLike = new ArrayLike[DenseVector]
    implicit val ArrayArrayLike = new ArrayLike[Array]
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
