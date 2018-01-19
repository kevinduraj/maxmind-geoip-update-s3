name := "geoip-update"

organization := "kduraj"

version := "0.1.0"

scalaVersion := "2.11.11"

crossScalaVersions := Seq("2.10.4", "2.11.2")

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.236",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"
)

initialCommands := "import kduraj._"
