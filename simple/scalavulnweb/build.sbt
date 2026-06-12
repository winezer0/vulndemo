name := "scalavulnweb"
version := "1.0-SNAPSHOT"
scalaVersion := "2.13.12"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-ws" % "2.9.2",
  "org.scalaj" %% "scalaj-http" % "2.4.2",
  "org.playframework" %% "play-json" % "2.9.4"
)
