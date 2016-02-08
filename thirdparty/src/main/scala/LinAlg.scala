package io.continuum.bokeh
package thirdparty

import scala.reflect.ClassTag

import breeze.linalg.{DenseVector,DenseMatrix}

object LinAlg extends LinAlg
trait LinAlg {
    def meshgrid(x1: DenseVector[Double], x2: DenseVector[Double]): (DenseMatrix[Double], DenseMatrix[Double]) = {
        val x1Mesh = DenseMatrix.zeros[Double](x2.length, x1.length)
        for (i <- 0 until x2.length) {
            x1Mesh(i, ::) := x1.t
        }
        val x2Mesh = DenseMatrix.zeros[Double](x2.length, x1.length)
        for (i <- 0 until x1.length) {
            x2Mesh(::, i) := x2
        }
        (x1Mesh, x2Mesh)
    }
}
