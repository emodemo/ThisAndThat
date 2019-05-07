package org.demo.breeze

import breeze.linalg.{*, DenseMatrix, DenseVector, normalize, sum}
import breeze.numerics.pow
import breeze.stats.regression.leastSquares

class Plays {

  private val normL1 = 1.0

  def test123(data: List[List[Double]], q: Double): Unit = {
    //    val points = data.head.length
    //    val dimensions = data.length
    val input: DenseMatrix[Double] = DenseMatrix(data.map(_.toArray):_*)  // col-point, row-dimension
    val normalizedInput = normalize(input(::, *), normL1) // (::,*) is by col; 1.0 is L1 norm, L2 is default
    val size = input.size
    val scales = List(1.2, 2.0)
    val normalizedScales = normalize(DenseVector(scales.toArray), normL1)
  }

  def linearRegression(): Unit = {
    // example with breeze
    val a = DenseMatrix((9.0, 1.0), (8.0, 1.0), (7.0, 1.0), (5.0, 1.0), (6.0, 1.0), (4.0, 1.0))
    val b = DenseVector(2.81, 4.17, 5.52, 7.96, 6.7, 9.24)
    val result = leastSquares(a, b)
    val residual = sum(pow(b - result(a), 2))
    val coef = result.coefficients
    val rsq = result.rSquared // WRONG, it gives ssr instead
  }

}
