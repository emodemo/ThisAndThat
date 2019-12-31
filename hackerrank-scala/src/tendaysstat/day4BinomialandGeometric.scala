package tendaysstat

import scala.io.StdIn
import scala.math.pow

/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object day4BinomialandGeometric {
  
   def main(args: Array[String]){
      
     // binomial 1
      val b = StdIn.readDouble()
      val g = StdIn.readDouble()
      val p = b / (b + g)
      var sum = 0.0
      for(i <- 3 until 7){
        sum += binomialDist(6, i, p)
      }
      println("%.3f".format(sum))
      
      // binomial 2
      sum = 0.0
      for(i <- 8 until 11){
        sum += binomialDist(10, i, 0.88)
      }
      println("%.3f".format(sum))
      
      sum = 0.0
      for(i <- 2 until 11){
        sum += binomialDist(10, i, 0.12)
      }
      println("%.3f".format(sum))
   }
   
   private def factorial(x: Int) : Int = {
     if(x < 0) return 0
     if(x <= 1) return 1
     return x * factorial(x - 1)
   }


   private def combination(n: Int, x: Int) : Int = {
     factorial(n) / (factorial(x) * factorial(n-x))
   }
   
   // n is trials; x is success
   private def binomialDist(n: Int, x: Int, p: Double) : Double = {
     combination(n, x) * pow(p, x) * pow(1-p, n - x)
   }

  def geometric1(): Unit = {
    val n = 5
    val p = 1.0 / 3.0
    val q = 1.0 - p
    val distro = pow(q, n - 1) * p
    println("%.3f".format(distro))
  }

  def geometric2(): Unit = {
    val numerator = StdIn.readInt() // 1
    val denominator = StdIn.readInt() // 3
    val n = StdIn.readInt() // n of inspections

    val p = numerator.toDouble / denominator
    var distro = 0.0
    for(i <- 1 until n+1){
      distro += pow(1.0 - p, i - 1) * p
    }
    println("%.3f".format(distro))
  }

}