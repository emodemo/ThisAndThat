package org.pocs

import java.util.UUID
import scala.concurrent.Future

/** based on https://softwaremill.com/free-tagless-compared-how-not-to-commit-to-monad-too-early */
/** another excellent example https://github.com/cb372/free-vs-tagless-final */
object TaglessVsFree {

  case class User(id: UUID, points: Int)

  // basic
  object Initial {
    // the algebra + dsl
    trait UserRepoAlgebra {
      def find(id: UUID): Future[Option[User]]
      def update(u: User): Future[Unit]
    }

    trait Interpreter extends UserRepoAlgebra {
      override def find(id: UUID): Future[Option[User]] =
        /* go and talk to a database */ Future.successful(None) //Future.successful(Option[User].apply(User(id, 0)))
      override def update(u: User): Future[Unit] =
        /* go and talk to a database */ Future.successful(())
    }

    // the program using interpreter
    object Service {
      import scala.concurrent.ExecutionContext.Implicits.global
      def addPoints(repo: UserRepoAlgebra, userId: UUID, pointsToAdd: Int): Future[Either[String, Unit]] = {
        val future = repo.find(userId)
          future.flatMap {
          case None => Future.successful(Left("User not found"))
          case Some(user) =>
            val updated = user.copy(points = user.points + pointsToAdd)
            repo.update(updated).map(_ => Right(()))
        }
      }
    }

    // the execution (i.e. calling the service)
    object Execution {
      val result: Future[Either[String, Unit]] =
        Service.addPoints(new Interpreter {}, UUID.randomUUID(), 10)
    }
    // so we want to get rid of the Future[] in the algebra/dsl
  }

  // tagless, just remove the Future with an F monad
  // my thoughts:
  // The Program is like a services using abstract UserRepo, that can be implemented with Futures, with Try, or else,
  // where each public method is a separate program
  object Tagless {
    // the algebra + dsl
    trait UserRepoAlgebra[F[_]] {
      def find(id: UUID): F[Option[User]]
      def update(user: User): F[Unit]
    }

    // the interpreter is an implementation of the algebra/dsl
    trait FutureInterpreter extends UserRepoAlgebra[Future] {
      override def find(id: UUID): Future[Option[User]] = /* go and talk to a database */ Future.successful(None)
      override def update(u: User): Future[Unit] = /* go and talk to a database */ Future.successful(())
    }

    // the program using the interpreter
    // could be in separate class called Program: class Programs[F[_] : Monad](alg: Algebra[F])
    // The major difference with the original code is that instead of using Future,
    // we have parametrized the UserRepo with the resulting container.
    object Service {
      import cats.Monad
      import cats.implicits._
      def addPoints[F[_] : Monad](repo: UserRepoAlgebra[F], userId: UUID, pointsToAdd: Int): F[Either[String, Unit]] = {
        val f = repo.find(userId)
          f.flatMap {
          case None => implicitly[Monad[F]].pure(Left("User not found"))
          case Some(user) =>
            val updated = user.copy(points = user.points + pointsToAdd)
            repo.update(updated).map(_ => Right(()))
        }
      }
    }

    // the execution
    object Execution {
      import scala.concurrent.ExecutionContext.Implicits.global
      val result: Future[Either[String, Unit]] =
        Service.addPoints(new FutureInterpreter {}, UUID.randomUUID(), 10)
    }
  }

  // free
  // 1 - separate algebra from dsl
  // 2 - the service is implemented as a Free Monad using the DSL
  // where the interpreter function will "map" thru it.
  object FreeMonad {
    // the algebra
    object Algebra {
      sealed trait UserRepoAlgebra[T]
      case class Find(id: UUID) extends UserRepoAlgebra[Option[User]]
      case class Update(user: User) extends UserRepoAlgebra[Unit]
    }

    // the DSL
    object DSL {
      import Algebra._
      import cats.free.Free
      // could use this, but skipped for clarity: type Program[A] = Free[UserRepoAlgebra, A]
      def find(id: UUID): Free[UserRepoAlgebra, Option[User]] = Free.liftF(Find(id))
      def update(user: User): Free[UserRepoAlgebra, Unit] = Free.liftF(Update(user))
    }

    // the interpreter
    // implementing it as a function requires to have one apply method and hence case classes
    // instead if an Interpreter class with many methods as in Tagless
    object Interpreter {
      import Algebra._
      import cats.~>
      val userToFutureInterpreter: UserRepoAlgebra ~> Future = new (UserRepoAlgebra ~> Future) {
        override def apply[A](fa: UserRepoAlgebra[A]): Future[A] = fa match {
          case Find(id) => /* go and talk to a database */ Future.successful(None)
          case Update(user) => /* go and talk to a database */ Future.successful(())
        }
      }
    }

    // the program builds up from the dsl primitives
    // the service is a monad, it does not use the interpreter. the execution maps the interpreter over the monad service
    object Service {
      import DSL._  // instead of calling the repo, the service call the DSL, which return Free[Repo, Either] instead of Monad[Either]
      import Algebra._
      import cats.free.Free
      def addPoints(userId: UUID, pointsToAdd: Int): Free[UserRepoAlgebra, Either[String, Unit]] = {
        val free = find(userId);
        free.flatMap {
          case None => Free.pure(Left("User not found"))
          case Some(user) =>
            val updated = user.copy(points = user.points + pointsToAdd)
            update(updated).map(_ => Right(()))
        }
      }
    }

    object Execution {
      import Interpreter._
      // the execution
      import scala.concurrent.ExecutionContext.Implicits.global
      val result: Future[Either[String, Unit]] =
        Service.addPoints(UUID.randomUUID(), 5).foldMap(userToFutureInterpreter)
      // in case of many interpreters:
        // .foldMap(futureToWhateverInterpeter)
        // where the result will be Whatever[...]
        // and userToFutureInterpreter will call dsl methods from the Free[Whatever, ...]
     // alternatively it could be done with:
       // Coproduct[1stAlgebra, 2ndAlgebra, T] instead of Free
       // val bothInterpreters = AtoBInterpreter or BtoCInterpreter
     // but wat about combining independent interpreters: business, logging, A/B allow etc... in fact they are not quite independent.
    }
  }

  // tagless: controller call service.dostuff(){call repo}
  // free: controller call service.dostuff() and apply repo over it.
  // think of visitor pattern too, Could be seen as some kind of a mix.... maybe

}
