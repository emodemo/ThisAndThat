package com.algs4scala.datastructures

import scala.collection.mutable.ListBuffer

class GraphDFS(graph: Graph) {

  // 1 - traverse the graph starting from a given node
  // 2 - if the destination node was there, start from it,
  // go thru its parents until the start node is reached
  def dfsPath(from: Int, to: Int): List[Int] = {
    val visited = Array.ofDim[Boolean](graph.size)
    val parentOnPath = Array.ofDim[Int](graph.size)
    val buff = ListBuffer[Int]()
    // 1
    dfs(from, visited, parentOnPath)
    // 2
    if(!visited(to)){
      buff.toList
    }
    else{
      var tmp = to
      while(tmp != from){
        buff += tmp
        tmp = parentOnPath(tmp)
      }
      buff += from
      buff.reverse.toList
    }
  }

  // traverse the graph starting from a given node
  private def dfs(node: Int, visited: Array[Boolean], parentOnPath: Array[Int]): Unit = {
    visited(node) = true
    graph.adj(node).foreach(adjNode => {
      if(!visited(adjNode)){
        parentOnPath(adjNode) = node
        dfs(adjNode, visited, parentOnPath)
      }
    })
  }

}