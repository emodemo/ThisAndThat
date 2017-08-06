package com.algs4scala.graph

import scala.collection.mutable.Queue
import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.Map

/**
 * MST for undirected graph
 * </br> complexity same as Kruskal:
 * </br>space: edges
 * </br>time: edges * log(edges)
 *
 * </br> for directed graph look at minimum cost arborescence
 * @param graph the graph
 */
class MSTPrimLazy(graph: Graph) {

  /**
   * lazy Prim implementation
   * <ol>
   * <li>step 1 - Get a node and its adjacent nodes
   * <li>step 2 - Add the node to result and choose next node (one with min edge weight)
   *           among visible nodes (added on step 1, but not yet added to result)
   * <li>step 3 - Ensure visited nodes will not be revisited and go to step 1
   * </ol>
   * @return list of edge tuples(node v, node w, weight)
   */
  def mst(): List[(Int, Int, Double)] = {
    // setups
    def ordering: Ordering[Edge] = Ordering.by(e => e.weight)
    val minPQ = PriorityQueue[Edge]()(ordering.reverse)
    val primMST = Queue[Edge]()
    val visited = Array.ofDim[Boolean](graph.size)
    def visit(node: Int) = {
      visited(node) = true
      graph.adjEdges(node).foreach(edge => if(!visited(edge.to)) minPQ += edge)
    }
    // alg
    val someNode = graph.getNodes()(0)
    visit(someNode)
    while(! minPQ.isEmpty){
      val edge = minPQ.dequeue(); val from = visited(edge.from); val to = visited(edge.to)
      if(!from || !to) primMST += edge
      if(!from) visit(edge.from)
      if(!to) visit(edge.to)
    }
    primMST.map(edge => (edge.from, edge.to, edge.weight)).toList
  }

}
