package com.algs4scala.datastructures

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks.breakable
import scala.util.control.Breaks.break
import scala.annotation.tailrec
import scala.collection.mutable.Stack

/**
 * Experimental implementation of a Quad/Oct Tree generalization for multidimensional space</br>
 * Implementation is <code>@tailrec</code> optimized
 *
 * @author Emo
 *
 * @tparam T the type of each dimension of the key
 * @tparam V the value
 * @param ordering of the related type
 */
class MultiDPointTree[T, V](implicit ordering: Ordering[T]) {

  private var root: Node = null

  def insert(key: Array[T], value: V) = {
    if (root == null) root = new Node(key, value)
    else doInsert(root, calculateDirection(root, key), key, value)
  }

  @tailrec
  private def doInsert(node: Node, childIndex: Int, key: Array[T], value: V): Unit = {
    if (node.nodes(childIndex) == null) node.nodes(childIndex) = new Node(key, value)
    else doInsert(node.nodes(childIndex), calculateDirection(node.nodes(childIndex), key), key, value)
  }

  private def calculateDirection(node: Node, key: Array[T]): Int = {
    var snindex = 0
    for (i <- 0 until key.size) {
      if (ordering.gteq(key(i), node.key(i))) snindex += i + 1
    }
    snindex
  }

  def find(key: Array[T]): Option[V] = {
    if (key == null) throw new NullPointerException("null key")
    doFind(root, key)
  }

  @tailrec
  private def doFind(node: Node, key: Array[T]): Option[V] = {
    if (node == null) return None
    if (isSame(key, node.key)) return Some(node.value)
    val childNode = node.nodes(calculateDirection(node, key))
    if (childNode == null) return None
    doFind(childNode, key)
  }

  private def isSame(first: Array[T], second: Array[T]): Boolean = {
    for (i <- 0 until first.length) {
      if (!ordering.equiv(first(i), second(i))) return false
    }
    true
  }

  def query(minmaxCoordinates: Array[Array[T]]): List[V] = {
    var buffer = ArrayBuffer[V]()
    doQuery(minmaxCoordinates, root, buffer)
    buffer.toList
  }

  // @tailrec manual optimization
  private def doQuery(box: Array[Array[T]], node: Node, buffer: ArrayBuffer[V]) : Unit = {
    val stack = Stack[Node]()
    stack.push(node)
    while (!stack.isEmpty) {
      val n = stack.pop()
      if (contains(box, n.key)) buffer += n.value
      val subnodes = subnodesToQuery(box, n)
      for (i <- subnodes) {
        stack.push(n.nodes(i))
      }
    }
  }

  private def contains(box: Array[Array[T]], point: Array[T]): Boolean = {
    for (i <- 0 until box.length) {
      if (ordering.lt(point(i), box(i)(0)) || ordering.gt(point(i), box(i)(1))) return false
    }
    true
  }

  // go thru each box, choose dimension's side (less or more), choose to check for < min or > max
  private def subnodesToQuery(box: Array[Array[T]], node: Node): Array[Int] = {
    val flips = Array.fill(box.length)(false) // 0
    val result = ArrayBuffer[Int]()
    val nodes = node.nodes
    for (n <- 0 until nodes.length) {
      breakable {
        // must do the flips on each node check
        for (b <- 0 until box.length) {
          doFlips(n, b, flips)
        }
        // case node is null "continue to next one"
        if (nodes(n) == null) break
        // check if box is eligible
        for (b <- 0 until box.length) {
          if (!flips(b) && ordering.lt(node.key(b), box(b)(0))) break
          else if (flips(b) && ordering.gt(node.key(b), box(b)(1))) break
        }
        // add node for further search
        result += n
      }
    }
    result.toArray
  }

  private def doFlips(nodeN: Int, boxN: Int, flips: Array[Boolean]) = {
    if (nodeN != 0) {
      if (boxN == 0) {
        if (flips(boxN)) flips(boxN) = false
        else flips(boxN) = true
      } else {
        if ((nodeN / Math.pow(2, boxN)) % 1 == 0) {
          if (flips(boxN)) flips(boxN) = false
          else flips(boxN) = true
        }
      }
    }
  }

  def delete(key: Array[T]): Unit = {
    ???
  }

  private class Node(val key: Array[T], val value: V) {
    val nodes = Array.ofDim[Node](key.length * 2)
    override def toString(): String = {
      var s = "("
      key.foreach(k => s += k.toString() + ",")
      s.substring(0, s.length() - 1) + ")"
    }
  }

}
