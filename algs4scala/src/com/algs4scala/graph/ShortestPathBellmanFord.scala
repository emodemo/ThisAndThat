package com.algs4scala.graph

import scala.collection.mutable.Queue
import scala.collection.mutable.ListBuffer

// not quite different than Dijkstra. Checks for negative cycle; uses queue instead of minPQ
class ShortestPathBellmanFord(graph: Graph) {

 // total of the weight so far (distance from->each other point)
  private  val shortestDistanceTo = Array.fill(graph.getNodes().size)(Double.PositiveInfinity)
  // paths
  private  val parentNodeOnPath = Array.ofDim[Edge](graph.getNodes().size)
  // tmpQueue
  private val queue = Queue[Int]()

  def shortestPath(from: Int, to: Int): List[Edge] = {
    // reset tmp holders
    for(i <- 0 until graph.getNodes().size){
      shortestDistanceTo(i) = Double.PositiveInfinity
      parentNodeOnPath(i) = null
      queue.clear()
    }
    // Bellman-Ford alg
    // TODO: in fact the dfs is done twice. It can be optimized if the cycle detection is calculated while sorting
    // TODO: check for cycle
      // else
      // distance from -> from is 0.0
       shortestDistanceTo(from) = 0.0
       queue.enqueue(from)
       while(! queue.isEmpty){
          relaxGraph(queue.dequeue)
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
      queue.enqueue(to)
    }
  }
}