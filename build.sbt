name := "thrift-example"

version := "0.1"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-Xmax-classfile-name", "100")

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "com.twitter"        %% "finagle-thrift" % "6.33.0",
  "com.twitter" %% "scrooge-core" % "3.16.3",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.2" % "test"
  )
com.twitter.scrooge.ScroogeSBT.newSettings