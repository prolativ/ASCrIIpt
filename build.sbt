name := "ASCrIIpt"

version := "1.0"

scalaVersion := "2.11.8"

cancelable in Global := true

val interfaces = project in file("interfaces")
val renderers = project in file("renderers")
val root = (project in file(".")).aggregate(interfaces, renderers)

libraryDependencies += "com.googlecode.lanterna" % "lanterna" % "2.0.0"
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.2.2" % "test"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "jline" % "jline" % "2.12"
libraryDependencies += "com.googlecode.lanterna" % "lanterna" % "2.0.0"
libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-core" % "2.1.0"

libraryDependencies += "org.reflections" % "reflections" % "0.9.10"
libraryDependencies += "default" % "interfaces_2.10" % "0.1-SNAPSHOT"


resolvers += Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)
resolvers += "Local Ivy Repository" at "file://"+Path.userHome.absolutePath+"/.ivy2/local"
