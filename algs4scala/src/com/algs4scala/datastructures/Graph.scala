package com.algs4scala.datastructures

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

  def addEdge(from: Int, to: Int, weight: Int): Unit = {
    var adj = adjacents(from)
    if(adj == null){
      adj = new ListBuffer[Edge]()
      adjacents(from) = adj
    }
    adj += new Edge(from, to, weight)
    // add to nodes
    addNode(from)
    addNode(to)
  }

  def adj(node: Int): List[Int] = {
    val tmpNodes = adjacents(node)
    var res = List[Int]()
    if(tmpNodes != null){
      res = adjacents(node).map(edge => edge.to).toList
    }
    res
  }

  def getNodes(): List[Int] = nodes.toList

  private def addNode(node: Int){
    if(!nodes.contains(node)){
      nodes += node
    }
  }

  private class Edge(val from: Int, val to: Int, val weight: Int) {
    override def toString(): String =  from + " - " + to + " : " + weight
  }
}