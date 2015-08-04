name := "protobufbodyparser"

organization := "com.mondorad"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Scalaz Bintray Repo"  at "http://dl.bintray.com/scalaz/releases",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.3.10",
  "com.typesafe.play" %% "play-test" % "2.3.10" % "test",
  "com.google.protobuf" % "protobuf-java" % "2.6.1",
  "com.google.code.findbugs" % "jsr305" % "3.0.0"
)


