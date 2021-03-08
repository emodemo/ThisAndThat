package org.creek.sort

import scala.util.Random

object Sort {

  /**
    * Merge sort, stable
    * @tparam T the type of the data to sort
    * @param data to sort
    * @param ordering the sorting function
    * @return sorted data
    */
  // TODO: too many objects of type List are created. Need to nullify unused ones
  def mergeSort[T](data: List[T])(implicit ordering: Ordering[T]): List[T] = data match {
    case Nil => Nil
    case _ => //case head :: tail =>
      val middle = data.length / 2
      if (middle == 0) data
      else {
        val (first, second) = data.splitAt(middle)
        // to preserve stability
        merge(mergeSort(second)(ordering), mergeSort(first)(ordering))
      }
  }

  private def merge[T](left: List[T], right: List[T])(implicit ordering: Ordering[T]): List[T] = {
    (left, right) match {
      case (Nil, Nil) => Nil
      case (Nil, _)   => right
      case (_, Nil)   => left
      case (leftHead :: leftTail, rightHead :: rightTail) =>
        if (ordering.lt(leftHead, rightHead))
          leftHead :: merge(leftTail, right)(ordering)
        else
          rightHead :: merge(left, rightTail)(ordering)
    }
  }

  /**
    * Quick sort, unstable
    * @tparam T the type of the data to sort
    * @param data to sort
    * @param lessThan the sorting function
    * @return sorted data
    */
  def quickSort[T](data: List[T], lessThan: (T, T) => Boolean): List[T] = {
    val shuffle = Random.shuffle(data)
    shuffle match {
      case Nil => Nil
      case head :: tail =>
        val (smaller, bigger) = tail.partition(lessThan(_, head))
        quickSort[T](smaller, lessThan) ++ (head :: quickSort[T](bigger, lessThan))
    }
  }

  /**
    * Quick sort, unstable
    * @tparam T the type of the data to sort
    * @param data to sort
    * @param ordering the sorting function
    * @return sorted data
    */
  def quickSort[T](data: List[T])(implicit ordering: Ordering[T]): List[T] = {
    val shuffle = Random.shuffle(data)
    shuffle match {
      case Nil => Nil
      case head :: tail =>
        val (smaller, bigger) = tail.partition(ordering.lt(_, head))
        quickSort[T](smaller) ++ (head :: quickSort[T](bigger))
    }
  }
}
