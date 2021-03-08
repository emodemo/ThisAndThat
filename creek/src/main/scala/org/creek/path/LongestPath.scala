package org.creek.path

import org.creek.collection.{Edge, Graph}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object LongestPath {

  /** for edge-weighted directed graphs - time E + V ; space V */
  def acyclic[N](graph: Graph[N], from: N, to: N): List[Edge[N]] = {
    val minDistTo = mutable.Map[N, Double]()
    graph.getNodes.foreach(node => minDistTo += (node -> Double.NegativeInfinity))
    minDistTo += (from -> 0.0)
    // holds parent of node in the resulting path
    val edgeTo = mutable.Map[N, Edge[N]]()
    edgeTo += (from -> null)

    val sorted = Topological.sort[N](graph) match {
      case Right(list) => list
      // TODO set return type as Either with error possibility
      case Left(_) => List[N]()
    }

    for{
      node <- sorted
      edge <- graph.adjacentEdges(node)
    } yield relaxEdge(edge, edgeTo, minDistTo)

    // TODO handle better returning => e.g. error or options or positive infinity
    if(edgeTo.contains(to) == false) return List[Edge[N]]()
    formatResult[N](from, to, edgeTo)
  }

  private def relaxEdge[N](edge: Edge[N],
                           edgeTo: mutable.Map[N, Edge[N]],
                           minDistTo: mutable.Map[N, Double]): Unit = {
    val (from, to, weight) = (edge.from, edge.to, edge.weight)
    if (minDistTo(to) <= minDistTo(from) + weight) {
      minDistTo(to) = minDistTo(from) + weight
      edgeTo += to -> edge
    }
  }

  private def formatResult[N](from: N, to: N, edgeTo: mutable.Map[N, Edge[N]]): List[Edge[N]] ={
    // go backwards starting from the end node
    val result = ListBuffer[Edge[N]]()
    var tmp = edgeTo(to)
    while(tmp != null){
      result += tmp
      tmp = edgeTo(tmp.from)
    }
    result.toList.reverse
  }

}
