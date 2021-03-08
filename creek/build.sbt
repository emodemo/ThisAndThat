name := "creek"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
  , "org.graphstream" % "gs-core" % "1.3"
  , "org.graphstream" % "gs-algo" % "1.3"

)
