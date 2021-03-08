package org.creek.path

import org.creek.collection.Graph

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Topological {

  val errorMessage = "Cycle has been detected."

  def sort[N](graph: Graph[N]): Either[(String, List[N]), List[N]] = {
    val visited = ListBuffer[N]() // same as preOrder
    val postOrder = ListBuffer[N]()
    val onCurrentPath = ListBuffer[N]()
    val parentOnPath = mutable.Map[N, N]()
    val cycle = ListBuffer[N]()

    for(node <- graph.getNodes){
      if(cycle.isEmpty && visited.contains(node) == false)
        dfs(graph, node, visited, onCurrentPath, parentOnPath, cycle, postOrder)
    }
    if(cycle.isEmpty) Right(postOrder.reverse.toList)
    else Left(errorMessage, cycle.reverse.toList)
  }

  private def dfs[N](graph: Graph[N],
                     node: N,
                     visited: ListBuffer[N],
                     onCurrentPath: ListBuffer[N],
                     parentOnPath: mutable.Map[N, N],
                     cycle: ListBuffer[N],
                     postOrder: ListBuffer[N]): Unit ={
    onCurrentPath += node
    visited += node
    for(adjNode <- graph.adjacentNodes(node)){
      if(cycle.isEmpty == false) return
      // if not visited go forward, the sort part
      if(visited.contains(adjNode) == false){
        parentOnPath += adjNode -> node
        dfs(graph, adjNode, visited, onCurrentPath, parentOnPath, cycle, postOrder)
      }
      // else if visited and already on the current path => cycle, the check cycle part
      else if(onCurrentPath.contains(adjNode)){
        parentOnPath += adjNode -> node
        var nodeInCycle = node
        cycle += adjNode
        while((nodeInCycle == adjNode) == false){
          cycle += nodeInCycle
          nodeInCycle = parentOnPath(nodeInCycle)
        }
        cycle += nodeInCycle
        return // stop on cycle
      }
    }
    postOrder += node
    onCurrentPath -= node
  }
}
