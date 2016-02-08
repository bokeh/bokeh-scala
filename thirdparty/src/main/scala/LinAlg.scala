package io.continuum.bokeh
package thirdparty

import scala.reflect.ClassTag

import breeze.linalg.{DenseVector,DenseMatrix}
import breeze.storage.Zero

object LinAlg extends LinAlg
trait LinAlg {
    def meshgrid[X1:Zero:ClassTag, X2:Zero:ClassTag]
            (x1: DenseVector[X1], x2: DenseVector[X2]): (DenseMatrix[X1], DenseMatrix[X2]) = {

        val x1Mesh = DenseMatrix.zeros[X1](x2.length, x1.length)
        for (i <- 0 until x2.length) {
            x1Mesh(i, ::) := x1.t
        }

        val x2Mesh = DenseMatrix.zeros[X2](x2.length, x1.length)
        for (i <- 0 until x1.length) {
            x2Mesh(::, i) := x2
        }

        (x1Mesh, x2Mesh)
    }
}
