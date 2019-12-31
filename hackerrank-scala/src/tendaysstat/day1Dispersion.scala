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
object day1Dispersion {

  def interQuartilerange(){
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
      val lower = fullLength / 2
      var upper = fullLength / 2
      if(fullLength % 2 == 1) {upper = fullLength / 2 + 1}
      
      val sorted = buffer.sortWith((first, second) => first < second).toArray
      val q1 = median(sorted.slice(0, lower))
      val q3 = median(sorted.slice(upper, fullLength))
      printf("%.1f".format(q3-q1))
  }

  def quartiles(args: Array[String]){
    val length = StdIn.readInt()
    val vars = StdIn.readLine().split(" ").map(_.toDouble)

    val lower = length / 2
    var upper = length / 2
    if(length % 2 == 1) {upper = length / 2 + 1}

    val sorted = vars.sortWith((first, second) => first < second)
    println("%.0f".format(median(sorted.slice(0, lower))))
    println("%.0f".format(median(sorted)))
    println("%.0f".format(median(sorted.slice(upper, length))))
  }

  def sd(args: Array[String]){
    val length = StdIn.readInt()
    val vars = StdIn.readLine().split(" ").map(_.toDouble)

    val meanValue = vars.sum/vars.length
    val sumSqrtDistances = vars.map(value => Math.pow(value - meanValue, 2)).sum
    val result = Math.sqrt(sumSqrtDistances/length)
    printf("%.1f".format(result))

  }



  private def median(data: Array[Int]): Double = {
      val middle = data.length/2
      if(data.length % 2 == 1){
        data(middle)
      }else{
        (data(middle - 1) + data(middle))/2 
      }
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