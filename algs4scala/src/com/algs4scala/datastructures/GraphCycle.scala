package com.algs4scala.datastructures

class GraphCycle {

  private var cycle = false

  def hasCycle(graph: Graph): Boolean = {
    cycle = false
    val visited = Array.ofDim[Boolean](graph.size)
    for(node <- graph.getNodes()){
      if(! visited(node)){
        dfs(graph, node, node, visited)
      }
    }
    cycle
  }


  // traverse the graph starting from a given node to a given node
  // currently it goes thru all the graph. May be optimized,
  // but logic is easier to read this way
  private def dfs(graph: Graph, from: Int, to: Int, visited: Array[Boolean]): Unit = {
    visited(from) = true
    graph.adj(from).foreach(adjNode => {
      if(!visited(adjNode)){
        dfs(graph, adjNode, to, visited)
      }
      else if(adjNode == to){
        cycle = true // has cycle
      }
    })
  }
}