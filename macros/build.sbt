name := "macros"

organization := "com.mondorad"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.5"

resolvers ++= Seq(
  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/",
  "Scalaz Bintray Repo"  at "http://dl.bintray.com/scalaz/releases",
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.3.7",
  "com.typesafe.play" %% "play-test" % "2.3.7" % "test"
)