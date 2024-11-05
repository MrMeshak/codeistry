package com.codeistry

import cats.effect.{IOApp, IO}
import cats.implicits.*
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.Slf4jFactory
import pureconfig.ConfigSource

import com.codeistry.http.routes.HealthRoutes
import com.codeistry.config.*
import com.codeistry.config.syntax.*
import pureconfig.error.ConfigReaderException

given logging: LoggerFactory[IO] = Slf4jFactory.create[IO]

object Application extends IOApp.Simple {

  override def run: IO[Unit] =
    ConfigSource.default.loadF[IO, EmberConfig].flatMap { config =>
      EmberServerBuilder
        .default[IO]
        .withHost(config.host)
        .withPort(config.port)
        .withHttpApp(HealthRoutes[IO].routes.orNotFound)
        .build
        .use(_ => IO.println("Codeistry") *> IO.never)
    }

}
