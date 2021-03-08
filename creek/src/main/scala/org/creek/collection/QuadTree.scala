package org.creek.collection

import scala.collection.mutable.ListBuffer

class QuadTree[K, V](implicit ordering: Ordering[K]) {

  private var root: Node = _

  def insert(x: K, y: K, value: V): Unit = {
    root = doInsert(root, new Node(x, y, value))
    root.toString
  }

  private def doInsert(parent: Node, node: Node): Node = {
    if(parent == null) return node
    else if(ordering.lt(node.x, parent.x) && ordering.lt(node.y, parent.y)) parent.SW = doInsert(parent.SW, node)
    else if(ordering.lt(node.x, parent.x) && !ordering.lt(node.y, parent.y)) parent.NW = doInsert(parent.NW, node)
    else if(!ordering.lt(node.x, parent.x) && ordering.lt(node.y, parent.y)) parent.SE = doInsert(parent.SE, node)
    else if(!ordering.lt(node.x, parent.x) && !ordering.lt(node.y, parent.y)) parent.NE = doInsert(parent.NE, node)
    parent
  }

  /** min inclusive , max exclusive */
  def query(xMin: K, xMax: K, yMin: K, yMax: K): List[V] = {
    if(ordering.lt(xMax, xMin) || ordering.lt(yMax, yMin)) return List[V]()
    val buf = ListBuffer[V]()
    doQuery(xMin, xMax, yMin, yMax, root, buf)
    buf.toList
  }

  private def doQuery(xMin: K, xMax: K, yMin: K, yMax: K, parent: Node, buf: ListBuffer[V]): Unit = {
    if(parent == null) return
    if(contains(xMin, xMax, yMin, yMax, parent)) buf += parent.value
    if(ordering.lt(xMin, parent.x) && ordering.lt(yMin, parent.y)) doQuery(xMin, xMax, yMin, yMax, parent.SW, buf)
    if(ordering.lt(xMin, parent.x) && !ordering.lt(yMax, parent.y)) doQuery(xMin, xMax, yMin, yMax, parent.NW, buf)
    if(!ordering.lt(xMax, parent.x) && ordering.lt(yMin, parent.y)) doQuery(xMin, xMax, yMin, yMax, parent.SE, buf)
    if(!ordering.lt(xMax, parent.x) && !ordering.lt(yMax, parent.y)) doQuery(xMin, xMax, yMin, yMax, parent.NE, buf)
  }

  private def contains(xMin: K, xMax: K, yMin: K, yMax: K, node: Node): Boolean = {
    if(ordering.lteq(xMin, node.x) && ordering.gt(xMax, node.x) &&
       ordering.lteq(yMin, node.y) && ordering.gt(yMax, node.y)) true
    else false
  }

  private class Node(val x: K, val y: K, val value: V){
    var SW: Node = _
    var SE: Node = _
    var NW: Node = _
    var NE: Node = _
    override def toString: String = {
      var children = ""
      children = children concat (if (SW == null) "{ - } " else "{" + SW.x + "-" + SW.y + "} ")
      children = children concat (if (SE == null) "{ - } " else "{" + SE.x + "-" + SE.y + "} ")
      children = children concat (if (NW == null) "{ - } " else "{" + NW.x + "-" + NW.y + "} ")
      children = children concat (if (NE == null) "{ - }" else "{" + NE.x + "-" + NE.y + "}")
      s"$x - $y : $value {$children}"
    }
  }
}
