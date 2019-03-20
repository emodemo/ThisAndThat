package org.demo.categories

import cats._
import cats.data.OptionT
import cats.implicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * http://loicdescotte.github.io/posts/scala-compose-option-future
  */
object MonadComposition extends App{

  // COMBINE vs COMPOSE
  // OPTIONS
  val optionA: Option[String] = Some("a")
  val optionB: Option[String] = Some("b")

  val test0: Option[String] = optionA combine optionB  // combine is from cats
  val test1: Option[String] = optionA.flatMap(a => optionB.map(b => a + b))
  val test2: Option[String] = for {
    a <- optionA
    b <- optionB
  } yield a + b
  assert(test1 == test2) // Some("ab")
  assert(test0 == test2) // Some("ab")


  // FUTURE
  def futureA: Future[String] = Future("a")
  def futureB(s: String): Future[String] = Future(s + "b")
  def futureC: Future[String] = Future("b")

  val test3: Future[String] = futureA combine futureB("a") // Future(Success(aab))
  val test4: Future[String] = futureA combine futureC // Future(Success(ab))
  // or desugared versions of test5 and test6
  val test5: Future[String] = for { // Future(Success(ab))
    a <- futureA
    ab <- futureB(a)
  } yield ab
  val test6: Future[String] = for { // Future(Success(aab))
    a <- futureA
    ab <- futureB(a)
  } yield a + ab

  assert(test3 != test5)
 // assert(test4 == test5) // true
 // assert(test3 == test6) // true

  //FUTURE[OPTION]
  // an issue when calling several sequential resources that can give 0 or more values
  def futureOptionA: Future[Option[String]] = Future(Some("a"))
  def futureOptionB(s: String): Future[Option[String]] = Future(Some(s + "b"))
  def futureOptionC: Future[Option[String]] = Future(Some("b"))

  // futureOptionA.combine(futureOptionB("a"))  // compilation error, cannot combine
  // futureOptionA combine futureOptionC        // compilation error, cannot combine

  // V0: handmade but ugly
  val combinedACUgly: Future[Option[String]] = for {
    optA <- futureOptionA
    optC <- futureOptionC
  } yield for {
    a <- optA
    c <- optC
  } yield a+c

  val combinedABUgly = for {
    optA <- futureOptionA
    optB <- futureOptionB(optA.get)
  } yield for {
    a <- optA
    ab <- optB
  } yield ab

  //V1: handmade binding, needs to manually handle the Option part
  val combinedAB: Future[Option[String]] = futureOptionA.flatMap{
    case Some(s) => futureOptionB(s)
    case _ => Future.successful(None) // case None
  }
  //V2: manual monad
  val combinedAB2: FutureOpt[String] = for {
    a <- FutureOpt(futureOptionA)
    ab <- FutureOpt(futureOptionB(a))
  } yield ab
  //V2b: manual monad
  val combinedAB2desugared: FutureOpt[String] = FutureOpt(futureOptionA).flatMap(a => FutureOpt(futureOptionB(a)).map(ab => ab))
  //V3: cats monad
  val combinedAB3: OptionT[Future, String] = for {
    a <- OptionT(futureOptionA)
    ab <- OptionT(futureOptionB(a))
  } yield ab

  //monad binding
  case class FutureOpt[A](fo: Future[Option[A]]) {
    def map[B](f: A => B): FutureOpt[B] = FutureOpt[B](fo.map(o => o.map(f)))
    def flatMap[B](f: A => FutureOpt[B]): FutureOpt[B] = {
      val newFO: Future[Option[B]] = fo.flatMap(opt => {
        opt match {
          case Some(a) => f(a).fo // WTSyntax
          case None => Future.successful(None)
        }
      })
      FutureOpt(newFO)
    }
  }

  // better monad binding with cats api implementation at typelevel.org

}
