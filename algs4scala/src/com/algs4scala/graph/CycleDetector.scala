package com.algs4scala.graph

import scala.collection.mutable.ListBuffer
import scala.util.control.Breaks
import scala.collection.mutable.Stack

class CycleDetector(graph: Graph) {

  def detectCycle(): List[Int] = {
    val cycle = ListBuffer[Int]()
    val visited = Array.ofDim[Boolean](graph.size)
    val onStack = Array.ofDim[Boolean](graph.size)
    val parentOnPath = Array.ofDim[Int](graph.size)
    graph.getNodes().foreach(node => if(! visited(node)) dfs(node, visited, onStack, parentOnPath, cycle))
    cycle.reverse.toList
  }

  // TODO: HAS ERROR - SEE BELLMAN-FORD
  // either a topological order or a cycle is hit
  private def dfs(node: Int, visited: Array[Boolean], onStack: Array[Boolean], parentOnPath: Array[Int], cycle: ListBuffer[Int]): Unit = {
    visited(node) = true
    onStack(node) = true
    Breaks.breakable{
      graph.adjEdges(node).foreach(adjEdge => {
        // short circuit if directed cycle found
        if(!cycle.isEmpty) Breaks.break()
        // found new node => dfs
        else if(! visited(adjEdge.to)){
          parentOnPath(adjEdge.to) = node
          dfs(adjEdge.to, visited, onStack, parentOnPath, cycle)
        }
        // directed cycle found
        else if(onStack(adjEdge.to)){
          var tmpNode = adjEdge.from
          while(tmpNode != adjEdge.to){
            cycle += tmpNode
            tmpNode = parentOnPath(tmpNode)
          }
          cycle += tmpNode
          Breaks.break()
        }
      })
    }
  }

}