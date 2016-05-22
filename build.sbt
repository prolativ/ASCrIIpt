name := "ASCrIIpt"

version := "1.0"

scalaVersion := "2.11.8"

cancelable in Global := true

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "com.googlecode.lanterna" % "lanterna" % "2.0.0"
libraryDependencies += "jline" % "jline" % "2.12"

