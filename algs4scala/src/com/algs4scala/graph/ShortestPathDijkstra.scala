package com.algs4scala.graph

import com.algs4scala.datastructures.IndexedPriorityQueue
import scala.collection.mutable.ListBuffer

/**
 * Not optimal solution as it calculates all path from a start point, not just the requested one
 *
 * 1 - set the distance from a start point to all other points to Infintiy
 * 2 - set the distance start -> start to 0 and add the starting point to a min priority queue
 * 3 - get the minPoint from queue,
 * 4 - take adjacent edges of minPoint and relax it. If an edge is relaxed add its end to the queue,
 * so that its edges are relaxed too.
 * 5 - take the node leading to another to be the one from an edge relaxation
 * 6 - calculate the path from->to by going backward starting from the "to" node
 * and getting nodes that lead to it, until the "from" node is reach
 *
 * @param graph the Graph
 */
class ShortestPathDijkstra(graph: Graph) {

  // total of the weight so far (distance from->each other point)
  private  val shortestDistanceTo = Array.fill(graph.getNodes().size)(Double.PositiveInfinity)
  // paths
  private  val parentNodeOnPath = Array.ofDim[Edge](graph.getNodes().size)
  // tmp holder
  private  val indexedMinPQ : IndexedPriorityQueue[Double] = new IndexedPriorityQueue()(Ordering.Double.reverse)

  def shortestPath(from: Int, to: Int): List[Edge] = {
    // reset tmp holders
    for(i <- 0 until graph.getNodes().size){
      shortestDistanceTo(i) = Double.PositiveInfinity
      parentNodeOnPath(i) = null
      indexedMinPQ.clear()
    }
    // alg
    // distance from -> from is 0.0
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
    graph.adjEdges(from).foreach(edge => relaxEdge(edge))
  }

  /**
   * take an edge, if distance start->to isLarger than distance start->from + weight
   * => we can update start->to with lesser value (namely the distance start->from + weight)
   * , then add/update the newly updated node (to) to relax its edges (add/update it to the queue)
   */
  private def relaxEdge(edge: Edge): Unit = {
    val to = edge.to
    if(shortestDistanceTo(to) > shortestDistanceTo(edge.from) + edge.weight){
      shortestDistanceTo(to) = shortestDistanceTo(edge.from) + edge.weight
      parentNodeOnPath(to) = edge
      indexedMinPQ.enqueue(to, shortestDistanceTo(to))
    }
  }
}