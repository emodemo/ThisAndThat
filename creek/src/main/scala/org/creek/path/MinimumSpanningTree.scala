package org.creek.path

import org.creek.collection.{Edge, Graph}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// other possibilities are lazy Prim (E log E), Kruskal (E log E) and Chazelle (almost E)
object MinimumSpanningTree {

  /** time E log V; space V */
  def eagerPrim[N](graph: Graph[N]): List[Edge[N]] = {
    // BASE: BFS + minPQ(weight from tree), minDistTo(tree), edgeTo(on path) and relaxation
    val visited = ListBuffer[N]()
    val resultEdges = ListBuffer[Edge[N]]()
    // for minimum spanning forest, otherwise > val node = graph.getNodes.head; scan[N](node)
    graph.getNodes.foreach(node => if(visited.contains(node) == false) scan(node))

    def scan(node: N): Unit = {
      val eligible = mutable.Map[N, Edge[N]]()
      visited += node
      graph.adjacentEdges(node).foreach(edge => eligible += (edge.to -> edge))

      while(eligible.isEmpty == false){
        // find minDist edge from eligible and add it to the result
        // instead of minPQ
        val (minNode, minEdge) = eligible.minBy[Double]{case (_, edge) => edge.weight}
        eligible -= minNode
        visited += minNode
        resultEdges += minEdge

        // go through edges and update eligible if path to new node or shorter to already eligible node is found
        for(edge <- graph.adjacentEdges(minNode)){
          if(visited.contains(edge.to) == false){ // skip visited nodes
            eligible.get(edge.to) match {
              case Some(eligibleEdge) => if(eligibleEdge.weight > edge.weight) eligible += edge.to -> edge // update
              case None => eligible += edge.to -> edge // or add
            }
          }
        }
      }
    }

    resultEdges.toList
  }
}
