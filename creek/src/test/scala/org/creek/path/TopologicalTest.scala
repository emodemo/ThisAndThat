package org.creek.path

import org.creek.util._
import org.scalatest.FunSuite

class TopologicalTest extends FunSuite{

  test("sortNumbers"){
    val graph = directedNumberGraph("topological_numbers.txt")
    val result = Topological.sort(graph).right.get
    assert(result.indexOf(0) > result.indexOf(1))
    assert(result.indexOf(0) > result.indexOf(5))
    assert(result.indexOf(0) > result.indexOf(6))
    assert(result.indexOf(2) > result.indexOf(0))
    assert(result.indexOf(2) > result.indexOf(3))
    assert(result.indexOf(3) > result.indexOf(5))
    assert(result.indexOf(5) > result.indexOf(4))
    assert(result.indexOf(6) > result.indexOf(4))
    assert(result.indexOf(6) > result.indexOf(9))
    assert(result.indexOf(7) > result.indexOf(6))
    assert(result.indexOf(8) > result.indexOf(7))
    assert(result.indexOf(9) > result.indexOf(10))
    assert(result.indexOf(9) > result.indexOf(11))
    assert(result.indexOf(9) > result.indexOf(12))
    assert(result.indexOf(11) > result.indexOf(12))
  }

  test("sortCourses"){
    val graph = directedDependenciesGraph("topological_courses.txt")
    val result = Topological.sort(graph).right.get
    assert(result.indexOf("Algorithms") > result.indexOf("Databases"))
    assert(result.indexOf("Algorithms") > result.indexOf("Theoretical CS"))
    assert(result.indexOf("Algorithms") > result.indexOf("Scientific Computing"))
    assert(result.indexOf("Introduction to CS") > result.indexOf("Algorithms"))
    assert(result.indexOf("Introduction to CS") > result.indexOf("Advanced Programming"))
    assert(result.indexOf("Advanced Programming") > result.indexOf("Scientific Computing"))
    assert(result.indexOf("Scientific Computing") > result.indexOf("Computational Biology"))
    assert(result.indexOf("Theoretical CS") > result.indexOf("Computational Biology"))
    assert(result.indexOf("Theoretical CS") > result.indexOf("Artificial Intelligence"))
    assert(result.indexOf("Linear Algebra") > result.indexOf("Theoretical CS"))
    assert(result.indexOf("Calculus") > result.indexOf("Linear Algebra"))
    assert(result.indexOf("Artificial Intelligence") > result.indexOf("Robotics"))
    assert(result.indexOf("Artificial Intelligence") > result.indexOf("Machine Learning"))
    assert(result.indexOf("Artificial Intelligence") > result.indexOf("Neural Networks"))
    assert(result.indexOf("Machine Learning") > result.indexOf("Neural Networks"))

  }

  test("sortTasks"){
    val graph = directedDependenciesGraph("topological_tasks.txt")
    val result = Topological.sort(graph).right.get
    assert(result.contains("clean"))
    assert(result.indexOf("build") > result.indexOf("lint"))
    assert(result.indexOf("test") > result.indexOf("build"))
    assert(result.indexOf("test") > result.indexOf("lint"))
    assert(result.indexOf("deploy") > result.indexOf("build"))
    assert(result.indexOf("deploy") > result.indexOf("test"))
  }

  test("sortNumbersWithCycle"){
    val graph = directedNumberGraph("topological_numbers_cycle.txt")
    val result = Topological.sort(graph).left.get
    assert(result._1 == Topological.errorMessage)
    val cycle = result._2
    assert(cycle.size == 5)
    assert(cycle.head == 9.0)
    assert(cycle(1) == 6.0)
    assert(cycle(2) == 7.0)
    assert(cycle(3) == 12.0)
    assert(cycle(4) == 9.0)
  }

  test("sortTasksWithCycle"){
    val graph = directedDependenciesGraph("topological_tasks_cycle.txt")
    val result = Topological.sort(graph).left.get
    assert(result._1 == Topological.errorMessage)
    val cycle = result._2
    assert(cycle.size == 4)
    assert(cycle.head == "deploy")
    assert(cycle(1) == "lint")
    assert(cycle(2) == "test")
    assert(cycle(3) == "deploy")
  }
}
