import sbt.Keys._

name := """fraud-detection"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.3",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.14"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"