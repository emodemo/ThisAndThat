package tendaysstat

import scala.io.StdIn

/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object day1Quartiles {
  
   def main(args: Array[String]){
      val length = StdIn.readInt()
      val vars = StdIn.readLine().split(" ").map(_.toDouble)
      
      var lower = length / 2
      var upper = length / 2
      if(length % 2 == 1) {upper = length / 2 + 1}
      
      val sorted = vars.sortWith((first, second) => first < second)
      println("%.0f".format(median(sorted.slice(0, lower))))
      println("%.0f".format(median(sorted)))
      println("%.0f".format(median(sorted.slice(upper, length))))
   }
   
    private def median(data: Array[Double]): Double = {
      val middle = data.length/2
      if(data.length % 2 == 1){
        data(middle)
      }else{
        (data(middle - 1) + data(middle))/2 
      }
    }
    
    
  
}