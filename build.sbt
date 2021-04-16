import sbt.Keys.{libraryDependencies, scalacOptions}

name := "scala_validation"

version := "0.1"

lazy val scalaReflect = Def.setting {
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
}

lazy val application = (project in file("application"))
  .dependsOn(fieldOfMacro)
  .settings(
    scalaVersion := "2.13.5",
    // other settings here
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:experimental.macros",
      "-Ymacro-debug-lite"
    ),
    Compile / scalacOptions += "-Ymacro-annotations"
  )


lazy val fieldOfMacro = (project in file("field_of_macro"))
  .settings(
    scalaVersion := "2.13.5",
    libraryDependencies += scalaReflect.value,
    // other settings here
    Compile / scalacOptions += "-Ymacro-annotations",
    libraryDependencies += "org.scalameta" %% "scalameta" % "4.4.10",
    libraryDependencies += "com.github.dwickern" %% "scala-nameof" % "3.0.0",
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:experimental.macros"
    )
  )

