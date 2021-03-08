package org.creek.path

import org.creek.collection.{Edge, Graph}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object ShortestPath {

  val errorMessage = "Cycle has been detected."

  /** for undirected unweighted graph , time V + E for worst case */
  def bfs[N](graph: Graph[N], from: N, to: N): List[N] = {
    // one map instead of visited[] and parentOnPath[]
    val visitedNodesParents = mutable.Map[N, N]()
    // 1 - traverse the graph starting from a given node
    bfs[N](from, graph, visitedNodesParents)
    // 2 - check if destination node was met
    if (visitedNodesParents.contains(to) == false) return List[N]()
    // 3 - go through parents until starting node is reached
    val result = ListBuffer[N]()
    var tmpNode = to
    while (tmpNode.equals(from) == false) {
      result += tmpNode
      tmpNode = visitedNodesParents(tmpNode)
    }
    result += from
    result.reverse.toList
  }

  private def bfs[N](from: N, g: Graph[N], visitedNodesParents: mutable.Map[N, N]): Unit = {
    val queue = mutable.Queue[N]()
    queue.enqueue(from)
    visitedNodesParents += (from -> from) // from -> null is not acceptable
    while (queue.isEmpty == false) {
      val tmpNode = queue.dequeue()
      g.adjacentNodes(tmpNode).foreach(
        adjNode => {
          if (visitedNodesParents.contains(adjNode) == false) {
            visitedNodesParents += (adjNode -> tmpNode)
            queue.enqueue(adjNode)
          }
        }
      )
    }
  }

  // TODO: refactor to remove obsolete code
  /** for edge-weighted directed graphs - time E log V ; space V */
  def dijkstra[N](graph: Graph[N], from: N, to: N): List[Edge[N]] = {
    // BASE: BFS + minPQ(weight from startNode), distTo(startNode), edgeTo(on path) and relaxation
    // order nodes by min distance from starting node. alternative impl is to create nodes with comparator
    val minPq = mutable.PriorityQueue.empty[(Double, N)](Ordering.by[(Double, N), Double](_._1).reverse)
    // order nodes by min distance from starting node -> default is Infinity, except for starting point which is 0.0
    val minDistTo = mutable.Map[N, Double]()
    graph.getNodes.foreach(node => minDistTo += (node -> Double.PositiveInfinity))
    minDistTo += (from -> 0.0)
    // holds parent of node in the resulting path
    val edgeTo = mutable.Map[N, Edge[N]]()
    edgeTo += (from -> null)

    // take adjacent edges of node with min weight and relax it.
    // if an edge is relaxed add its end to the queue, to relax its edges too.
    minPq.enqueue((minDistTo(from), from))
    while (minPq.isEmpty == false) {
      val node = minPq.dequeue()._2
      for (edge <- graph.adjacentEdges(node))
        yield relaxEdge[N](edge, edgeTo, minDistTo, minPq)
    }

    formatResult[N](from, to, edgeTo)
  }

  private def relaxEdge[N](edge: Edge[N],
                           edgeTo: mutable.Map[N, Edge[N]],
                           minDistTo: mutable.Map[N, Double],
                           minPq: mutable.PriorityQueue[(Double, N)]): Unit = {

    val (from, to, weight) = (edge.from, edge.to, edge.weight)
    if (minDistTo(to) > minDistTo(from) + weight) {
      minDistTo(to) = minDistTo(from) + weight
      edgeTo += (to -> edge)
      minPq.enqueue((minDistTo(to), to))
    }
  }

  // another possibility is Floyd Warshall - time V^3
  /** for edge-negative-weighted directed graphs - time V E ; space V */
  def bellmanFord[N](graph: Graph[N], from: N, to: N): Either[(String, List[Edge[N]]), List[Edge[N]]] = {
    // order nodes by min distance from starting node -> default is Infinity, except for starting point which is 0.0
    val minDistTo = mutable.Map[N, Double]()
    graph.getNodes.foreach(node => minDistTo += (node -> Double.PositiveInfinity))
    minDistTo += (from -> 0.0)
    // holds parent of node in the resulting path
    val edgeTo = mutable.Map[N, Edge[N]]()
    edgeTo += (from -> null)

    // relax V*E times, the two foreach account for E times
    for {
      _ <- 1 to graph.getNodes.length
      node <- graph.getNodes // do not have graph.getAllEdges
      edge <- graph.adjacentEdges(node)
    } yield relaxEdge[N](edge, edgeTo, minDistTo)

    val cycle = ListBuffer[Edge[N]]()
    // TODO: gather all cycles
    // check for cycle - E times
    for {
      node <- graph.getNodes // do not have graph.getAllEdges
      edge <- graph.adjacentEdges(node)
    } yield checkForCycle(edge)

    def checkForCycle(edge: Edge[N]): Unit = {
      if (cycle.isEmpty == false) return
      val (from, to, weight) = (edge.from, edge.to, edge.weight)
      if (minDistTo(to) > minDistTo(from) + weight) { // check for cycle, the rest is getting the actual cycle
        cycle += edge
        var nodeInCycle = from
        while ((nodeInCycle == to) == false) {
          val edgeOnCycle = edgeTo(nodeInCycle)
          cycle += edgeOnCycle
          nodeInCycle = edgeOnCycle.from
        }
      }
    }

    if (cycle.isEmpty) Right(formatResult[N](from, to, edgeTo))
    else Left(errorMessage, cycle.toList)
  }

  private def relaxEdge[N](edge: Edge[N],
                           edgeTo: mutable.Map[N, Edge[N]],
                           minDistTo: mutable.Map[N, Double]): Unit = {
    val (from, to, weight) = (edge.from, edge.to, edge.weight)
    if (minDistTo(to) >= minDistTo(from) + weight) {
      minDistTo(to) = minDistTo(from) + weight
      edgeTo += to -> edge
    }
  }

  private def formatResult[N](from: N, to: N, edgeTo: mutable.Map[N, Edge[N]]): List[Edge[N]] = {
    // go backwards starting from the end node
    val result = ListBuffer[Edge[N]]()
    var tmp = edgeTo(to)
    while (tmp != null) {
      result += tmp
      tmp = edgeTo(tmp.from)
    }
    result.toList.reverse
  }

  /** for edge-weighted directed graphs - time E + V ; space V */
  def acyclic[N](graph: Graph[N], from: N, to: N): List[Edge[N]] = {
    val minDistTo = mutable.Map[N, Double]()
    graph.getNodes.foreach(node => minDistTo += (node -> Double.PositiveInfinity))
    minDistTo += (from -> 0.0)
    // holds parent of node in the resulting path
    val edgeTo = mutable.Map[N, Edge[N]]()
    edgeTo += (from -> null)

    val sorted = Topological.sort[N](graph) match {
      case Right(list) => list
      // TODO set return type as Either with error possibility
      case Left(_) => List[N]()
    }

    for {
      node <- sorted
      edge <- graph.adjacentEdges(node)
    } yield relaxEdge(edge, edgeTo, minDistTo)

    // TODO handle better returning => e.g. error or options or positive infinity
    if (edgeTo.contains(to) == false) return List[Edge[N]]()
    formatResult[N](from, to, edgeTo)
  }
}
