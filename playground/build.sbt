name := "playground"

version := "0.1"

scalaVersion := "2.12.8"

resolvers ++= Seq(
  // for scalameter
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.5.0"
  ,"org.scalactic" %% "scalactic" % "3.0.5"
  ,"org.scalatest" %% "scalatest" % "3.0.5" % "test"
  // see resolvers too
  ,"com.storm-enroute" %% "scalameter" % "0.8.2"
)

scalacOptions ++= Seq(
  "-Ypartial-unification", // due to Functor Map
  "-language:higherKinds" // for F[_], or import scala.language.higherKinds
)

// used for full scalameter support
//testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
//parallelExecution in Test := false