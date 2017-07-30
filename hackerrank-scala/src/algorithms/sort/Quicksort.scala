package algorithms.sort

import scala.io.StdIn
import scala.util.Random

object QuickSort {
  
   def main(args: Array[String]){
     
     val length = StdIn.readInt()
     var array =  StdIn.readLine().split(" ").map(_.toInt)
     quickSort(array)
     print(array, 0, array.size)
   }
   
   private def quickSort(array: Array[Int]) {
     var vars = Random.shuffle(array.toList).toArray
     sort(array, 0, array.size - 1)
   }
   
   // will partition | <= j | j | >= j |
   // returns j - the index of the partition item
   // takes the 1st value as the initial hook
   private def partition(array: Array[Int], lo: Int, hi: Int) : Int = {
     var i = lo + 1
     var j = hi
     while(i < j){
       // go right as long as the value found is less than the 1st value (pivot)
       while(array(i) < array(lo) && i < hi) i += 1
       // go left as long as the value found is bigger than the 1st value (pivot)
       while(array(lo) < array(j) && j >= lo) j -= 1
       if(i < j) swap(array, i, j) 
     }
     if(array(lo) > array(j)) swap(array, lo, j)
     j
   }
   
   private def swap(array: Array[Int], i: Int, j: Int) {
     val swap = array(i)
     array(i) = array(j)
     array(j) = swap
   }
   
   private def sort(array: Array[Int], lo: Int, hi: Int) {
     if(hi <= lo) return
     val j = partition(array, lo, hi)
     sort(array, lo, j-1)
     sort(array, j+1, hi)
   }
   
   private def print(array: Array[Int], from: Int, to: Int) {
     val array2 = array.slice(from, to)
     array2.foreach(v => printf("%.0f ".format(v.toDouble)))
     println()
   }
}
