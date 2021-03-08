package org.creek.collection

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.math.pow
import scala.util.control.Breaks.{break, breakable}

 // TODO: add logic for updating existing node's value
// TODO: incorrect API as ordering for key, does not match the actual key type...

class PointTree[K, V](dimensions: Int)(implicit ordering: Ordering[K]) {

  private var root: Node = _

  def insert(key: Array[K], value: V): Unit = {
    if(key == null || key.length == dimensions == false) return
    val node = new Node(key, Some(value))
    if (root == null) root = node
    else doInsert(root, node)
  }

  // if there is no node on the expected place => fill it, else go deeper
  @tailrec
  private def doInsert(parent: Node, node: Node): Unit = {
    val direction = getDirection(parent, node)
    if (parent.children(direction) == null) parent.children(direction) = node
    else doInsert(parent.children(direction), node)
  }

  // get the parent's NDbox/quadrant where child should go
  // for 1D 0|1, for 2D 2|3 , for 3D 2|3 - 6|7
  //                    0|1          0|1 - 4|5
  // FIXME: does not work for 4+ dimension
  private def getDirection(parent: Node, node: Node): Int = {
    var boxId = 0
    for (i <- node.key.indices) {
      if (ordering.gt(node.key(i), parent.key(i))) {
        if(i < 1) boxId += i + 1
        else boxId += i + i
      }
    }
    boxId
  }

  def find(key: Array[K]): Option[V] = {
    if(root == null || key == null || key.length == dimensions == false) None
    else doFind(root, key)
  }

  @tailrec
  private def doFind(node: Node, key: Array[K]): Option[V] = {
    if(node.key.sameElements(key)) return Some(node.value.get)
    val childNode = node.children(getDirection(node, new Node(key, None)))
    if(childNode == null) return None
    doFind(childNode, key)
  }

  def query(query: Query): Option[List[V]] = {
    if(root == null || query == null || query.queryCoordinates.length == dimensions == false) return None
    val result = ListBuffer[V]()
    doQuery(query, ListBuffer[Node](root), result)
    Some(result.toList)
  }


  @tailrec
  private def doQuery(query: Query, nodes: ListBuffer[Node], result: ListBuffer[V]): Unit = {
    if(nodes.isEmpty) return
    val node = nodes.head
    nodes -= node // dequeue
    if(contains(query, node)) result += node.value.get
    nodes ++= eligibleChildren(query, node)
    doQuery(query, nodes, result)
  }

  /**
    * TODO: NEEDS IMPROVEMENT </br>
    * It looks like an easy pattern to follow, but current impl is a bit tricky verification: </br>
    * for each quadrant, for each dimension check whether comparison with node should be
    * with min or max values. </br>
    * Example with 3D space: </br>
    * 2 | 3  -  6 | 7  </br>
    * -- --     -- --  </br>
    * 0 | 1  -  4 | 5  </br>
    *  </br>
    * N  X  Y  Z | X   Y   Z   | X  Y  Z </br>
    * 0  <  <  < | -   -   -   | 0  0  0 </br>
    * 1  >  <  < | flp -   -   | 1  0  0 </br>
    * 2  <  >  < | flp flp -   | 0  1  0 </br>
    * 3  >  >  < | flp -   -   | 1  1  0 </br>
    * 4  <  <  > | flp flp flp | 0  0  1 </br>
    * 5  >  <  > | flp -   -   | 1  0  1 </br>
    * 6  <  >  > | flp flp -   | 0  1  1 </br>
    * 7  >  >  > | flp -   -   | 1  1  1 </br>
    * */
  private def eligibleChildren(query: Query, node: Node): ListBuffer[Node] = {
    val queryCoordinates = query.queryCoordinates
    val queryLength= queryCoordinates.length
    val result = ListBuffer[Node]()
    val checkMax = Array.fill(queryLength) (false) // check min side
    // quadrant is for 2D, need to find multiD equivalent word
    // here I profit from the fact that each quadrant can have 0 or 1 children
    val quadrants = node.children
    for(quadrant <- quadrants.indices){
      breakable{
        // must do the flips on each node check
        for(dimension <- 0 until queryLength){
          doFlips(quadrant, dimension, checkMax)
        }
        if(quadrants(quadrant) == null) break() // no child => skip the check
        for(dimension <- 0 until queryLength){
          // check conditions for not being eligible node
          if(checkMax(dimension) == false && ordering.lt(node.key(dimension), queryCoordinates(dimension)._1)) break()
          else if(checkMax(dimension) && ordering.gt(node.key(dimension), queryCoordinates(dimension)._2)) break()
        }
        // add node for further search
        result += quadrants(quadrant)
      }
    }
    result
  }

  private def doFlips(quadrant: Int, dimension: Int, checkMax: Array[Boolean]): Unit = {
    if(quadrant == 0) return
    if(dimension == 0 || quadrant / pow(2, dimension) % 1 == 0) checkMax(dimension) = !checkMax(dimension)
  }

  /** check if node is inside the query constraints.. exclusive min, inclusive max*/
  private def contains(query: Query, node: Node): Boolean = {
    val key = node.key
    val q = query.queryCoordinates
    for(i <- q.indices){
      val (min, max) = (q(i)._1, q(i)._2)
      if(ordering.lt(key(i), min) || ordering.gteq(key(i), max)) return false
    }
    true
  }

  private class Node(val key: Array[K], val value: Option[V]) {
    // 2 children for each dimension: min and max
    private val maxChildren: Int = pow(2, key.length).ceil.asInstanceOf[Int]
    val children: Array[Node] = Array.ofDim[Node](maxChildren)
    override def toString: String = {
      val keys = key.mkString(" ")
      val valOption = value match {
        case Some(v) => v.toString
        case None => ""
      }
      s"$keys - $valOption"
    }
  }

  /** min (inclusive) and max (exclusive) key for each dimension */
  class Query(val queryCoordinates: Array[(K, K)]){
    override def toString: String = queryCoordinates.map{case(min, max) => s"($min - $max)"}.mkString(" ")
  }
}
