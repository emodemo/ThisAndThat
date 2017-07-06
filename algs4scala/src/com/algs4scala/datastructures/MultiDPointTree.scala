package com.algs4scala.datastructures

import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._
import scala.annotation.tailrec
import scala.collection.mutable.Stack

/**
 * A Quad/Oct Tree generalization for multidimensional space</br>
 * Implementation is partially <code>@tailrec</code> optimized
 * 
 * @author Emo
 *
 * @param <DType> the type of each dimension of the key
 * @param <Value> the value
 */
class MultiDPointTree[DType, Value] (implicit ordering: Ordering[DType]) {
  
  private var root: Node = null
  
  def insert(key: Array[DType], value: Value) = {
    if(root == null) root = new Node(key, value)
    else doInsert(root, calculateDirection(root, key), key, value)
  }

  @tailrec
  private def doInsert(node: Node, childIndex: Int, key: Array[DType], value: Value) {
    if(node.nodes(childIndex) == null) node.nodes(childIndex) = new Node(key, value)
    else doInsert(node.nodes(childIndex), calculateDirection(node.nodes(childIndex), key), key, value)
  }
  
  private def calculateDirection(node: Node, key: Array[DType]) : Int = {
    var snindex = 0
    for(i <- 0 until key.size){
      if(ordering.gteq(key(i), node.key(i))) snindex += i + 1
    }
    snindex
  }
  
  def find(key: Array[DType]) : Option[Value] = {
    if(key == null) throw new NullPointerException("null key")
    doFind(root, key)
  }
  
  @tailrec
  private def doFind(node: Node, key: Array[DType]) : Option[Value] = {
    if(node == null) return None
    if(isSame(key, node.key)) return Some(node.value)
    val childNode = node.nodes(calculateDirection(node, key))
    if(childNode == null) return None
    doFind(childNode, key)
  }
  
  private def isSame(first: Array[DType], second: Array[DType]) : Boolean = {
    for(i <- 0 until first.length){
      if(!ordering.equiv(first(i), second(i))) return false
    }
    true
  }
  
  def query(minmaxCoordinates: Array[Array[DType]]) : List[Value] = {
    var buffer = ArrayBuffer[Value]()
    doQuery(minmaxCoordinates, root, buffer)
    buffer.toList
  }

  // a recursive implementation. Not used as the class is aimed for tailrec optimization
  private def doQuery_2(box: Array[Array[DType]], node: Node, buffer: ArrayBuffer[Value]) {
    if(contains(box, node.key)) buffer += node.value
    val subnodes = subnodesToQuery(box, node)
		for(i <- subnodes){
		  doQuery(box, node.nodes(i), buffer)			  
		}
  }
  
  //@tailrec manual optimization
  private def doQuery(box: Array[Array[DType]], node: Node, buffer: ArrayBuffer[Value]) {
    val stack = Stack[Node]()
    stack.push(node)
    while(!stack.isEmpty){
    	val n = stack.pop()
      if(contains(box, n.key)) buffer += n.value
 			val subnodes = subnodesToQuery(box, n)
 			for(i <- subnodes){
 			  stack.push(n.nodes(i))
 			}
    }
  }
  
  private def contains(box: Array[Array[DType]], point: Array[DType]) : Boolean = {
    for(i <- 0 until box.length){
      if(ordering.lt(point(i), box(i)(0)) || ordering.gt(point(i), box(i)(1))) return false
    }
    true    
  }
  
  // go thru each box, choose dimension's side (less or more), choose to check for < min or > max
  private def subnodesToQuery(box: Array[Array[DType]], node: Node) : Array[Int] = {
    val flips = Array.fill(box.length)(false) // 0
    val result = ArrayBuffer[Int]()
    val nodes = node.nodes
    for(n <- 0 until nodes.length){
      breakable {
        // must do the flips on each node check
    	  for(d <- 0 until box.length){
    	     if(n != 0){
    	       if(d == 0){
    	         if(flips(d)) flips(d) = false
    	         else flips(d) = true
    	       }
    	       else{
    	         if((n / Math.pow(2, d)) % 1 == 0){
    	           if(flips(d)) flips(d) = false
    	           else flips(d) = true
    	         }
    	       }
    	     }
    	  }
    	  // case node is null "continue to next one"
        if(nodes(n) == null) break
        // check if box is eligible
        for(d <- 0 until box.length){
          if(!flips(d) && ordering.lt(node.key(d), box(d)(0))) break
          else if(flips(d) && ordering.gt(node.key(d), box(d)(1))) break
        }
        // add node for further search
        result += n
      }
    }
    result.toArray
  }

  def delete(key: Array[DType]){
    ???
  }
  
  
  private class Node(val key: Array[DType], var value: Value){
 		val nodes = Array.ofDim[Node](key.length * 2)
    override def toString() : String = {
      var s = "("
      key.foreach(k => s += k.toString() + ",")
      s.substring(0, s.length()-1) + ")"
    }
  }
  
}
