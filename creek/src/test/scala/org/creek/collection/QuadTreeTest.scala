package org.creek.collection

import org.creek.util._
import org.scalatest.FunSuite

class QuadTreeTest extends FunSuite {

  // quad_tree structure must be
  // root 3-10 {2-4; 5-7; - ; 6-11}
  // child 2-4 { - ; - ; 1-9; - }
  // child 5-7 {4-6; 7-3; 4-8; 6-9}
  // child 7-3 { - ; - ; 6-6; 8-6}

  test("query"){
    val tree = new QuadTree[Int, String]()
    val lines = readItemsLines("quad_tree.txt")
    for(line <- lines) tree.insert(line._1.toInt, line._2.toInt, line._1 + line._2)
    // query 1
    var result = tree.query(4, 8, 3, 7)
    assert(result.size === 3)
    assert(result.head === "46")
    assert(result(1) === "73")
    assert(result(2) === "66")
    // query 2
    result = tree.query(1, 6, 8, 11)
    assert(result.size === 3)
    assert(result.head === "310")
    assert(result(1) === "19")
    assert(result(2) === "48")
  }
}
