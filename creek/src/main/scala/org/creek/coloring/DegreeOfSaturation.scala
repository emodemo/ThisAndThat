package org.creek.coloring

import org.creek.collection.Graph
import org.creek.coloring.Util.minColorAvailable
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object DegreeOfSaturation {

  /** time N square */
  def color[N](graph: Graph[N]): mutable.Map[Int, ListBuffer[N]] = {
    val nodeToColor = mutable.Map[N, Int]()
    val colorToNodes = mutable.Map[Int, ListBuffer[N]]()
    // 1st criterion - max saturation degree, 0 at the beginning
    // 2nd criterion - max N of degree
    // 3rd criterion - random

    // tuple3 with (node, N of Degree, saturation = 0)
    val sorted = mutable.Map[N, (Int, Int)]()
    graph.getNodes.map(node => (node, graph.adjacentNodes(node).length, 0)).
      foreach { case (node, degree, saturation) => sorted += node -> (degree, saturation) }

    // at the beginning saturation is 0 => skip from sorting
    var chosenNode = sorted.maxBy[Int] { case (_, (degree, _)) => degree }._1
    sorted -= chosenNode // drop the head

    var continue = true
    while (continue) {
      // set the color
      val minColor = minColorAvailable(graph, chosenNode, nodeToColor)
      nodeToColor += chosenNode -> minColor
      colorToNodes.getOrElseUpdate(minColor, ListBuffer[N]()) += chosenNode
      // update unvisited neighbours
      for (adjacent <- graph.adjacentNodes(chosenNode) if sorted.contains(adjacent)) {
        val (degree, saturation) = sorted(adjacent)
        sorted.update(adjacent, (degree, saturation + 1))
      }
      if (sorted.isEmpty) continue = false // break statement
      else {
        // choose next node, respecting the order of the algorithm
        chosenNode = sorted.maxBy[(Int, Int)] { case (_, (degree, saturation)) => (saturation, degree) }._1
        sorted -= chosenNode
      }
    }
    colorToNodes
  }

}
