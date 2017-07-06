package tendaysstat

import scala.io.StdIn

/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object dya4Binomial1and2 {
  
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
     combination(n, x) * Math.pow(p, x) * Math.pow(1-p, n - x)
   }
}