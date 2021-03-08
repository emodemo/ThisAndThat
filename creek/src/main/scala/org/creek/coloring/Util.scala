package org.creek.coloring

import org.creek.collection.Graph

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

private[coloring] object Util {

  /** find minimum available color looking at neighbours */
  def minColorAvailable[N](graph: Graph[N], node: N, nodeToColor: mutable.Map[N, Int]): Int = {
    val coloredAdjacent = ListBuffer[Int]()
    // get colors of all adjacent
    for (adjacent <- graph.adjacentNodes(node)) {
      val color = nodeToColor.getOrElse(adjacent, -1)
      if (color > -1) coloredAdjacent += color
    }
    // find min available color
    var minColor = 0
    val sorted = coloredAdjacent.sorted
    for (color <- sorted) {
      if (minColor >= color) minColor = color + 1
      else if (minColor < color) return minColor
    }
    minColor
  }
}
