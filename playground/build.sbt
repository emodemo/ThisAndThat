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
  // junit test
  //, "com.novocode" % "junit-interface" % "0.8" % "test->default"
  //, "org.graphstream" % "gs-core" % "1.3"
  //, "org.graphstream" % "gs-algo" % "1.3"
  // see resolvers too
  ,"com.storm-enroute" %% "scalameter" % "0.8.2"
  // breeze
  , "org.scalanlp" %% "breeze" % "1.0-RC2" // gets also macros and shapeless
  , "org.scalanlp" %% "breeze-natives" % "1.0-RC2"
  , "org.jblas" % "jblas" % "1.2.4" // may not need
  , "com.googlecode.netlib-java" % "netlib-java" % "1.1" // may not need
  , "com.github.fommil.netlib" % "all" % "1.1.2"
  
)

scalacOptions ++= Seq(
  "-Ypartial-unification", // due to Functor Map
  "-language:higherKinds" // for F[_], or import scala.language.higherKinds
)

// used for full scalameter support
//testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")
//parallelExecution in Test := false