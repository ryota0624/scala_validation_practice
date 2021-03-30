import sbt.Keys.{libraryDependencies, scalacOptions}

name := "scala_validation"

version := "0.1"

lazy val scalaReflect = Def.setting {
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
}

lazy val application = (project in file("application"))
  .dependsOn(macroSub)
  .settings(
    scalaVersion := "2.13.5",
    // other settings here
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:experimental.macros",
      "-Ymacro-debug-lite"
    ),
    Compile / scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.3"
    )
  )

lazy val macroSub = (project in file("macro"))
  .dependsOn(validationLibrary)
  .settings(
    scalaVersion := "2.13.5",
    libraryDependencies += scalaReflect.value,
    // other settings here
    Compile / scalacOptions += "-Ymacro-annotations",
    libraryDependencies += "org.scalameta" %% "scalameta" % "4.4.10",
    scalacOptions ++= Seq(
      "-Ymacro-annotations",
      "-language:experimental.macros"
    )
  )

lazy val validationLibrary = (project in file("validationLibrary"))
  .settings(
    scalaVersion := "2.13.5"
  )
