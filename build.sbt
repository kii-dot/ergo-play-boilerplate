import dependencies._
import utils.commonScalacOptions

name := """ergo-play-boilerplate"""
organization := "io.ergo"

version := "1.0"
scalaVersion := "2.12.15"

lazy val commonSettings = List(
  scalacOptions ++= commonScalacOptions,
  scalaVersion := "2.12.15",
  organization := "io.ergo",
  version := "0.1",
  resolvers ++= Seq(
    "Sonatype Releases".at(
      "https://oss.sonatype.org/content/repositories/releases/"
    ),
    "New Sonatype Releases".at(
      "https://s01.oss.sonatype.org/content/repositories/releases/"
    ),
    "New Sonatype Snapshots".at(
      "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    ),
    "SonaType".at("https://oss.sonatype.org/content/groups/public"),
    "Sonatype Snapshots".at(
      "https://oss.sonatype.org/content/repositories/snapshots/"
    ),
    "Bintray".at("https://jcenter.bintray.com/")
  ),
  libraryDependencies ++= Testing ++
    Enumeratum
)

lazy val allConfigDependency = "compile->compile;test->test"

// ===================== Modules ===================== //
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .withId("ergoBackend")
  .settings(commonSettings)
  .settings(moduleName := "ergobackend", name := "ErgoBackend")
  .settings(
    libraryDependencies ++= Circe
  )
  .dependsOn(sampleModule, sampleBot)

// =================== Base Modules ====================== //
// Description  : Base Modules are modules that is at the bottom
//            of the module hierarchy, and normally other modules
//            depends on it more, and have minimal dependencies
//            other than test modules.
//            Think Raw Materials, like Metal, Wood, Stone

// #NOTE Don't add more stuff into commons unless it makes sense
// We would like to shed out the unnecessary stuffs.
//
// What should commons have?
//  Things that are exle specific, but not ergo specific.
lazy val common = utils
  .mkModule("common", "Common")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++=
      Ergo ++
        Testing ++
        HttpDep ++
        DependencyInjection ++
        Edge
  )

// ====================== Feature Modules ===================== //
// Description    : Modules that carries out certain features, and does
//                that job.
//                Think a Business or System, like a Cashier system in a shop
lazy val sampleModule = utils
  .mkModule("sample-module", "SampleModule")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++=
      Ergo ++
        Testing ++
        Edge
  )
  .dependsOn(
    Seq(common)
      .map(_ % allConfigDependency): _*
  )

lazy val sampleBot = utils
  .mkModule("sample-bot", "SampleBot")
  .settings(commonSettings)
  .settings(
    libraryDependencies ++=
      Ergo ++
        Testing
  )
  .dependsOn(
    Seq(common).map(_ % allConfigDependency): _*
  )

// ==== Modules END ==== //

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x                             => MergeStrategy.first
}

assembly / assemblyJarName := s"${name.value}-${version.value}.jar"
