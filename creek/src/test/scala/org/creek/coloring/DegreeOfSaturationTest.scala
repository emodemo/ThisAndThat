package org.creek.coloring

import org.creek.util.undirectedNumberGraph
import org.scalatest.FunSuite

class DegreeOfSaturationTest extends FunSuite {

  test("degree of saturation coloring") {
    val graph = undirectedNumberGraph("color_graph.txt")
    val coloring = DegreeOfSaturation.color[Double](graph)
    assert (coloring.size == 4)
    // check coloring is correct
    // val nodeToColor = coloring.flatMap(item => item._2.map(node => node -> item._1))
    val nodeToColor = for {
      item <- coloring
      node <- item._2
    } yield node -> item._1

    assert(nodeToColor(1.0) !== nodeToColor(2.0))
    assert(nodeToColor(1.0) !== nodeToColor(3.0))
    assert(nodeToColor(1.0) !== nodeToColor(4.0))
    assert(nodeToColor(2.0) !== nodeToColor(5.0))
    assert(nodeToColor(3.0) !== nodeToColor(2.0))
    assert(nodeToColor(3.0) !== nodeToColor(4.0))
    assert(nodeToColor(3.0) !== nodeToColor(6.0))
    assert(nodeToColor(3.0) !== nodeToColor(7.0))
    assert(nodeToColor(4.0) !== nodeToColor(5.0))
    assert(nodeToColor(4.0) !== nodeToColor(6.0))
    assert(nodeToColor(4.0) !== nodeToColor(7.0))
    assert(nodeToColor(5.0) !== nodeToColor(8.0))
    assert(nodeToColor(6.0) !== nodeToColor(7.0))
    assert(nodeToColor(7.0) !== nodeToColor(8.0))
  }

}
