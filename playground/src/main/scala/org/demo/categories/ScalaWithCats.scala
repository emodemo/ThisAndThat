package org.demo.categories

import cats._
import cats.implicits._
import scala.annotation.tailrec

object ScalaWithCats extends App{

  // SEMIGROUP
  println("===== SEMIGROUP =====")
  Semigroup[Int].combine(1, 2) // 3
  1 |+| 2 |+| 3 // 6
  Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) // List(1,2,3,4,5,6)
  Semigroup[Option[Int]].combine(Option(1), Option(2)) // Some(3)
  Semigroup[Option[Int]].combine(Option(1), None) // Some(1)
  Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6) // 67

  val aMap = Map("foo" -> Map("bar" -> 5))
  val anotherMap = Map("foo" -> Map("bar" -> 6))
  val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap) // Map(foo -> Map(bar -> 11)
  combinedMap.get("foo") // Some(Map(bar -> 11))

  // 3 implementations of combine
  def combine(items: List[Int]): Int = items.foldLeft(Monoid[Int].empty)(_ combine _)
  def combine2[A](items: List[A])(implicit monoid: Monoid[A]): A = items.foldLeft(monoid.empty)(_ combine _)
  def combine3[A: Monoid](items: List[A]): A = items.foldLeft(Monoid[A].empty)(_ combine _)

  // MONOID
  println("===== MONOID =====")
  Monoid[String].empty
  1 |+| 2 |+| 3 |+| Monoid[Int].empty // 6
  Monoid[String].combineAll(List("a", "b", "c")) // abc
  Monoid[Map[String, Int]].combineAll(List(Map("a" -> 1, "b" -> 2), Map("a" -> 3))) // Map(a->4, b->2)

  // FUNCTOR
  println("===== FUNCTOR =====")
  Functor[Option].map(Option("Hello"))(_.length)
  Functor[Option].map(None: Option[String])(_.length)
  // lift a function A=>B to F[A]=>F[B]
  val liftedOpt: Option[String] => Option[Int] = Functor[Option].lift(_.length)
  liftedOpt(Some("Hello")) // Some(5)
  // fproduct pairs a value with the result of applying a function to that value, a tuple with (value, result).
  val product = Functor[List].fproduct(List("Cats", "is", "awesome"))(_.length).toMap
  product.getOrElse("Cats", 0)
  // compose
  val listOpt = Functor[List] compose Functor[Option] // something like Functor[List[Option]]
  val plusOne = (x: Int) => x + 1
  listOpt.map(List(Some(1), None, Some(3)))(plusOne) // List(Some(2), None, Some(4))
  // Identity Monad
  Functor[Id].map("a")(_ + "b") // ab

  // APPLY
  println("===== APPLY =====")
  val listOpt2 = Apply[List] compose Apply[Option]
  // result is same as map
  listOpt2.ap(List(Some(plusOne)))(List(Some(1), None, Some(3))) // List(Some(2), None, Some(4))
  Apply[Option].ap(Some((x: String) => x.length()))(Some("Hello"))

  val add2Int = (a: Int, b: Int) => a + b
  Apply[Option].map2(Some(1), Some(2))(add2Int) // Some(3)
  Apply[Option].ap2(Some(add2Int))(Some(1), Some(2)) // Some(3)
  Apply[Option].map2(Some(1), None)(add2Int) // None
  Apply[Option].ap2(Some(add2Int))(Some(1), None) // None
  Apply[Option].tuple2(Some(1), Some(2)) // Some((1,2))

  val add3Int = (a: Int, b: Int, c: Int) => a + b + c
  Apply[Option].ap3(Some(add3Int))(Some(1), Some(2), Some(3)) // Some(6)
  Apply[Option].map3(Some(1), Some(2), Some(3))(add3Int) // Some(6)
  Apply[Option].tuple3(Some(1), Some(2), Some(3)) // Some((1,2,3))

  // APPLICATIVE
  println("===== APPLICATIVE =====")
  Applicative[Option].pure(1) // Some(1)

  Applicative[List] compose Applicative[Option]

  // MONAD
  println("===== MONAD =====")
  val list = List(List(1), List(2, 3)).flatten
  val list2 = List(1).combine(List(2, 3))
  assert(list == list2)
  Monad[List].flatMap(list)(x => List(x, x)) // List(1, 1, 2, 2, 3, 3)
  Monad[Option].flatMap(Some(1)) (x => Some(x + 1)) // Some(2)
  // no compose !!! becomes an Applicative
  Monad[List] compose Monad[Option] // same as Applicative compose
  // => compose Outer Monad with Inner Monad => see OptionT

  // FOLDABLE, TRAVERSABLE, ...
  println("===== FOLDABLE, TRAVERSABLE, ... =====")

  // EITHER, VALIDATED, ...
  println("===== EITHER, VALIDATED =====")
  // In general, Validated is used to accumulate errors,
  // while Either is used to short-circuit a computation upon the first error.

}
