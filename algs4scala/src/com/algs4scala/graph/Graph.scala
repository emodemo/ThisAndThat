package com.algs4scala.graph

import scala.collection.mutable.ListBuffer

/**
 * Adjacency list graph implementation for weighted, directed graph
 *
 * @param size the n of nodes in the graph
 */
class Graph(val size: Int) {

  private val adjacents = Array.ofDim[ListBuffer[Edge]](size)
  // list of all nodes
  private val nodes = ListBuffer[Int]()
  private val edges = ListBuffer[Edge]()

  /**
   * Add edge
   * @param from from
   * @param to to
   * @param weight weight
   */
  def addEdge(from: Int, to: Int, weight: Double): Unit = {
    var adj = adjacents(from)
    if(adj == null){
      adj = new ListBuffer[Edge]()
      adjacents(from) = adj
    }
    val edge = new Edge(from, to, weight)
    adj += edge
    // add to edge
    edges += edge
    // add to nodes
    addNode(from)
    addNode(to)
  }

  /**
   * Get adjacent nodes
   * @param node node
   * @return adjacent nodes
   */
  def adjNodes(node: Int): List[Int] = {
    val tmpNodes = adjacents(node)
    var res = List[Int]()
    if(tmpNodes != null){
      res = adjacents(node).map(edge => edge.to).toList
    }
    res
  }

  /**
   * Get adjacent edges
   * @param node node
   * @return adjacent edges
   */
  def adjEdges(node: Int): List[Edge] = {
    val tmpNodes = adjacents(node)
    var res = List[Edge]()
    if(tmpNodes != null){
      res = adjacents(node).toList
    }
    res
  }

  /**
   *  Get all nodes
   *  @return nodes
   */
  def getNodes(): List[Int] = nodes.toList

  /**
   *  Get all edges
   *  @return edges
   */
  def getEdges(): List[Edge] = edges.toList

  private def addNode(node: Int) = {
    if(!nodes.contains(node)){
      nodes += node
    }
  }

  /**
   * @param from from
   * @param to to
   * @param weight weight
   */
  class Edge(val from: Int, val to: Int, val weight: Double){
    override def toString(): String =  from + " - " + to + " : " + weight
  }
}
