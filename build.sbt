name := "secSpider"

version := "0.1.0-SNAPSHOT"

organization := "com.kingdee.safe"

scalaVersion := "2.11.6"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.9",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.9",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "1.1.4"
libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"

