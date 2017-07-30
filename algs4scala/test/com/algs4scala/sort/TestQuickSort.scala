package com.algs4scala.sort

import org.junit.Test
import org.junit.Assert
import org.hamcrest.CoreMatchers

/** */
class TestQuickSort {

  /** */
  @Test
  def sortInt(): Unit =  {
    val input = List(1, 3, 9, 5, 7, 2, 8, 4, 6, 0)
    val result = QuickSort.sort(input)
    val expected = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    Assert.assertThat(result, CoreMatchers.is(expected))
  }

  /** */
  @Test
  def sortIntReversed(): Unit =  {
    val input = List(1, 3, 9, 5, 7, 2, 8, 4, 6, 0)
    val result = QuickSort.sort(input, lessThan = (x: Int, y: Int) => Ordering.Int.compare(x, y) > 0)
    val expected = List(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)
    Assert.assertThat(result, CoreMatchers.is(expected))
  }

  /** */
  @Test
  def sortString(): Unit =  {
    val input = List("x", "b", "y", "a", "d", "c", "e", "z", "f")
    val result = QuickSort.sort(input)
    val expected = List("a", "b", "c", "d", "e", "f", "x", "y", "z")
    Assert.assertThat(result, CoreMatchers.is(expected))
  }

  /** */
  @Test
  def sortStringReversed(): Unit =  {
    val input = List("x", "b", "y", "a", "d", "c", "e", "z", "f")
    val result = QuickSort.sort(input, lessThan = (x: String, y: String) => Ordering.String.compare(x, y) > 0)
    val expected = List("z", "y", "x", "f", "e", "d", "c", "b", "a")
    Assert.assertThat(result, CoreMatchers.is(expected))
  }
}
