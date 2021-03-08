package org.creek

import org.creek.collection.{Directed, Graph, Undirected}

import scala.collection.mutable
import scala.io.Source

//TODO: make this internal API better, as resource files already follow a pattern
package object util {

  private val SPLIT = "\\s"
  private val SPLIT_DEPENDENCIES = " / "
  private val DASH = "/"

  def directedDependenciesGraph(name: String): Graph[String] = {
    val dependencies = readDependenciesLines(name)
    val graph = Graph[String](Directed)
    for(dependency <- dependencies){
      if(dependency._2.isEmpty) graph.addEdge(dependency._1, null)
      else{
        for(parent <- dependency._2){
          graph.addEdge(parent, dependency._1)
        }
      }
    }
    graph
  }

  def directedTupleDependenciesGraph(name: String): Graph[(String, String)] = {
    val dependencies = readTupleDependenciesLines(name)
    val graph = Graph[(String, String)](Directed)
    val nodes = mutable.Map[String, String]()
    for(line <- dependencies){
      nodes += line._1._1 -> line._1._2
    }
    for(line <- dependencies){
      if(line._2.isEmpty) graph.addEdge(line._1, null)
      else {
        for(parent <- line._2){
          nodes.get(parent)
          graph.addEdge(line._1, (parent, nodes(parent)))
        }
      }
    }
    graph
  }

  def undirectedNumberGraph(name: String): Graph[Double] ={
    val edges = readNumericLines(name)
    val g = Graph[Double](Undirected)
    edges.foreach{ edge =>
      if(edge.length == 2) g.addEdge(edge(0), edge(1))
      else if(edge.length == 3) g.addEdge(edge(0), edge(1), edge(2))
    }
    g
  }

  def directedNumberGraph(name: String): Graph[Double] ={
    val edges = readNumericLines(name)
    val g = Graph[Double](Directed)
    edges.foreach{ edge =>
      if(edge.length == 2) g.addEdge(edge(0), edge(1))
      else if(edge.length == 3) g.addEdge(edge(0), edge(1), edge(2))
    }
    g
  }

  def directedStringGraph(name: String): Graph[String] ={
    val edges = readStringLines(name)
    val g = Graph[String](Directed)
    edges.foreach{ edge =>
      if(edge.length == 2) g.addEdge(edge(0), edge(1))
      else if(edge.length == 3) g.addEdge(edge(0), edge(1), edge(2).toDouble)
    }
    g
  }

  def readItemsLines4(name: String): List[(String, String, String, String)] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT))map(array => (array(0), array(1), array(2), array(3)))
    lines.toList
  }

  def readItemsLines3(name: String): List[(String, String, String)] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT))map(array => (array(0), array(1), array(2)))
    lines.toList
  }

  def readItemsLines(name: String): List[(String, String)] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT)).map(array => (array(0), array(1)))
    lines.toList
  }

  // lines are in the format "double double" or "double double double"
  private def readNumericLines(name: String): List[Array[Double]] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT).map(_.toDouble))
    lines.toList
  }

  // lines are in the format "string string" or "string string double"
  private def readStringLines(name: String): List[Array[String]] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT))
    lines.toList
  }

  // line are in the format "string" or "string / string+"
  private def readDependenciesLines(name: String): Map[String, Array[String]] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT_DEPENDENCIES))
    lines.map(line => line.head -> line.tail).toMap
  }

  // lines are in the format "nodeKey nodeValue dep1 dep2 depN"
  private def readTupleDependenciesLines(name: String): Map[(String, String), Array[String]] = {
    val source = Source.fromURL(getClass.getResource(DASH + name))
    val lines = source.getLines().map(_.split(SPLIT))
    lines.map(line => (line.head, line(1)) -> line.tail.drop(1)).toMap
  }

}
