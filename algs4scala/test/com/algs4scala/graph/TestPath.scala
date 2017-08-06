package com.algs4scala.graph

import org.junit.Assert
import org.junit.Test

class TestPath {

  @Test
  def testBFSPath() : Unit = {
    val graph = createGraph()
    val bfs = new BFS(graph)
    Assert.assertEquals(List(0, 5), bfs.bfsPath(0, 5))
  }

  @Test
  def testDFSPath() : Unit = {
    val graph = createGraph()
    val dfs = new DFS(graph)
    Assert.assertEquals(List(0, 2, 3, 5), dfs.dfsPath(0, 5))
  }

 /**
  * 0 -> 2,1,5
  * 1 -> 0,2
  * 2 -> 0,1,3,4
  * 3 -> 2,4,5
  * 4 -> 2,3
  * 5 -> 3,0
  */
  private def createGraph() : Graph = {
    val graph = new Graph(6)
    graph.addEdge(0, 2, 0)
    graph.addEdge(0, 1, 0)
    graph.addEdge(0, 5, 0)

    graph.addEdge(1, 0, 0)
    graph.addEdge(1, 2, 0)

    graph.addEdge(2, 0, 0)
    graph.addEdge(2, 1, 0)
    graph.addEdge(2, 3, 0)
    graph.addEdge(2, 4, 0)

    graph.addEdge(3, 2, 0)
    graph.addEdge(3, 4, 0)
    graph.addEdge(3, 5, 0)

    graph.addEdge(4, 2, 0)
    graph.addEdge(4, 3, 0)

    graph.addEdge(5, 3, 0)
    graph.addEdge(5, 0, 0)

    graph
  }
}