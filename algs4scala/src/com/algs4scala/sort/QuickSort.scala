package com.algs4scala.sort

import scala.util.Random

/** */
object QuickSort {

  /**
   * Quick sort
   * @tparam T the type of the data to sort
   * @param data to sort
   * @param lessThan the sorting function
   * @return sorted data
   */
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

  /**
   * Quick sort
   * @tparam T the type of the data to sort
   * @param data to sort
   * @param ordering the sorting function
   * @return sorted data
   */
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

}
