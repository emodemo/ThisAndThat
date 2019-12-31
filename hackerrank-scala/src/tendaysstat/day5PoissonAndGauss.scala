package tendaysstat

import scala.annotation.tailrec
import scala.io.StdIn
import scala.math.{E, pow, sqrt, exp, abs}
object day5PoissonAndGauss {

  def poisson1(): Unit ={

    val lambda = StdIn.readDouble()
    val x = StdIn.readInt()
    var result = pow(lambda, x) * pow(E, -1 * lambda)
    result = result / factorial(x)
    println("%.3f".format(result))
  }


  def poisson2(): Unit = {
    val lambda1 = 0.88
    val xsquare = lambda1 + (lambda1 * lambda1)
    val cost1 = 160 + (40 * xsquare)

    val lambda2 = 1.55
    val ysquare = lambda2 + (lambda2 * lambda2)
    val cost2 = 128 + (40 * ysquare)

    println("%.3f".format(cost1))
    println("%.3f".format(cost2))
  }

    // time for car is X hours// time for car is X hours
    // Gaussian with mean = 20 h and SD = 2 h
    // result1: p for car in less than 19,5 hours
    // result2: p for car between 22 and 22 hours
  def gauss1(): Unit ={ // cumulative probability for X being leq to x is FX(x)=P(X=<x)
      // cumulative probability for X btwn a nd b is FX(b) - FX(a) = P(a=<X=<b) = P(a<X<b)
      // CDF and error functions
      val mean = 20
      val sd = 2
      val result1 = cdf(19.5, mean, sd)
      val result2 = cdf(22, mean, sd) - cdf(20, mean, sd)
      println("%.3f".format(result1))
      println("%.3f".format(result2))
  }

  // mean = 70, sd = 10
  // p for x > 80, for x >= 60, for x < 60
  def gauss2(): Unit = {
    val mean = 70
    val sd = 10
    val result1 = 100 * (1 - cdf(80, mean, sd))
    val result2 = 100 * (1 - cdf(60, mean, sd))
    val result3 = 100 * cdf(60, mean, sd)
    println("%.3f".format(result1))
    println("%.3f".format(result2))
    println("%.3f".format(result3))
  }

  private def factorial(x: Int) : Int = {
    if(x < 0) 0
    else if(x <= 1) 1
    else x * factorial(x - 1)
  }

  private def cdf(x: Double, mean: Double, sd: Double) = {
    val z = (x - mean) / (sd * sqrt(2))
    0.5 * (1 + err(z))
  }

  /* http://introcs.cs.princeton.edu/java/21function/ErrorFunction.java.html */
  private def err(z: Double) = {
    val t = 1.0 / (1.0 + 0.5 * abs(z))
    // use Horner's method
    val ans = 1 - t * exp(-z * z - 1.26551223 + t * (1.00002368 + t * (0.37409196 + t * (0.09678418 + t * (-0.18628806 + t * (0.27886807 + t * (-1.13520398 + t * (1.48851587 + t * (-0.82215223 + t * 0.17087277)))))))))
    if (z >= 0) ans
    else -ans
  }
}
