package org.creek.path

import org.creek.collection.{Directed, Graph}
import org.creek.util._
import org.scalactic.{Equality, TolerantNumerics}
import org.scalatest.FunSuite

import scala.math.{exp, log}

class ShortestPathTest extends FunSuite{

  private implicit val doubleEquality: Equality[Double] = TolerantNumerics.tolerantDoubleEquality(0.001)

  test("bfs"){
    val g = undirectedNumberGraph("graph.txt")
    assert( ShortestPath.bfs[Double](g, 0, 5) === List[Double](0, 5))
    assert( ShortestPath.bfs[Double](g, 0, 6) === List[Double](0, 5, 6))
  }

  test("dijkstra"){
    val g = directedNumberGraph("directed_weighted_graph_2.txt")
    assert( ShortestPath.dijkstra[Double](g, 0, 0).map(edge => edge.weight).sum === 0.00)
    assert( ShortestPath.dijkstra[Double](g, 0, 1).map(edge => edge.weight).sum === 1.05)
    assert( ShortestPath.dijkstra[Double](g, 0, 2).map(edge => edge.weight).sum === 0.26)
    assert( ShortestPath.dijkstra[Double](g, 0, 3).map(edge => edge.weight).sum === 0.99)
    assert( ShortestPath.dijkstra[Double](g, 0, 4).map(edge => edge.weight).sum === 0.38)
    assert( ShortestPath.dijkstra[Double](g, 0, 5).map(edge => edge.weight).sum === 0.73)
    assert( ShortestPath.dijkstra[Double](g, 0, 6).map(edge => edge.weight).sum === 1.51)
    assert( ShortestPath.dijkstra[Double](g, 0, 7).map(edge => edge.weight).sum === 0.60)
  }

  test("bellmanFord"){
    val g = directedNumberGraph("directed_weighted_graph_2.txt")
    assert( ShortestPath.bellmanFord[Double](g, 0, 0).right.get.map(edge => edge.weight).sum === 0.00)
    assert( ShortestPath.bellmanFord[Double](g, 0, 1).right.get.map(edge => edge.weight).sum === 1.05)
    assert( ShortestPath.bellmanFord[Double](g, 0, 2).right.get.map(edge => edge.weight).sum === 0.26)
    assert( ShortestPath.bellmanFord[Double](g, 0, 3).right.get.map(edge => edge.weight).sum === 0.99)
    assert( ShortestPath.bellmanFord[Double](g, 0, 4).right.get.map(edge => edge.weight).sum === 0.38)
    assert( ShortestPath.bellmanFord[Double](g, 0, 5).right.get.map(edge => edge.weight).sum === 0.73)
    assert( ShortestPath.bellmanFord[Double](g, 0, 6).right.get.map(edge => edge.weight).sum === 1.51)
    assert( ShortestPath.bellmanFord[Double](g, 0, 7).right.get.map(edge => edge.weight).sum === 0.60)
  }

  test("bellmanFord_negativeWeight"){
    val g = directedNumberGraph("directed_weighted_negative_graph.txt")
    assert( ShortestPath.bellmanFord[Double](g, 0, 0).right.get.map(edge => edge.weight).sum === 0.00)
    assert( ShortestPath.bellmanFord[Double](g, 0, 1).right.get.map(edge => edge.weight).sum === 0.93)
    assert( ShortestPath.bellmanFord[Double](g, 0, 2).right.get.map(edge => edge.weight).sum === 0.26)
    assert( ShortestPath.bellmanFord[Double](g, 0, 3).right.get.map(edge => edge.weight).sum === 0.99)
    assert( ShortestPath.bellmanFord[Double](g, 0, 4).right.get.map(edge => edge.weight).sum === 0.26)
    assert( ShortestPath.bellmanFord[Double](g, 0, 5).right.get.map(edge => edge.weight).sum === 0.61)
    assert( ShortestPath.bellmanFord[Double](g, 0, 6).right.get.map(edge => edge.weight).sum === 1.51)
    assert( ShortestPath.bellmanFord[Double](g, 0, 7).right.get.map(edge => edge.weight).sum === 0.60)
  }

  test("bellmanFord_negativeCycle"){
    val g = directedNumberGraph("directed_weighted_graph_negative_cycle.txt")
    val cycle = ShortestPath.bellmanFord[Double](g, 0, 4)
    assert( cycle.left.get._1 == ShortestPath.errorMessage)
    assert( cycle.left.get._2.map(edge => edge.weight).sum === -0.31)
  }

  test("bellmanFord_arbitrage"){
    val graph = directedStringGraph("arbitrage_rates.txt")
    // create new graph with -log(weight)
    val arbitrageGraph = Graph[String](Directed)
    for{
      node <- graph.getNodes
      edge <- graph.adjacentEdges(node)
    } arbitrageGraph.addEdge(edge.from, edge.to, -log(edge.weight))

    // call bellmanFord
    val bellmanFord = ShortestPath.bellmanFord[String](arbitrageGraph, arbitrageGraph.getNodes.head, arbitrageGraph.getNodes.head)
    val arbitrage = bellmanFord match {
      case Left(cycle) => Right(cycle._2)
      case Right(path) => Left("no cycle => no arbitrage", path)
    }
    val opportunity = arbitrage.right.get.map(edge => exp(-edge.weight)).product
    assert( opportunity === 1.0061 ) // if more then 1.000 => opportunity
    // the input file has more arbitrage opportunities => TODO: update src to catch them all
  }

  test("acyclic"){
    val graph = directedNumberGraph("directed_weighted_graph_acyclic.txt")
    // no path => TODO: update src to give infinity instead
    assert( ShortestPath.acyclic[Double](graph, 0, 5).map(edge => edge.weight).sum === 0.00)
    // has path
    assert( ShortestPath.acyclic[Double](graph, 5, 0).map(edge => edge.weight).sum === 0.73)
    assert( ShortestPath.acyclic[Double](graph, 5, 1).map(edge => edge.weight).sum === 0.32)
    assert( ShortestPath.acyclic[Double](graph, 5, 2).map(edge => edge.weight).sum === 0.62)
    assert( ShortestPath.acyclic[Double](graph, 5, 3).map(edge => edge.weight).sum === 0.61)
    assert( ShortestPath.acyclic[Double](graph, 5, 4).map(edge => edge.weight).sum === 0.35)
    assert( ShortestPath.acyclic[Double](graph, 5, 5).map(edge => edge.weight).sum === 0.00)
    assert( ShortestPath.acyclic[Double](graph, 5, 6).map(edge => edge.weight).sum === 1.13)
    assert( ShortestPath.acyclic[Double](graph, 5, 7).map(edge => edge.weight).sum === 0.28)
  }

}
