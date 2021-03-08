package org.creek.collection

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer


// TODO: use KDPoint and KDBox ... one beautiful day
// TODO: add value, and getValue(key: Key)
// TODO: remove negInf and posInf... what should they be in case of String... again double => refactor
class KDTree[T](negInf: T, posInf: T, dimensions: Int)(implicit ordering: Ordering[T]) {

  private var root: Node = _

  def insert(key: Array[T]): Unit = {
    //TODO: Array.length must be same as dimensions, or use the root size
    if (root == null) root = new Node(key, 0)
    else doInsert(root, key)
  }

  @tailrec
  private def doInsert(parent: Node, childKey: Array[T]): Unit = {
    // TODO: check if node already exists
    val comparedDim = parent.dimension
    val compare = ordering.compare(childKey(comparedDim), parent.key(comparedDim))
    val childDimension = if (comparedDim < dimensions - 1) comparedDim + 1 else 0
    if (compare <= 0) {
      if (parent.smaller == null) parent.smaller = new Node(childKey, childDimension)
      else doInsert(parent.smaller, childKey)
    }
    else {
      if (parent.bigger == null) parent.bigger = new Node(childKey, childDimension)
      else doInsert(parent.bigger, childKey)
    }
  }

  /** min (inclusive) and max (exclusive) values for each dimension */
  def query(query: Array[(T, T)]): List[Array[T]] = {
    // TODO: check query dimensions are correct
    // TODO: check if root is not null
    val buffer = ListBuffer[Array[T]]()
    val rootBoxes = Array.ofDim[(T, T)](query.length).map(_ => (negInf, posInf))
    doQuery(query, root, rootBoxes, buffer)
    buffer.toList
  }

  // Note: both contains and intersects can be done as curring functions, as query is always the same
  // conditions for true: Qxmin <= node.x ; Qxmax > node.x
  private def contains(query: Array[(T, T)], node: Node): Boolean = {
    for (dim <- query.indices) {
      if (ordering.gt(query(dim)._1, node.key(dim))) return false // ._1 = min
      if (ordering.lteq(query(dim)._2, node.key(dim))) return false // ._2 = max
    }
    true
  }

  // conditions for true: Qxmin <= nodexmax, Qxmax > nodexmin
  private def intersects(query: Array[(T, T)], nodeBoxes: Array[(T, T)]): Boolean = {
    for (dim <- query.indices) {
      if (ordering.gt(query(dim)._1, nodeBoxes(dim)._2)) return false // ._1 = min
      if (ordering.lteq(query(dim)._2, nodeBoxes(dim)._1)) return false // ._2 = max
    }
    true
  }

  // expected 2 boxes per dimension, one for less an one for more
  private def doQuery(query: Array[(T, T)], node: Node, boxes: Array[(T, T)], buffer: ListBuffer[Array[T]]): Unit = {
    if (contains(query, node)) buffer += node.key
    if (node.smaller != null) {
      // if smaller => update upper bound of boxes dimension that correspond to parent dimension
      val childBoxes = boxes.clone()
      childBoxes.update(node.dimension, (childBoxes(node.dimension)._1, node.key(node.dimension)))
      // this if can be done once at beginning of method, with input boxes
      if (intersects(query, childBoxes)) doQuery(query, node.smaller, childBoxes, buffer)
    }
    if (node.bigger != null) {
      val childBoxes = boxes.clone()
      childBoxes.update(node.dimension, (node.key(node.dimension), childBoxes(node.dimension)._2))
      if (intersects(query, childBoxes)) doQuery(query, node.bigger, childBoxes, buffer)
    }
  }

  def nearest(node: T): List[T] = ???

  private class Node(val key: Array[T], val dimension: Int) {
    var smaller: Node = _
    var bigger: Node = _

    override def toString: String = {
      val string = key.map(k => k.toString).mkString(", ")
      s"{$string}"
    }
  }

}
