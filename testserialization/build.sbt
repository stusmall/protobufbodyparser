import sbtprotobuf.{ProtobufPlugin=>PB}

seq(PB.protobufSettings: _*)

organization := "com.mondorad"

name := "testserialization"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

