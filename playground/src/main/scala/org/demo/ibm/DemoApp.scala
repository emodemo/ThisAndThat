package org.demo.ibm

//import scala.concurrent.duration.Duration
//import scala.concurrent.{Await, ExecutionContext, Future}
//
//import cats._
//import cats.implicits._
//import cats.data._
//import cats.syntax.semigroup

// IBM: Model-first microservices with Scala and Cats
// has errors in the example

object DemoApp {

//  implicit val ec = ExecutionContext.Implicits.global
//  val query = "Mathias K"
//  val search: Future[Unit] = Api.findPublications(query).map{
//    authorPublications => renderResponse(200, s"Found $authorPublications")
//  }
//  Await.result(search, Duration.Inf)
//
//  val search_v2: Future[Unit] = Api.findPublicationsWithCats_v2(query).map{
//    authorPublications => renderResponse(200, s"Found $authorPublications")
//  } recover {
//    case InvalidQuery => renderResponse(400, s"Not a valid query: '$query'")
//    case NotFound => renderResponse(404, s"No results found for '$query'")
//  }
//  Await.result(search_v2, Duration.Inf)
//
//
//  //
//  def renderResponse(status: Int, message: String) = ???
//
//}
//
//object Api {
//  // function here will invoke network I/O and needs to be
//  def findAuthor(query: String): Future[Long] = ???
//  def getAuthor(id: Long): Future[Author] = ???
//  def getPublications(authorId: Long): Future[List[Publication]] = ???
//
//  // DRAWBACK 1 => the following two calls can be parallel
//  // DRAWBACK 2 => although Future comes with inbuilt Try(Success, Failure),
//  // but in a domain problem exceptions should be avoided.
//  // Instead pass proper values that will not be treated exceptionally
//  def findPublications(query: String): Future[AuthorPublications] =
//    for {
//      authorId <- findAuthor(query)
//      author <- getAuthor(authorId)
//      publications <- getPublications(authorId)
//    } yield AuthorPublications(author, publications)
//
//  // DRAWBACK 1 fix is to join the two futures into a cartesian product => run in parallel
//  def findPublicationsWithCats(query: String): Future[AuthorPublications] =
//    for {
//      authorId <- findAuthor(query)
//      // (author, publications) <- getAuthor(authorId) product getPublications(authorId)
//      (author, publications) <- Semigroupal[Future].product(getAuthor(authorId), getPublications(authorId))
//    } yield AuthorPublications(author, publications)
//
//  // DRAWBACK 2 fix // Not compilable as in the article?
//  // Either is Right-Based Monad => can be transformed with flatMap and map
//  def findPublicationsWithCats_v2(query: String) =
//    for {
//      authorId <- findAuthor(query)
//      //(author, publications) <- getAuthor(authorId) product getPublications(authorId)
//      (author, publications) <- Semigroupal[Future].product(getAuthor(authorId), getPublications(authorId))
//    } yield AuthorPublications(author, publications)
//  // bind Future and ServiceError together while leaving the inner result type unbound
//  //type Result[A] = EitherT[Future, ServiceError, A]
//  // this allows you to invoke the companion object as "Result"
//  //val Result = EitherT
//  def findAuthor_v2(query: String) = {
//    if(query == "Mathias K") EitherT.rightT(42L)
//    else if (query.isEmpty) EitherT.leftT(InvalidQuery)
//    else EitherT.leftT(NotFound)
//  }
//  def getAuthor_v2(id: Long) =  EitherT.rightT(Author(id, "Mathias K"))
//  def getPublications_v2(authorId: Long) = EitherT.rightT(List(Publication(1L, authorId, "some title")))

}

// Model
case class Author(id: Long, name: String)
case class Publication(id: Long, authorId: Long, title: String)
case class AuthorPublications(author: Author, publications: List[Publication])

// extends Throwable not in the example
sealed trait ServiceError extends Throwable
case object InvalidQuery extends ServiceError
case object NotFound extends ServiceError