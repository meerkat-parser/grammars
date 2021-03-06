
name := "Meerkat Grammars"

organization := "org.meerkat"

version := "0.1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
    "org.meerkat" % "meerkat_2.11" % "0.1.0",
    "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
	"junit" % "junit" % "4.11",
    "com.google.guava" % "guava-testlib" % "18.0",
    "commons-io" % "commons-io" % "2.4",
    "org.bitbucket.inkytonik.dsinfo" %% "dsinfo" % "0.4.0"
)


// SBT Eclipse configuration

EclipseKeys.withSource := true
