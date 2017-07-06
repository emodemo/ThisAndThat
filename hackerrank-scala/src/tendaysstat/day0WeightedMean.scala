package tendaysstat

import scala.io.StdIn

/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object day0WeightedMean {
  
  def main(args: Array[String]){
      val length = StdIn.readInt()
      val vars = StdIn.readLine().split(" ").map(_.toInt)
      val weight = StdIn.readLine().split(" ").map(_.toInt)
      var sum = 0.0;
      
      for(i <- 0 until length){
        sum = sum + (vars(i) * (weight(i)))
      }
      val ret = sum/(weight.sum)
      println("%.1f".format(ret))
  }
}