package com.codeistry

import cats.effect.{IOApp, IO}
import cats.effect.IO
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.Slf4jFactory
import com.codeistry.http.routes.HealthRoutes

given logging: LoggerFactory[IO] = Slf4jFactory.create[IO]

object Application extends IOApp.Simple {

  override def run: IO[Unit] = EmberServerBuilder
    .default[IO]
    .withHttpApp(HealthRoutes[IO].routes.orNotFound)
    .build
    .use(_ => IO.println("Codeistry") *> IO.never)
}
