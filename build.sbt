import sbt.Keys.{libraryDependencies, scalacOptions}

name := "scala_validation"

version := "0.1"


lazy val scalaReflect = Def.setting {
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
}

lazy val application = (project in file("application"))
  .settings(
    resolvers += "jitpack" at "https://jitpack.io",
    scalaVersion := "2.13.5",
    // other settings here
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:experimental.macros",
      "-Ymacro-debug-lite"
    ),
    libraryDependencies += "com.github.ryota0624" % "scala_field" % "v0.0.2",
    Compile / scalacOptions += "-Ymacro-annotations"
  )
