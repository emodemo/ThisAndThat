name := "hackerranck-scala"

version := "0.1"
scalaVersion := "2.13.0-M4"

scalaSource in Compile := baseDirectory.value / "src"
scalaSource in Test := baseDirectory.value / "test"

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

// targets: compile, test