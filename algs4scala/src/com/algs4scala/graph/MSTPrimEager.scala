package com.algs4scala.graph

import com.algs4scala.datastructures.IndexedPriorityQueue


/**
 * MST for undirected graph
 * space: nodes
 * time: edges * log(nodes)
 *
 * @param graph the graph
 */
class MSTPrimEager(graph: Graph) {

  /**
   * <ol>
   * <li>step 1 - Get a node and its adjacent edges
   * <li>step 2 - For each not-yet visited adjacent node see if connecting edge provide a shorter path
   *               => update the distance to the node, its connecting edge on the mst, and set it to be visited in the indexedMinPQ
   * <li>step 3 - Get the next node with minimum weight and go to step 1
   * </ol>
   * @return list of edge tuples(node v, node w, weight)
   */
  def mst(): List[(Int, Int, Double)] = {
    // setup
    val visited = Array.ofDim[Boolean](graph.size)
    val shortestDistanceTo = Array.fill(graph.getNodes().size)(Double.PositiveInfinity)
    val parentNodeOnPath = Array.ofDim[Edge](graph.getNodes().size)
    val indexedMinPQ : IndexedPriorityQueue[Double] = new IndexedPriorityQueue()(Ordering.Double)
    def visit(node: Int) = {
      visited(node) = true
      graph.adjEdges(node).foreach(edge => {
        // if edge is eligible && edge is new best connection from tree to "to" node
        if(!visited(edge.to) && edge.weight < shortestDistanceTo(edge.to)){
          shortestDistanceTo(edge.to) = edge.weight
          parentNodeOnPath(edge.to) = edge
          indexedMinPQ.enqueue(edge.to, edge.weight)
        }
      })
    }
    // alg
    // distance from -> from is 0.0
    val someNode = graph.getNodes()(0)
    shortestDistanceTo(someNode) = 0.0
    indexedMinPQ.enqueue(someNode, shortestDistanceTo(someNode))
    while(! indexedMinPQ.isEmpty){
      visit(indexedMinPQ.dequeue()._1)
    }
    parentNodeOnPath.filter(edge => edge != null).map(edge => (edge.from, edge.to, edge.weight)).toList
  }

}
