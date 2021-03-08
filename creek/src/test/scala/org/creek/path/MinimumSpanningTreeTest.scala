package org.creek.path

import org.creek.util._
import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite

class MinimumSpanningTreeTest extends FunSuite {

  test("eagerPrim") {
    implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.01)
    val graph = undirectedNumberGraph("mst.txt")
    val mst = MinimumSpanningTree.eagerPrim[Double](graph)
    assert(mst.map(edge => edge.weight).sum == 1.81) // mst path
    assert(mst.size == 7) // N edges
    val edges = mst.map(edge => (edge.from, edge.to, edge.weight))
    assert(edges.contains((0.0, 7.0, 0.16)))
    assert(edges.contains((7.0, 1.0, 0.19)))
    assert(edges.contains((2.0, 0.0, 0.26)))
    assert(edges.contains((2.0, 3.0, 0.17)))
    assert(edges.contains((7.0, 5.0, 0.28)))
    assert(edges.contains((5.0, 4.0, 0.35)))
    assert(edges.contains((2.0, 6.0, 0.40)))
  }



}
