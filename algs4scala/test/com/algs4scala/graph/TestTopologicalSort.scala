package com.algs4scala.graph

import org.junit.Test
import org.junit.Assert

class TestTopologicalSort {

  @Test
  def testTopologicalSort(): Unit = {

    val graph = prepareGraph()
    val sort = new TopologicalSort(graph)
    val expectedSort = List(8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 1, 5, 4)
    Assert.assertEquals(expectedSort, sort.sort())
  }

  // expected topological is 8,7,2,3,0,6,9,10,11,12,1,5,4
  // expected preorder is = 0,5,4,1,6,9,11,12,10,2,3,7,8
  private def prepareGraph() : Graph = {
    val graph = new Graph(13)
    graph.addEdge(0, 5, 0)
    graph.addEdge(0, 1, 0)
    graph.addEdge(0, 6, 0)

    graph.addEdge(2, 0, 0)
    graph.addEdge(2, 3, 0)

    graph.addEdge(3, 5, 0)

    graph.addEdge(5, 4, 0)

    graph.addEdge(6, 4, 0)
    graph.addEdge(6, 9, 0)

    graph.addEdge(7, 6, 0)

    graph.addEdge(8, 7, 0)

    graph.addEdge(9, 11, 0)
    graph.addEdge(9, 12, 0)
    graph.addEdge(9, 10, 0)

    graph.addEdge(11, 12, 0)

    graph
  }

}