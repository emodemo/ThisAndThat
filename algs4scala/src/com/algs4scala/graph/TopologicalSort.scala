package com.algs4scala.graph

import scala.collection.mutable.Queue
import scala.collection.mutable.Stack

/**
 * Topological on graph
 * @param graph to surch in
 */
class TopologicalSort(graph: Graph) {

  private val preOrder = Queue[Int]()
  private val inOrder = Queue[Int]()
  private val postOrder = Queue[Int]()
  private val revrsePostOrder = Stack[Int]()

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
