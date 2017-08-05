package com.algs4scala.graph

import com.algs4scala.datastructures.IndexedPriorityQueue
import scala.collection.mutable.ListBuffer

/**
 * Not optimal solution as it calculates all path from a start point, not just the requested one
 */
class GraphDijkstraShortestPath(graph: Graph) {

  // total of the weight so far
  private  val shortestDistanceTo = Array.fill(graph.getNodes().size)(Double.PositiveInfinity)
  // paths
  private  val parentNodeOnPath = Array.ofDim[Edge](graph.getNodes().size)
  // tmp holder
  private  val indexedMinPQ : IndexedPriorityQueue[Double] = new IndexedPriorityQueue()(Ordering.Double.reverse)

  /**
   *
   */
  def shortestPath(from: Int, to: Int): List[Edge] = {
    // alg
    shortestDistanceTo(from) = 0.0
    indexedMinPQ.enqueue(from, shortestDistanceTo(from))
    while(! indexedMinPQ.isEmpty()){
       relaxGraph(indexedMinPQ.dequeue()._1)
    }
    path(to)
  }

  private def path(to: Int): List[Edge] = {
     val result = ListBuffer[Edge]()
     var tmpEdge = parentNodeOnPath(to)
     while(tmpEdge != null){
       result += (tmpEdge)
       tmpEdge = parentNodeOnPath(tmpEdge.from)
     }
     result.toList.reverse
  }

  private def relaxGraph(from: Int): Unit = {
    graph.adjEdges(from).foreach(edge => relax(edge, from))
  }

  private def relax(edge: Edge, from: Int): Unit = {
    val to = edge.to
    if(shortestDistanceTo(to) > shortestDistanceTo(from) + edge.weight){
      shortestDistanceTo(to) = shortestDistanceTo(from) + edge.weight
      parentNodeOnPath(to) = edge
      indexedMinPQ.enqueue(to, shortestDistanceTo(to))
    }
  }
}
