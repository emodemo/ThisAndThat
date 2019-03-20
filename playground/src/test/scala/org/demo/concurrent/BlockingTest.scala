package org.demo.concurrent

import scala.concurrent.{Await, ExecutionContext, Future, blocking}
import scala.concurrent.duration.Duration
import org.scalatest.FunSuite
import org.scalameter._

class BlockingTest extends FunSuite {
  private val from = 1
  private val to = Runtime.getRuntime.availableProcessors() * 8

  test("blocking_with_warmer") {
    val time = withWarmer {
      new Warmer.Default
    } withMeasurer {
      new Measurer.IgnoringGC
    } measure {
      implicit val ec = ExecutionContext.global
      val futures = for (_ <- from to to) yield
        Future {
          blocking {
            Thread.sleep(1000)
          }
        }
      for (f <- futures) Await.ready(f, Duration.Inf)
    }
    println(s"Total time: $time")
  }

  test("blocking") {
    implicit val ec = ExecutionContext.global
    val futures = for (_ <- from to to) yield
      Future {
        blocking {
          Thread.sleep(1000)
        }
      }
    for (f <- futures) Await.ready(f, Duration.Inf)
  }
  // up to N of Cores
  test("non-blocking") {
    implicit val ec = ExecutionContext.global
    val futures = for (_ <- from to to) yield
      Future {
        Thread.sleep(1000)
      }
    for (f <- futures) Await.ready(f, Duration.Inf)
  }
  // sequential
  test("forLoop") {
    (from to to).foreach { _ => Thread.sleep(1000) }
  }
  // up to N of cores
  test("forLoopParallel") {
    (from to to).par.foreach { _ => Thread.sleep(1000) }
  }
  test("forLoopParallelBlocking")(
    (from to to).par.foreach { _ => blocking { Thread.sleep(1000) }}
  )
}
