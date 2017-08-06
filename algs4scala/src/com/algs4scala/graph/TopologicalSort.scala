package com.algs4scala.graph

import scala.collection.mutable.Queue
import scala.collection.mutable.Stack

/**
 * Topological sort on graph. </br>
 * In fact it is DFS's reverse post-orderinf
 *
 * @param graph to search in
 */
class TopologicalSort(graph: Graph) {

  private val preOrder = Queue[Int]()
  private val inOrder = Queue[Int]()
  private val postOrder = Queue[Int]()

  /**
   * @return sorted graph or empty if cycle is detected
   */
  def sort(): List[Int] = {
    // TODO: in fact the dfs is done twice. It can be optimized if the cycle detection is calculated while sorting
    // TODO: check for cycle
      // else
      val visited = Array.ofDim[Boolean](graph.size)
      graph.getNodes.foreach(node => {
        if(! visited(node)){
          dfs(node, visited)
        }
      })
    postOrder.reverse.toList
  }

  private def dfs(node: Int, visited: Array[Boolean]): Unit = {
    preOrder.enqueue(node)
    visited(node) = true
    graph.adjNodes(node).foreach(adjNode => {
      if(!visited(adjNode)){
        dfs(adjNode, visited)
      }
    })
    postOrder.enqueue(node)
  }
}
