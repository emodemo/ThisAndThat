package com.algs4scala.datastructures

import scala.collection.mutable.Map
import com.algs4scala.sort.MergeSort

/**
 * Not the best indexedQueue. Not at all.
 *</br> But uses my impl of MergeSort :-)
 *
 * @tparam T the type of itmes
 * @param ordering the ordering of the items
 */
class IndexedPriorityQueue[Т](implicit ordering: Ordering[Т]) {

  private val map = Map[Int, Т]()

  def enqueue(index: Int, item: Т): Unit = {
    map += ((index, item))
  }

  def dequeue() = {
    val sorted = MergeSort.sort(map.values.toList)(ordering)
    val entry = map.find(entry => entry._2 == sorted(0)).get
    map.remove(entry._1)
    entry
  }

  def isEmpty(): Boolean = {
    map.isEmpty
  }
}
