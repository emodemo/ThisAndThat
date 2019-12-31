package tendaysstat

import scala.collection.mutable.ListBuffer
import scala.io.StdIn
import scala.math.{pow, sqrt}

object day7Correlation {

  def pearsonCorrelation() = {

    val size = StdIn.readInt()
    val x = Array[Double](size)
    val y = Array[Double](size)
    val xValues = StdIn.readLine().split(" ")
    val yValues = StdIn.readLine().split(" ")
    for (i <- 0 until size) {
      x(i) = xValues(i).toDouble
      y(i) = yValues(i).toDouble
    }
    println("%.3f".format(pearson(x, y)))
  }

  private def pearson(x: Array[Double], y: Array[Double]) : Double = {
    val meanX = mean(x)
    val meanY = mean(y)
    val len = x.length
    var numerator = 0.0
    for(i <- 0 until len){
      numerator += (x(i) - meanX) * (y(i) - meanY)
    }
    val denominator = len * sd(x, meanX) * sd(y, meanY)
    numerator / denominator
  }

  private def mean(x: Array[Double]) = {
    x.sum / x.length
  }

  private def sd(x: Array[Double], mean: Double) = {
    var sum = 0.0
    val n = x.length
    for(i <- 0 until n){
      sum += pow(x(i) - mean, 2)
    }
    val variance = sum / n
    sqrt(variance)
  }

  def spearmanCorrelation() = {
    val size = StdIn.readInt()
    val x = Array[Double](size)
    val y = Array[Double](size)
    val xValues = StdIn.readLine().split(" ")
    val yValues = StdIn.readLine().split(" ")
    for (i <- 0 until size) {
      x(i) = xValues(i).toDouble
      y(i) = yValues(i).toDouble
    }
    println("%.3f".format(spearman(x, y)))
  }

  private def spearman(x: Array[Double], y: Array[Double]) : Double = {
    val rankX = ranks(x)
    val rankY = ranks(y)
    val len = x.length
    var dsq = 0.0
    for(i <- 0 until len){
      dsq += pow(rankX(i) - rankY(i), 2)
    }
    val r = 1 - ((6 * dsq) / (len * ((len * len) - 1)))
    r
  }

  private def ranks(k: Array[Double]): Array[Double] = {
    val n = k.length
    val tuples = ListBuffer[(Double, Int)]()
    for(i <- 0 to n){
      tuples +=  Tuple2(k(i), i)
    }
    val sorted = tuples.sortWith((t1, t2) => t1._1 < t2._1)
    val ranks = Array[Double](n)
    sorted.foreach(s => ranks(s._2) = s._1)
    ranks
  }


}
