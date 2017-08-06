package com.algs4scala.graph

import org.junit.Test
import org.junit.Assert

class TestMST {

  @Test
  def testPrimLazy(): Unit = {
    val graph = createGraph()
    val mst = new MSTPrimLazy(graph)
    val res = mst.mst()
    val weight = res.map(tuple => tuple._3).sum
    Assert.assertEquals(1.81, weight, 0.01)
  }

  @Test
  def testPrimEager(): Unit = {
    val graph = createGraph()
    val mst = new MSTPrimEager(graph)
    val res = mst.mst()
    val weight = res.map(tuple => tuple._3).sum
    Assert.assertEquals(1.81, weight, 0.01)
  }

  @Test
  def testKruskal(): Unit = {
    val graph = createGraph()
    val mst = new MSTKruskal(graph)
    val res = mst.mst()
    val weight = res.map(tuple => tuple._3).sum
    Assert.assertEquals(1.81, weight, 0.01)
  }

  private def createGraph() : Graph = {
    val graph = new Graph(8)
    add2wayEdge(graph, 4, 5, 0.35)
    add2wayEdge(graph, 4, 7, 0.37)
    add2wayEdge(graph, 5, 7, 0.28)
    add2wayEdge(graph, 0, 7, 0.16)
    add2wayEdge(graph, 1, 5, 0.32)
    add2wayEdge(graph, 0, 4, 0.38)
    add2wayEdge(graph, 2, 3, 0.17)
    add2wayEdge(graph, 1, 7, 0.19)
    add2wayEdge(graph, 0, 2, 0.26)
    add2wayEdge(graph, 1, 2, 0.36)
    add2wayEdge(graph, 1, 3, 0.29)
    add2wayEdge(graph, 2, 7, 0.34)
    add2wayEdge(graph, 6, 2, 0.40)
    add2wayEdge(graph, 3, 6, 0.52)
    add2wayEdge(graph, 6, 0, 0.58)
    add2wayEdge(graph, 6, 4, 0.93)
    graph
  }

  private def add2wayEdge(graph: Graph, from: Int, to: Int, weight: Double) = {
    graph.addEdge(from, to, weight)
    graph.addEdge(to, from, weight)
  }
}
