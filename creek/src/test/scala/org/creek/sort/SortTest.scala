package org.creek.sort

import org.scalatest.FunSuite
import org.creek.util._

import scala.util.Random

class SortTest extends FunSuite {

  test("mergeSort integers") {
    val input = List(1, 3, 9, 5, 7, 2, 8, 4, 6, 0)
    val result = Sort.mergeSort[Int](input)
    val expected = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    assert(input !== expected)
    assert(result === expected)
  }

  test("mergeSort strings") {
    val input = List("x", "b", "y", "a", "d", "c", "e", "z", "f")
    val result = Sort.mergeSort[String](input)
    val expected = List("a", "b", "c", "d", "e", "f", "x", "y", "z")
    assert(input !== expected)
    assert(result === expected)
  }

  test("mergeSort stability test") {
    val input = readItemsLines("stable_sort.txt")
    val shuffle = Random.shuffle(input)
    val orderByTime = Sort.mergeSort[(String, String)](shuffle)(Ordering.by[(String, String), Int](_._2.toInt))
    val result = Sort.mergeSort[(String, String)](orderByTime)(Ordering.by[(String, String), String](_._1))
    val expected = readItemsLines("stable_sort_result.txt")
    assert(input !== expected)
    assert(result === expected)
  }

  test("quickSort integers") {
    val input = List(1, 3, 9, 5, 7, 2, 8, 4, 6, 0)
    val result = Sort.quickSort(input)
    val expected = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    assert(input !== expected)
    assert(result === expected)
  }

  test("quickSort integers reversed") {
    val input = List(1, 3, 9, 5, 7, 2, 8, 4, 6, 0)
    val result = Sort.quickSort(input, lessThan = (x: Int, y: Int) => Ordering.Int.compare(x, y) > 0)
    val expected = List(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    assert(input !== expected)
    assert(result === expected)
  }

  test("quickSort strings") {
    val input = List("x", "b", "y", "a", "d", "c", "e", "z", "f")
    val result = Sort.quickSort(input)
    val expected = List("a", "b", "c", "d", "e", "f", "x", "y", "z")
    assert(input !== expected)
    assert(result === expected)
  }

  test("quickSort strings reversed") {
    val input = List("x", "b", "y", "a", "d", "c", "e", "z", "f")
    val result = Sort.quickSort(input, lessThan = (x: String, y: String) => Ordering.String.compare(x, y) > 0)
    val expected = List("z", "y", "x", "f", "e", "d", "c", "b", "a")
    assert(input !== expected)
    assert(result === expected)
  }

}
