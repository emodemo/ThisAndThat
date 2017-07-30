package com.algs4scala.sort

import org.junit.Test
import org.junit.Assert
import org.hamcrest.CoreMatchers

/** */
class TestMergeSort {

  /** */
  @Test
  def sortInt(): Unit = {
    val input = List(1, 3, 9, 5, 7, 2, 8, 4, 6, 0)
    val result = MergeSort.sort(input)
    val expected = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    Assert.assertThat(result, CoreMatchers.is(expected))
  }

  /** */
  @Test
  def sortString(): Unit =  {
    val input = List("x", "b", "y", "a", "d", "c", "e", "z", "f")
    val result = MergeSort.sort(input)
    val expected = List("a", "b", "c", "d", "e", "f", "x", "y", "z")
    Assert.assertThat(result, CoreMatchers.is(expected))
  }

}
