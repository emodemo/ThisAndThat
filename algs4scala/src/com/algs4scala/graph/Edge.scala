package com.algs4scala.graph

/**
 * @param from from
 * @param to to
 * @param weight weight
 */
class Edge(val from: Int, val to: Int, val weight: Double){

  override def toString(): String =  from + " - " + to + " : " + weight

}
