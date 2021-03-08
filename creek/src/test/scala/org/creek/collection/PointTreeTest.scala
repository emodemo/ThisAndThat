package org.creek.collection

import org.creek.util.readItemsLines
import org.creek.util.readItemsLines3
import org.creek.util.readItemsLines4
import org.scalatest.FunSuite

class PointTreeTest extends FunSuite {

  //TODO: tests for FIND

  // quad_tree structure must be
  // root 3-10 {2-4; 5-7; - ; 6-11}
  // child 2-4 { - ; - ; 1-9; - }
  // child 5-7 {4-6; 7-3; 4-8; 6-9}
  // child 7-3 { - ; - ; 6-6; 8-6}

  test("query quad tree"){
    val tree = new PointTree[Int, String](2)
    //val tree = new QuadTree[Int, String]()
    val lines = readItemsLines("quad_tree.txt")
    for(line <- lines) tree.insert(Array[Int](line._1.toInt, line._2.toInt), line._1 + line._2)
    // query 1
    var query = new tree.Query(Array((4, 8),(3, 7)))
    var result = tree.query(query).get
    assert(result.size === 3)
    assert(result.head === "46")
    assert(result(1) === "73")
    assert(result(2) === "66")
    // query 2
    query = new tree.Query(Array((1, 6),(8, 11)))
    result = tree.query(query).get
    assert(result.size === 3)
    assert(result.head === "310")
    assert(result(1) === "19")
    assert(result(2) === "48")
  }

  test("query oct tree"){
    val tree = new PointTree[Int, String](3)
    //val tree = new QuadTree[Int, String]()
    val lines = readItemsLines3("oct_tree.txt")
    for(line <- lines) tree.insert(Array[Int](line._1.toInt, line._2.toInt, line._3.toInt), line._1 + line._2 + line._3)
    // query 1
    var query = new tree.Query(Array((4, 8),(3, 7),(0, 1)))
    var result = tree.query(query).get
    assert(result.size === 3)
    assert(result.head === "460")
    assert(result(1) === "730")
    assert(result(2) === "660")
    // query 2
    query = new tree.Query(Array((1, 6),(8, 11),(0, 1)))
    result = tree.query(query).get
    assert(result.size === 3)
    assert(result.head === "3100")
    assert(result(1) === "190")
    assert(result(2) === "480")
    // query 3
    query = new tree.Query(Array((4, 8),(3, 7),(0, 2)))
    result = tree.query(query).get
    assert(result.size === 6)
    assert(result.head === "460")
    assert(result(1) === "730")
    assert(result(2) === "461")
    assert(result(3) === "731")
    assert(result(4) === "660")
    assert(result(5) === "661")
    // query 4
    query = new tree.Query(Array((1, 6),(8, 11),(0, 2)))
    result = tree.query(query).get
    assert(result.size === 6)
    assert(result.head === "3100")
    assert(result(1) === "3101")
    assert(result(2) === "190")
    assert(result(3) === "480")
    assert(result(4) === "481")
    assert(result(5) === "191")
  }

  ignore("hex oct tree"){
    val tree = new PointTree[Int, String](4)
    //val tree = new QuadTree[Int, String]()
    val lines = readItemsLines4("hex_tree.txt")
    for(line <- lines) tree.insert(Array[Int](line._1.toInt, line._2.toInt, line._3.toInt, line._4.toInt),
      line._1 + line._2 + line._3 + line._4)
    // query 1
    var query = new tree.Query(Array((4, 8),(3, 7),(0, 1),(0, 1)))
    var result = tree.query(query).get
    assert(result.size === 3)
    assert(result.head === "4600")
    assert(result(1) === "7300")
    assert(result(2) === "6600")
    // query 2
    query = new tree.Query(Array((1, 6),(8, 11),(0, 1),(0, 1)))
    result = tree.query(query).get
    assert(result.size === 3)
    assert(result.head === "31000")
    assert(result(1) === "1900")
    assert(result(2) === "4800")
    // query 3
    query = new tree.Query(Array((4, 8),(3, 7),(0, 2),(0,1)))
    result = tree.query(query).get
    assert(result.size === 6)
    assert(result.head === "4600")
    assert(result(1) === "7300")
    assert(result(2) === "4610")
    assert(result(3) === "7310")
    assert(result(4) === "6600")
    assert(result(5) === "6610")
    // query 4
    query = new tree.Query(Array((1, 6),(8, 11),(0, 2),(0,1)))
    result = tree.query(query).get
    assert(result.size === 6)
    assert(result.head === "31000")
    assert(result(1) === "31010")
    assert(result(2) === "1900")
    assert(result(3) === "4800")
    assert(result(4) === "4810")
    assert(result(5) === "1910")
    // query 5
    query = new tree.Query(Array((4, 8),(3, 7),(0, 2),(0,2)))
    result = tree.query(query).get
    assert(result.size === 12)
    assert(result.head === "4600")
    assert(result(1) === "7300")
    assert(result(2) === "4610")
    assert(result(3) === "7310")
    assert(result(4) === "6600")
    assert(result(5) === "6610")
    // query 6
    query = new tree.Query(Array((1, 6),(8, 11),(0, 2),(0,2)))
    result = tree.query(query).get
    assert(result.size === 12)
    assert(result.head === "31000")
    assert(result(1) === "31010")
    assert(result(2) === "1900")
    assert(result(3) === "4800")
    assert(result(4) === "4810")
    assert(result(5) === "1910")
  }
}
