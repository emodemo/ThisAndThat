package com.algs4scala.graph

import org.junit.Test
import org.junit.Assert

class TestShortestPat {



  @Test
  def testDijkstra(){
    val graph = createGraph()
    val dijkstra = new ShortestPathDijkstra(graph)
    Assert.assertEquals(dijkstra.shortestPath(0, 0).map(edge => edge.weight).sum, 0.00, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 1).map(edge => edge.weight).sum, 1.05, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 2).map(edge => edge.weight).sum, 0.26, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 3).map(edge => edge.weight).sum, 0.99, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 4).map(edge => edge.weight).sum, 0.38, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 5).map(edge => edge.weight).sum, 0.73, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 6).map(edge => edge.weight).sum, 1.51, 0.01)
    Assert.assertEquals(dijkstra.shortestPath(0, 7).map(edge => edge.weight).sum, 0.60, 0.01)

  }

  @Test
  def testBellmanFord(){
    val graph = createGraph()
    val bellmanford = new ShortestPathBellmanFord(graph)
    Assert.assertEquals(bellmanford.shortestPath(0, 0).map(edge => edge.weight).sum, 0.00, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 1).map(edge => edge.weight).sum, 1.05, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 2).map(edge => edge.weight).sum, 0.26, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 3).map(edge => edge.weight).sum, 0.99, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 4).map(edge => edge.weight).sum, 0.38, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 5).map(edge => edge.weight).sum, 0.73, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 6).map(edge => edge.weight).sum, 1.51, 0.01)
    Assert.assertEquals(bellmanford.shortestPath(0, 7).map(edge => edge.weight).sum, 0.60, 0.01)

  }

  /**
   * Expected shortest paths from 0 to each other node
   * 0 to 0 0,00
   * 0 to 1 1,05   0->4 0.38   4->5 0.35   5->1 0.32
   * 0 to 2 0,26   0->2 0.26
   * 0 to 3 0,99   0->2 0.26   2->7 0.34   7->3 0.39
   * 0 to 4 0,38   0->4 0.38
   * 0 to 5 0,73   0->4 0.38   4->5 0.35
   * 0 to 6 1,51   0->2 0.26   2->7 0.34   7->3 0.39   3->6 0.52
   * 0 to 7 0,60   0->2 0.26   2->7 0.34
   */
  private def createGraph() : Graph = {
    val graph = new Graph(8)
    graph.addEdge(4, 5, 0.35)
    graph.addEdge(5, 4, 0.35)
    graph.addEdge(4, 7, 0.37)
    graph.addEdge(5, 7, 0.28)
    graph.addEdge(7, 5, 0.28)
    graph.addEdge(5, 1, 0.32)
    graph.addEdge(0, 4, 0.38)
    graph.addEdge(0, 2, 0.26)
    graph.addEdge(7, 3, 0.39)
    graph.addEdge(1, 3, 0.29)
    graph.addEdge(2, 7, 0.34)
    graph.addEdge(6, 2, 0.40)
    graph.addEdge(3, 6, 0.52)
    graph.addEdge(6, 0, 0.58)
    graph.addEdge(6, 4, 0.93)
    graph
  }

}