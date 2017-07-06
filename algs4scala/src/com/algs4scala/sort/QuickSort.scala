package com.algs4scala.sort

import scala.util.Random

object QuickSort {
  
  def sort[T](data: List[T], lessThan: (T, T) => Boolean): List[T] = {
    val shuffle = Random.shuffle(data)
    shuffle match {
      case Nil => Nil
      case head :: tail => {
        val (smaller, bigger) = tail.partition(lessThan(_, head))
        sort(smaller, lessThan) ++ (head :: sort(bigger, lessThan))
      }
    }
  }
  
  def sort[T](data: List[T])(implicit ordering: Ordering[T]): List[T] = {
    val shuffle = Random.shuffle(data)
    shuffle match {
      case Nil => Nil
      case head :: tail => {
        val (smaller, bigger) = tail.partition(ordering.lt(_, head))
        sort[T](smaller) ++ (head :: sort[T](bigger))
      }
    }
  }
  
  // infinite loop
//  def sortLomuto[T](data: List[T])(implicit ordering: Ordering[T]): List[T] = {
//    val shuffle = Random.shuffle(data)
//    shuffle match {
//      case Nil => Nil
//      case head :: tail => {
//        val (smaller, bigger) = partitionLomuto(data)
//        sortLomuto(smaller) ::: sortLomuto(bigger)
//      }
//    }
//  }
//  
//  private def partitionLomuto[T](data: List[T])(implicit ordering: Ordering[T]) = {
//    val pivot = data.last
//    data.partition(ordering.lt(_, pivot))
//  }
}
