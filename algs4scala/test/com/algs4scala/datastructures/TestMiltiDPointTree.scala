package com.algs4scala.datastructures

import org.junit.Test
import org.junit.Assert

/** */
class TestMiltiDPointTree {

  // TODO: add oct-tree test

  /**
   * expected tree structure is
   * 3,10
   * 2,4      5,7
   * ----     4,6      7,3      4,8      6,9
   *           ----    ---8,6  ----    ----
   *                      ----
   */
  @Test
  def quadtreeInsert(): Unit = {
    // create a tree, create int points and insert them in the the tree
    val tree: MultiDPointTree[Int, String] = new MultiDPointTree
    val points = Array(Array(3, 10), Array(5, 7), Array(6, 9), Array(2, 4), Array(4, 6), Array(7, 3), Array(4, 8), Array(8, 6))
    points.foreach(point => tree.insert(point, point(0) + "" + point(1)))

    Assert.assertEquals("86", tree.find(Array(8, 6)).get)
    Assert.assertSame(None, tree.find(Array(8, 8)))
    Assert.assertEquals("24", tree.find(Array(2, 4)).get)

    Assert.assertEquals(List("57", "86", "46"), tree.query(Array(Array(4, 9), Array(5, 7))))
  }
}
