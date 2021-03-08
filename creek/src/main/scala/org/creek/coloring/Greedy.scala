package org.creek.coloring

import org.creek.collection.Graph
import org.creek.coloring.Util.minColorAvailable

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Greedy {

  /** time N square */
  def color[N](graph: Graph[N]): mutable.Map[Int, ListBuffer[N]] = {
    val nodeToColor = mutable.Map[N, Int]()
    val colorToNodes = mutable.Map[Int, ListBuffer[N]]()

    // start with the head node, optionally a seed might be used... one day
    val head = graph.getNodes.head
    nodeToColor += head -> 0
    colorToNodes += 0 -> ListBuffer[N](head)

    // go trough remaining nodes and check their color suitability
    // check if already colored
    for (node <- graph.getNodes.tail if nodeToColor.contains(node) == false) {
      val minColor = minColorAvailable(graph, node, nodeToColor)
      // add the node with the respective color
      nodeToColor += node -> minColor
      colorToNodes.getOrElseUpdate(minColor, ListBuffer[N]()) += node
    }
    colorToNodes
  }
}
