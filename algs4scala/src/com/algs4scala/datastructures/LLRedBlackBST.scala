package com.algs4scala.datastructures

/**
 *
 * A left-leaned Red Black BST implementation based on Robert Sedgewick
 *
 * @author Emo
 *
 * @tparam K the key implementing an Ordering
 * @tparam V the value
 */
class LLRedBlackBST[K <: Ordered[K], V] {

  private var root: Node = null

  def insert(key: K, value: V): Unit = {
    if (key == null) throw new NullPointerException("null key")
    root = doInsert(root, key, value)
    root.redColor = false
  }

  private def doInsert(node: Node, key: K, value: V): Node = {
    if (node == null) new Node(key, value)
    else {
      // insert
      val compare = key.compareTo(node.key)
      if (compare < 0) node.left = doInsert(node.left, key, value)
      else if (compare > 0) node.right = doInsert(node.right, key, value)
      else node.value = value
      // balance
      balance(node)

      node // bad practice => return input argument
    }
  }

  private def balance(node: Node) = {
    if (node.right.redColor && !node.left.redColor) rotateLeft(node)
    if (node.left.redColor && node.left.left.redColor) rotateRight(node)
    if (node.left.redColor && node.right.redColor) flipColors(node)
    node.size = size(node.left) + size(node.right) + 1
  }

  def find(key: K): Option[V] = {
    if (key == null) throw new NullPointerException("null key")
    doFind(root, key)
  }

  private def doFind(node: Node, key: K): Option[V] = {
    var result = node
    while (result != null) {
      val compare = key.compareTo(result.key)
      if (compare < 0) result = result.left
      else if (compare > 0) result = result.right
      else return Some(result.value)
    }
    None
  }

  def delete(key: K) = {
    ???
  }

  private def size(node: Node): Int = {
    if (node == null) return 0
    node.size
  }

  private def rotateLeft(node: Node) = {
    val tmp = node.left
    node.right = tmp.left
    tmp.left = node
    tmp.redColor = node.redColor
    node.redColor = true
  }

  private def rotateRight(node: Node) = {
    val tmp = node.left
    node.left = tmp.right
    tmp.right = node
    tmp.redColor = node.redColor
    node.redColor = true
  }

  private def flipColors(node: Node) = {
    node.redColor = !node.redColor
    node.left.redColor = !node.left.redColor
    node.right.redColor = !node.right.redColor
  }

  private class Node(val key: K, var value: V, var redColor: Boolean = true, var size: Int = 1) {
    var left: Node = null
    var right: Node = null
  }

}
