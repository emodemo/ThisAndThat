package org.creek.collection

import org.scalatest.FunSuite

// TODO: import other tests cases
class KDTreeTest extends FunSuite {

  test("kdTree query"){
    val tree = new KDTree[Double](Double.NegativeInfinity, Double.PositiveInfinity,2)
    tree.insert(Array(2.0,3.0))
    tree.insert(Array(1.0,5.0))
    tree.insert(Array(4.0,2.0))
    tree.insert(Array(4.0,5.0))
    tree.insert(Array(3.0,3.0))
    tree.insert(Array(4.0,4.0))
    val result = tree.query(Array((1.5, 3.5), (1.5, 3.5)))
    assert(result.length == 2)
    assert(result.head === Array(2.0, 3.0))
    assert(result(1) === Array(3.0, 3.0))
  }

}
