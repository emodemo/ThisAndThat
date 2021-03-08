package org.creek.path

import org.creek.collection.{Edge, Graph}
import scala.util.control.Breaks._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object SP2 {

  // TODO: refactor to remove too much space usage
  /** for edge-weighted directed graphs - time E log V ; space V */
  def dijkstra[N](graph: Graph[N], from: N, to: N): List[Edge[N]] = {
    val resultEdges = ListBuffer[Edge[N]]()
    val distanceFormStart = mutable.Map[N, Double]()   // node maxdistance
    val eligible = mutable.Map[N, (Edge[N], Double)]() // instead of MinPQ, node, edge, maxdistance, for sorting
    val visited = ListBuffer[N]() += from              // no need of visited
    for (edge <- graph.adjacentEdges(from)) {
      eligible += (edge.to -> (edge, edge.weight))
      distanceFormStart += (edge.to -> edge.weight)
    }
    while (eligible.isEmpty == false) {
      val (minNode, (minEdge, _)) = eligible.minBy[Double] { case (_, (_, weight)) => weight }
      eligible -= minNode
      resultEdges += minEdge
      visited += minNode

      for (edge <- graph.adjacentEdges(minNode)) {
        breakable {
          if(visited.contains(edge.to)) break
          val distTo = distanceFormStart.getOrElse(edge.to, 0.0)
          val distFrom = distanceFormStart.getOrElse(edge.from, 0.0) + edge.weight
          eligible.get(edge.to) match {
            case Some((_, _)) => if (distTo > distFrom) {
             distanceFormStart += (edge.to -> distFrom)
             eligible += (edge.to -> (edge, distFrom))
            }
            case None =>
              distanceFormStart += (edge.to -> distFrom)
              eligible += (edge.to -> (edge, distFrom))
          }
        }
      }
    }
    resultEdges.toList
  }
}
