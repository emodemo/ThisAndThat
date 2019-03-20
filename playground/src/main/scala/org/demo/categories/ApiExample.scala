package org.demo.categories

/**
 * https://nikgrozev.com/2016/03/14/functional-programming-and-category-theory-part-1-categories-and-functors/
 * https://nikgrozev.com/2016/04/11/functional-programming-and-category-theory-part-2-applicative-functors/
 */
class ApiExample

trait MyFunctor[TC[_]] {
  // return another function
  def map[A, B](f: A => B): TC[A] => TC[B]
  // instead of returning another function, directly apply it
  // e.g. map the function and apply it in one go
  def map[A, B](f: A => B)(param: TC[A]): TC[B]
}

// For simplicity, just say that TC is a Functor
trait MySimpleFunctor[A] {
  def map[B](f: A => B): MySimpleFunctor[B]
}

trait MyApplicative[TC[_]] {
  // PURE
  def pure[A](a: A): TC[A]
  // example
  //pure("Text")
  //pure(x: Int => x.toString)
  // APPLY
  def apply[A, B](f: TC[A => B]): TC[A] => TC[B]
  // map the function and apply it in one go
  def apply[A, B](f: TC[A => B])(param: TC[A]): TC[B]
}

trait MyMonad[TC[_]] {
  // functions of type A → F[B] are called Kleisli arrows
}

