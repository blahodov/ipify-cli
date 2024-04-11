ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "demyst-ipify-cli"
  )

val circeVersion = "0.14.6"
val log4cats = "2.6.0"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-blaze-client" % "1.0.0-M40",
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.typelevel" %% "cats-effect" % "3.5.4",
  "org.typelevel" %% "log4cats-core" % log4cats,
  "org.typelevel" %% "log4cats-slf4j" % log4cats,
  "org.slf4j" % "slf4j-nop" % "2.0.12",
  "org.scalatest" %% "scalatest" % "3.2.18" % Test
)