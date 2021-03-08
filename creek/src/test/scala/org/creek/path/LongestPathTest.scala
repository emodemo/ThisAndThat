package org.creek.path

import org.creek.util.directedNumberGraph
import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite

class LongestPathTest extends FunSuite {

  test("acyclic"){
    implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.001)
    val graph = directedNumberGraph("directed_weighted_graph_acyclic.txt")
    assert( LongestPath.acyclic[Double](graph, 0, 5).map(edge => edge.weight).sum === 0.00)
    assert( LongestPath.acyclic[Double](graph, 5, 0).map(edge => edge.weight).sum === 2.44)
    assert( LongestPath.acyclic[Double](graph, 5, 1).map(edge => edge.weight).sum === 0.32)
    assert( LongestPath.acyclic[Double](graph, 5, 2).map(edge => edge.weight).sum === 2.77)
    assert( LongestPath.acyclic[Double](graph, 5, 3).map(edge => edge.weight).sum === 0.61)
    assert( LongestPath.acyclic[Double](graph, 5, 4).map(edge => edge.weight).sum === 2.06)
    assert( LongestPath.acyclic[Double](graph, 5, 5).map(edge => edge.weight).sum === 0.00)
    assert( LongestPath.acyclic[Double](graph, 5, 6).map(edge => edge.weight).sum === 1.13)
    assert( LongestPath.acyclic[Double](graph, 5, 7).map(edge => edge.weight).sum === 2.43)
  }
}
