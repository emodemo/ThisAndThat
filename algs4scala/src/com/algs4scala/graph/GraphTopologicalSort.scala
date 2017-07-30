package com.algs4scala.graph

import scala.collection.mutable.Queue
import scala.collection.mutable.Stack

/**
 * Topological on graph
 * @param graph to surch in
 */
class GraphTopologicalSort(graph: Graph) {

  val preOrder = Queue[Int]()
  val inOrder = Queue[Int]()
  val postOrder = Queue[Int]()
  val revrsePostOrder = Stack[Int]()

  /**
   * @return sorted graph
   */
  def sort(): List[Int] = {
    // TODO: check for cycle
    // else
    val visited = Array.ofDim[Boolean](graph.size)
    graph.getNodes.foreach(node => {
      if(! visited(node)){
        dfs(node, visited)
      }
    })
    revrsePostOrder.toList
  }

  private def dfs(node: Int, visited: Array[Boolean]): Unit = {
    visited(node) = true
    preOrder.enqueue(node)
    graph.adjNodes(node).foreach(adjNode => {
      if(!visited(adjNode)){
        dfs(adjNode, visited)
      }
    })
    postOrder.enqueue(node)
    revrsePostOrder.push(node)
  }
}
