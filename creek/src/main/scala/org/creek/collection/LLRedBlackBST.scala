package org.creek.collection

class LLRedBlackBST[K, V](implicit ordering: Ordering[K]) {

  private var root: Node = _

  def insert(key: K, value: V): Unit = {
    if (key == null) return
    if(root == null) root = new Node(key, value)
    else doInsert(root, key, value)
    root.redColor = false
  }

  private def doInsert(node: Node, key: K, value: V): Unit = {
      // insert
      val compare = ordering.compare(key, node.key)
      if (compare < 0) {
        if(node.left == null) node.left = new Node(key, value)
        else doInsert(node.left, key, value)
      }
      else if (compare > 0) {
        if(node.right == null) node.right = new Node(key, value)
        else doInsert(node.right, key, value)
      }
      else node.value = value
      balance(node)
  }


  private def balance(node: Node): Unit = {
    if (node.right.redColor && !node.left.redColor) rotateLeft(node)
    if (node.left.redColor && node.left.left.redColor) rotateRight(node)
    if (node.left.redColor && node.right.redColor) flipColors(node)
    node.size = size(node.left) + size(node.right) + 1
  }

  def find(key: K): Option[V] = key match {
    case null => None
    case _ => doFind(root, key)
  }

  private def doFind(node: Node, key: K): Option[V] = {
    var result = node
    while (result != null) {
      val compare = ordering.compare(key, node.key)
      if (compare < 0) result = result.left
      else if (compare > 0) result = result.right
      else return Some(result.value)
    }
    None
  }

  def delete(key: K): Unit = { }

  private def size(node: Node): Int = {
    if (node == null) return -1
    node.size
  }

  private def rotateLeft(node: Node): Unit = {
    val tmp = node.left
    node.right = tmp.left
    tmp.left = node
    tmp.redColor = node.redColor
    node.redColor = true
  }

  private def rotateRight(node: Node): Unit = {
    val tmp = node.left
    node.left = tmp.right
    tmp.right = node
    tmp.redColor = node.redColor
    node.redColor = true
  }

  private def flipColors(node: Node): Unit = {
    node.redColor = !node.redColor
    node.left.redColor = !node.left.redColor
    node.right.redColor = !node.right.redColor
  }

  private class Node(val key: K, var value: V, var redColor: Boolean = true, var size: Int = -1) {
    var left: Node = _
    var right: Node = _
  }

}
