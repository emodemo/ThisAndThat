package tendaysstat

import java.util.Scanner
import scala.collection.mutable.Map


/**
 * 
 * https://www.hackerrank.com/challenges/s10-basic-statistics
 * 
 * @author emo
 *
 */
object day0Averages {
   
  def main(args: Array[String]){
    
    val in = new Scanner(System.in)
		val vars = Array.ofDim[Double](in.nextInt())
		for(i <- 0 until vars.length){
		  vars(i) = in.nextDouble()
		}
    
		println(mean(vars))
  	println(median(vars))
		println(mode(vars))
    
  }
   
  def mean(args: Array[Double]): Double = {
    args.sum/args.size
  }
  
  def median(args: Array[Double]): Double = {
    val sorted = args.sortWith((first, second) => first < second)
    val middle = sorted.length/2
    if(sorted.length%2 == 1){
      sorted(middle)
    }else{
      (sorted(middle - 1) + sorted(middle))/2 
    }
  }
  
  def mode(args: Array[Double]): Int = {
    val sorted = args.sortWith((first, second) => first < second)
    var frequencies = Map[Double, Int]()
    for(key <- sorted){
      var value = 0;
      frequencies.get(key) match {
        case None => value = 1
        case Some(v) => value = v + 1
      }
      frequencies += (key -> value)
    }
    
    val maxfreq = frequencies.maxBy(_._2)._2
    val filtered = frequencies.filter((e) => e._2 == maxfreq)
    filtered.min._1.toInt
  }
  
}