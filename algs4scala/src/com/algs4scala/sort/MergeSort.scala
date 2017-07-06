package com.algs4scala.sort

object MergeSort {
  
  def sort[T](data: List[T])(implicit ordering: Ordering[T]): List[T] = {
    data match {
      case Nil => Nil
      case head :: tail => {
        val middle = data.length / 2
        if(middle == 0) { 
          data
        }
        else {
          val (first, second) = data.splitAt(middle)
          merge(sort(first)(ordering), sort(second)(ordering))
        }
      }
    }
  }
  
  private def merge[T](left: List[T], right: List[T])(implicit ordering: Ordering[T]) : List[T] = {
    (left, right) match {
      case (Nil, Nil) => Nil
      case (Nil, _) => right
      case (_, Nil) => left
      case (leftHead :: leftTail, rightHead :: rightTail) => {
        if(ordering.lt(leftHead, rightHead)) {
          leftHead :: merge(leftTail, right)(ordering)
        }
        else {
          rightHead :: merge(left, rightTail)(ordering)
        }
      }
    }
  }
  
  
 
}