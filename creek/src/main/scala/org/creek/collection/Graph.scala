package org.creek.collection

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// TODO: make the API with Option for getters, and Try for setters
// TODO: update to better API // add/delete edges, updateNode ... and so on
// TODO: add notes on adding an empty edge
// TODO: scalaDocs: N must implement HashCode and Equals
// TODO: reverse graph // meaningful for directed graph
// TODO: Kosaraju-Sharir for Strongly connected components - Sedgewick
// TODO: add ordering for keys, and use TreeMap??
// Or this one ??? Gabow's algorithm (aka Cheriyan-Mehlhorn algorithm)
trait Graph[N]{
  def getNodes : List[N]
  def addEdge(from: N, to: N, weight: Double = 0.0)
  def adjacentNodes(node: N) : List[N]
  def adjacentEdges(node: N) : List[Edge[N]]
  def size : Int
}

object Graph {
  def apply[N](kind: GraphType) : Graph[N] = {
    kind match {
      case Undirected => new UndirectedGraph[N]
      case Directed => new DirectedGraph[N]
    }
  }
}

sealed trait GraphType
case object Undirected extends GraphType
case object Directed extends GraphType

class Edge[N] private[collection](val from: N, val to: N, val weight: Double = 0.0){
  override def toString: String = s"$from - $to : $weight"
}

private abstract class AbstractGraph[N] extends Graph[N]{

  protected val nodes: mutable.Map[N, ListBuffer[Edge[N]]] = mutable.Map[N, ListBuffer[Edge[N]]]()

  protected def addNode(node: N): Unit = if(nodes.contains(node) == false) nodes += (node -> ListBuffer[Edge[N]]())
  override def getNodes: List[N] = nodes.keySet.toList
  override def adjacentNodes(node: N): List[N] = adjacentEdges(node).map(edge => edge.to)
  override def adjacentEdges(node: N): List[Edge[N]] = {
    nodes.get(node) match {
      case Some(value) => value.toList
      case None => List[Edge[N]]()
    }
  }
  override def size: Int = nodes.size
}

private class UndirectedGraph[N] extends AbstractGraph[N] {
  override def addEdge(from: N, to: N, weight: Double = 0.0): Unit = {
    if(from == null) return
    addNode(from)
    if(to == null) return // no edge, just a single node
    addNode(to)
    nodes.getOrElseUpdate(from, ListBuffer[Edge[N]]()) += new Edge[N](from, to, weight)
    nodes.getOrElseUpdate(to, ListBuffer[Edge[N]]()) += new Edge[N](to, from, weight)
  }
}

private class DirectedGraph[N] extends AbstractGraph[N] {
  override def addEdge(from: N, to: N, weight: Double = 0.0): Unit = {
    if(from == null) return
    addNode(from)
    if(to == null) return // no edge, just a single node
    addNode(to)
    nodes.getOrElseUpdate(from, ListBuffer[Edge[N]]()) += new Edge[N](from, to, weight)
  }
}
