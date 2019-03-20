package org.demo.categories

import cats.Monad
import cats.data.OptionT
import cats.implicits._

import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * (video) Monad transformers down to earth by Gabriele Petronella
 * https://blog.buildo.io/monad-transformers-for-the-working-programmer-aa7e981190e7
 */
object ScalaWithoutCats {

  // FUNCTIONS
  println("===== FUNCTIONS =====")
  val toDouble: Int => Double = (x: Int) => x.toDouble
  val square: Double => Double = (x: Double) => x * x
  val composed1: Int => Double = toDouble map square // from cats
  val composed2: Int => Double = toDouble andThen square
  val composed3: Int => Double = square compose toDouble // reverse composition order
  val composed4: Int => Double = (x: Int) => square(toDouble(x))
  val composed5: Int => Double = toDouble map square map square
  val composed6: Int => Double = toDouble andThen square andThen square
  assert(composed1(2) == composed2(2))
  assert(composed1(2) == composed3(2))
  assert(composed1(2) == composed4(2))
  assert(composed5(2) == composed6(2))

  // EXAMPLES
  def getCity1(name: String): Future[String] = getUser(name).flatMap(u => getAddress(u).map(a => a.city))
  def getCity2(name: String): Future[String] = for{
    u <- getUser(name)
    a <- getAddress(u)
  } yield a.city
  // MONADS DO NOT COMPOSE GENERICALLY, BUT SPECIFICALLY
  // MONAD TRANSFORMERS ARE A TOOL FOR WORKING WITH STACKED MONADS
  // MONAD TRANSFORMERS usually end with T => Foo[Bar[T]] becomes BarT[Foo, T].
  // alternatively use extensible effects API EFF-CATS
  // here there are two functors: Future and Option that are composed into one OptionT, with the help of monad ???
  def getCity3(name: String): OptionT[Future, String] = for {
    u <- OptionT(getUser3(name))
    a <- OptionT(getAddress3(u))
  } yield a.city
  val city3: Future[Option[String]] = getCity3("Emo").value

  // for clarification
  def get1(name: String): Future[Address] = getUser(name).flatMap(u => getAddress(u))
  def get2(name: String): Future[String] = getUser(name).flatMap(u => getAddress(u).flatMap(a => Future(a.city)))
  def get3(name: String): Future[String] = getUser(name).flatMap(u => getAddress(u).map(a => a.city))
  def get4(name: String): Future[Future[Address]] = getUser(name).map(u => getAddress(u))
  def get5(name: String): Future[Future[String]] = getUser(name).map(u => getAddress(u).map(a => a.city))

  // UTILITIES:
  val users = List(new User("Emo", new Address("Sofia")), new User("Nemo", new Address("Sofia")))
  // return Future[A]
  def getUser(name: String): Future[User] = Future(users.filter(u => u.name == name).head)
  def getAddress(user: User): Future[Address] = Future(user.address)
  // return Future[Option[A]]
  def getUser3(name: String): Future[Option[User]] = Future(Some(users.filter(u => u.name == name).head))
  def getAddress3(user: User): Future[Option[Address]] = Future(Some(user.address))

  class Address(val city: String)
  class User(val name: String, val address: Address)
  case class FutureOption[A](value: Future[Option[A]]) // or OptionT[F[_], A], where F[_] can be any monad

}
