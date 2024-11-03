package com.codeistry.foundations

object Cats {

  /*
    type classes
    - Applicative
    - Functor
    - Flatmap
    - Monad
    - ApplicativeError / MonadError
   */

  // Functor - "mappable" structures
  trait MyFunctor[F[_]] {
    def map[A, B](x: F[A])(f: A => B): F[B]
  }

  import cats.Functor
  import cats.instances.list.*
  val listFunctor = Functor[List]

  val mappedList = listFunctor.map(List(1, 2, 3))(_ + 2)

  import cats.syntax.functor.*
  def increment[F[_]](container: F[Int])(using functor: Functor[F]) = {
    container.map(_ + 1)
  }

  // Applicative
  trait MyApplicative[F[_]] extends Functor[F] {
    def pure[A](value: A): F[A]
  }

  import cats.Applicative
  val applicativeList = Applicative[List]
  val aSimpleList = applicativeList.pure(42)
  import cats.syntax.applicative.*
  val aSimpleList_v2 = 42.pure[List]

  // flatmap
  trait MyFlatMap[F[_]] extends Functor[F] {
    def flatMap[A, B](x: F[A])(f: A => F[B]): F[B]
  }

  import cats.FlatMap
  val flatmapList = FlatMap[List]
  val aSimpleListv3 = flatmapList.flatMap(List(1, 2, 3))(a => List(a, a + 1))
  import cats.syntax.flatMap.*
  val aSimpleListv4 = List(1, 2, 3).flatMap(a => List(a))
  def crossProduct[F[_]: FlatMap, A, B](containerA: F[A], containerB: F[B]) = {
    containerA.flatMap(a => containerB.map(b => (a, b)))
  }

  // Monad -  applicative + flatmap
  trait MyMonad[F[_]] extends Applicative[F] with FlatMap[F] {
    override def map[A, B](fa: F[A])(f: A => B) = flatMap(fa)(a => pure(f(a)))
  }

  import cats.Monad
  import cats.syntax.monad.*
  val monadList = Monad[List]
  def crossProduct_v2[F[_]: Monad, A, B](containerA: F[A], containerB: F[B]) = {
    for {
      a <- containerA
      b <- containerB
    } yield (a, b)
  }

  // applicative-error - computations that can fail
  trait MyApplicativeError[F[_], E] extends Applicative[F] {
    def raiseError[A](error: E): F[A]
  }

  import cats.ApplicativeError

  type ErrorOr[A] = Either[String, A]
  val applicativeError = ApplicativeError[ErrorOr, String]
  val desiredValue: ErrorOr[Int] = applicativeError.pure(42)
  val failedValue: ErrorOr[Int] = applicativeError.raiseError("Something bad")
  import cats.syntax.applicativeError.*
  val failedValue_v2: ErrorOr[Int] = "something bad happend".raiseError

  trait MyMonadError[F[_], E] extends ApplicativeError[F, E] with Monad[F]

  def main(args: Array[String]): Unit = {}
}
