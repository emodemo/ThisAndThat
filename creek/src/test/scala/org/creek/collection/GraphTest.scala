package org.creek.collection

import org.scalatest.FunSuite

class GraphTest extends FunSuite{

  test("undirected_adjacentNodes") {
    val graph = Graph.apply[Int](Undirected)
    graph.addEdge(1,2)
    graph.addEdge(1,3 )

    var adjacent = graph.adjacentNodes(1)
    assert(adjacent.length === 2)
    assert(adjacent.contains(2))
    assert(adjacent.contains(3))

    adjacent = graph.adjacentNodes(2)
    assert(adjacent.length === 1)
    assert(adjacent.contains(1))

    adjacent = graph.adjacentNodes(3)
    assert(adjacent.length === 1)
    assert(adjacent.contains(1))
  }

  test("directed_adjacentNodes") {
    val graph = Graph.apply[Int](Directed)
    graph.addEdge(1,2)
    graph.addEdge(1,3)

    var adjacent = graph.adjacentNodes(1)
    assert(adjacent.length === 2)
    assert(adjacent.contains(2))
    assert(adjacent.contains(3))

    adjacent = graph.adjacentNodes(2)
    assert(adjacent.length === 0)

    adjacent = graph.adjacentNodes(3)
    assert(adjacent.length === 0)
  }
}