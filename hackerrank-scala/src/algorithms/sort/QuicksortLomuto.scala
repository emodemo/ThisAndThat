package algorithms.sort

import scala.io.StdIn
import scala.util.Random

object QuickSortLomuto {
  def main(args: Array[String]){
     
     val length = StdIn.readInt()
     var array =  StdIn.readLine().split(" ").map(_.toInt)
     quickSort(array)
   }
   
   private def quickSort(array: Array[Int]) {
     var vars = Random.shuffle(array.toList).toArray
     sort(array, 0, array.size - 1)
   }
   
   // takes the last element as a pivot
   private def lomutoPartition(array: Array[Int], lo: Int, hi: Int) : Int = {
     val pivot = array(hi)
     var i = lo - 1
     for(j <- lo to hi){
        if(array(j) <= pivot){
          i += 1
          if(i != j) swap(array, i, j)
        }
     }
     i
   }
   
   private def swap(array: Array[Int], i: Int, j: Int) {
     val swap = array(i)
     array(i) = array(j)
     array(j) = swap
   }
   
   private def sort(array: Array[Int], lo: Int, hi: Int) {
     if(hi <= lo) return
     val j = lomutoPartition(array, lo, hi)
     print(array, 0, array.size)
     sort(array, lo, j-1)
     sort(array, j+1, hi)
   }
   
   private def print(array: Array[Int], from: Int, to: Int) {
     val array2 = array.slice(from, to)
     array2.foreach(v => printf("%.0f ".format(v.toDouble)))
     println()
   }
}