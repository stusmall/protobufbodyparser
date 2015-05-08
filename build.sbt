scalaVersion := "2.11.5"

lazy val root =  project.in( file(".") ).aggregate(test, core, macros)

lazy val macros = project.in(file("macros"))

lazy val test = project.in( file("testserialization") )

lazy val core = (project in file("core")).dependsOn(test).dependsOn(macros)

