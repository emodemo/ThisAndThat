package tendaysstat

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object day1InterQuartileRange {
  def main(args: Array[String]){
      val length = StdIn.readInt()
      val vars = StdIn.readLine().split(" ").map(_.toInt)
      val weight = StdIn.readLine().split(" ").map(_.toInt)
      
      val buffer = new ArrayBuffer[Int]
      for(i <- 0 until length){
        for(j <- 0 until weight(i)){
          buffer += vars(i)
        }
      }
      
      val fullLength = buffer.length
      var lower = fullLength / 2
      var upper = fullLength / 2
      if(fullLength % 2 == 1) {upper = fullLength / 2 + 1}
      
      val sorted = buffer.sortWith((first, second) => first < second).toArray
      val q1 = median(sorted.slice(0, lower))
      val q3 = median(sorted.slice(upper, fullLength))
      printf("%.1f".format(q3-q1))
  }
  
    private def median(data: Array[Int]): Double = {
      val middle = data.length/2
      if(data.length % 2 == 1){
        data(middle)
      }else{
        (data(middle - 1) + data(middle))/2 
      }
    }
}