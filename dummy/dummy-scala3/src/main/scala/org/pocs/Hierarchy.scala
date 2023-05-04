package org.pocs

object Hierarchy extends App {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    /** defines a neutral element for the "combine" operation */
    def empty: A
  }

  /*

  Semigroup -> Monoid
  combine()   empty()


  Functor      Semigroup
  map()        product()
       \       /
         Apply
         ap(),
         @impl product()
       /       \
  Applicative   FlatMap
  pure()        flatmap
  @impl         @impl
  map(ap, pure) ap(flatmap, map)
       \       /
         Monad
      // flatten (my note)( the actual actual monoid-like operation over functors)
      @impl map(flatmap, pure)
           |
        MyMonad
        // @impl flatten (my note)
        @impl pure
        @impl flatmap
  */

  /** a capability of mapping things (chain computations)
   * more general here, but more powerful in FlatMap */
  trait Functor[F[_]]{
    def map[A, B](fa: F[A])(f: A => B): F[B]

    def myNotes(): Unit = {
      /** example - here the implementation is almost identical */
      def do10xList(list: List[Int]): List[Int] = list.map(_ * 10)
      def do10xOption(option: Option[Int]): Option[Int] = option.map(_ * 10)
      /** example - could be abstracted with mapping functionality.
       *  Should be applicable to any wrapper type that has the "map" function
       *  or any wrapper type fo which we can provide "map" function
       *  */
      // def do10xGeneral[F[_]](container: F[Int]): F[Int] = container.map(_ * 10)
      /** but the F[_] above must have the capability of "mapping" iver internal values, hence: */
      def do10xGeneral[F[_]](container: F[Int])(using functor: Functor[F]): F[Int] = {
        functor.map(container)(_ * 10)
      }
      /** same using cats using implicit "given" functor in scope */
      //  import cats.syntax.functor._
      //  def do10xGeneralCats[F[_]](container: F[Int])(using functor: cats.Functor[F]): F[Int] = {
      //    container.map(_ * 10)
      //  }
    }
  }

  /** Cats isolates fundamental behaviour: 1 per trait */

  /** capability to do a CARTESIAN product */
  trait Semigroupal[F[_]]{
    def product[A, B](fa: F[A], fb: F[B]): F[(A, B)]
  }

  /** capability of invoking a wrapped function over a wrapped value
   * rarely used on its own, but the app us useful for implementing other methods.
   * extends Semigroupal: in case one want not only the result cartesian product,
   * but also to apply a function over it and produce an F[C] type */
  trait Apply[F[_]] extends Semigroupal [F] with Functor[F] {
    def ap[A, B](fab: F[A => B], fa: F[A]): F[B]

    /** turn the value of type A into a value of type tuple(A,B) by calling the "app" method */
    override def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = {
      /** a function that takes a value of type A and return another function that takes a value of type B and returns a tuple */
      val toTuple: A => B => (A, B) = (a: A) => (b: B) => (a, b)
      /** then we need to turn the F[A] into F of a function, hence extend Functor */
      val fab: F[B => (A, B)] = map(fa)(toTuple)
      ap(fab, fb)
    }

    /** do a cartesian product and run a function between al possible combinations and produce a result C */
    def mapN[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C] = {
      // product(fa, fb) returns a wrapper of a function that can be transformed by the map method
      // map(product(fa, fb)) ((a,b) => f(a,b)) // short way
      map(product(fa, fb)) { case (a,b) => f(a,b) } // long way
    }
  }

  /** a capability of rising a value A to a wrapped type F[A] */
  trait Applicative[F[_]] extends Apply[F] {  //extends Functor[F]
    def pure[A](a: A): F[A]

    /** comes for free if we have ap */
    override def map[A, B](fa: F[A])(f: A => B): F[B] = {
      ap(pure(f), fa) // although the other impl is clearer to me!!!
    }
  }

  /** a capability of chaining things/computations of any sort */
  trait FlatMap[F[_]] extends Apply[F] { // extends Functor[F]
    /** in cats this method resides in trait FlatMap */
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
    override def ap[A, B](fab: F[A => B], fa: F[A]): F[B] = {
      flatMap(fa)(a => map(fab)(ff => ff(a))) // ME
    }
  }

  /** a capability of chaining things/computations of any sort */
  trait Monad1[F[_]] extends Applicative[F] with FlatMap[F] {
    /** in cats this method resides in trait FlatMap */
    // def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
    /** implemented with flatMap, instead of ap */
    override def map[A, B](fa: F[A])(f: A => B): F[B] = {
      // turns "f: A => B" into "f: A=> F[B]"
      // so we need a function that takes "a" into f(a), and rise to to F[B] by calling the "pure" method
      flatMap(fa)(a => pure(f(a)))
    }
  }

  /** examples */
  val numbersList = List(1,2,3)
  val charsList = List('a', 'b', 'c')
  def getPairsList(numbers: List[Int], chars: List[Char]): List[(Int, Char)] = numbers.flatMap(n => chars.map(c => (n, c)))
  println(getPairsList(numbersList, charsList))

//  import cats.Monad
//  import cats.instances.list._
//  val listMonad = Monad[List]
//  def getPairs[F[_], A, B](ma: F[A], mb: F[B], monad: Monad[F]): F[(A, B)] = {
//    monad.flatMap(ma)(a => monad.map(mb)(b => (a,b)))
//  }
//  println(getPairs(numbersList, charsList, listMonad))

  // a bit more detailed as for me, if A, B do not implement the Monad
  class MyTestMonad extends Monad1[List]{
    override def pure[A](a: A): List[A] = List(a)
    // override def ap[A, B](fab: List[A => B], fa: List[A]): List[B] = fa.flatMap(a => fab.map(ff => ff(a)))
    override def flatMap[A, B](fa: List[A])(f: A => List[B]): List[B] = fa.flatMap(f)
  }
  def getPairs1[F[_], A, B](ma: F[A], mb: F[B], monad: Monad1[F]): F[(A, B)] = {
    monad.flatMap(ma)(a => monad.map(mb)(b => (a,b)))
  }
  val listMonad1 = new MyTestMonad
  println(getPairs1(numbersList, charsList, listMonad1))


}
