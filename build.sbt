val scala3Version = "3.5.2"
val http4sVersion = "1.0.0-M43"

lazy val root = project
  .in(file("."))
  .settings(
    name := "codeistry",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.http4s" %% "http4s-ember-server" % http4sVersion,
      "org.http4s" %% "http4s-dsl" % http4sVersion,
      "org.typelevel" %% "log4cats-slf4j" % "2.7.0",
      "org.slf4j" % "slf4j-nop" % "2.1.0-alpha1"
    )
  )
