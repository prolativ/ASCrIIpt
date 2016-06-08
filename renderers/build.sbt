name := "renderers"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "jline" % "jline" % "2.12"
libraryDependencies += "default" % "interfaces_2.10" % "0.1-SNAPSHOT"

resolvers += Resolver.mavenLocal

resolvers += Resolver.url("bintray-sbt-plugins", url("http://dl.bintray.com/sbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)