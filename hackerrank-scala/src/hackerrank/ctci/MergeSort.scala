package hackerrank.ctci


// sort is fine, but swap count fails on some cases
object MergeSort {
  
  def main(args: Array[String]){
    
    val sc = new java.util.Scanner (System.in);
    val length = sc.nextInt()
    for(i <- 0 until length){
      var n = sc.nextInt()
      var array = Array.ofDim[Int](n)
      for(j <- 0 until n){
        array(j) = sc.nextInt()
      }
      mergesort(array)
    }
  }
  
  private def mergesort(array: Array[Int]) = {
    val aux = Array.ofDim[Int](array.size)
    val count = sort(array, aux, 0, array.length - 1)
    println(count)
  }
  
  private def sort(array: Array[Int], aux: Array[Int], lo: Int, hi: Int) : Int = {
    if(hi <= lo) return 0
    val mid = lo + (hi - lo) / 2;
    var count = 0;
    count += sort(array, aux, lo, mid);
    count += sort(array, aux, mid + 1, hi);
    count += merge(array, aux, lo, mid, hi);
    count
  }
  
  private def merge(array: Array[Int], aux: Array[Int], lo: Int, mid: Int, hi: Int) : Int = {

   // copy
   Array.copy(array, lo, aux, lo, hi+1-lo)
   // swap
   var i = lo
   var j = mid+1;
   var count = 0
   for (k <- lo to hi) {
      if      (i > mid)           {array(k) = aux(j); j += 1}
      else if (j > hi)            {array(k) = aux(i); i += 1}
      else if ((aux(j) < aux(i))) {array(k) = aux(j); j += 1; count += mid + 1 - i}
      else                        {array(k) = aux(i); i += 1}
   }
   count
  }
  
  private def isSorted(array: Array[Int], lo: Int, hi: Int) : Boolean = {
    for(i <- lo+1 to hi){
      if(array(i) > array(i-1)) return false
    }
	  true
   }
}