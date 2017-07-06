package tendaysstat

import scala.io.StdIn

/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object day1SD {
   def main(args: Array[String]){
      val length = StdIn.readInt()
      val vars = StdIn.readLine().split(" ").map(_.toDouble)
      
      val meanValue = vars.sum/vars.size
      val sumSqrtDistances = vars.map(value => Math.pow((value - meanValue), 2)).sum
      val result = Math.sqrt(sumSqrtDistances/length)
      printf("%.1f".format(result))
      
   }
  
   
}