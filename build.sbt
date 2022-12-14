ThisBuild / organization := "rs.novalite"
ThisBuild / scalaVersion := "2.13.10"
ThisBuild / scalafmtOnCompile := true

val setScalaSource = Compile / scalaSource := baseDirectory.value / "src"
val setTestSource = Test / scalaSource := baseDirectory.value / "test"
val setResourcesDirectory = Compile / resourceDirectory := baseDirectory.value / "resources"
val setTestResourcesDirectory = Test / resourceDirectory := baseDirectory.value / "test/resources"

lazy val root = (project in file("."))
  .settings(
    name := "scala-workshop-2022",
    setScalaSource,
    setTestSource,
    setResourcesDirectory,
    setTestResourcesDirectory,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.9.3",
      "com.typesafe.play" %% "play-json-joda" % "2.9.3",
      "joda-time" % "joda-time" % "2.10.8",
      "org.apache.commons" % "commons-lang3" % "3.12.0",
      "com.github.tototoshi" %% "scala-csv" % "1.3.10",
      "org.scalatest" %% "scalatest" % "3.2.14" % Test
    )
  )