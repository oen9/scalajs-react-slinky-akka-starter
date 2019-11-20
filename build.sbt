val LogbackVersion = "1.2.3"
scalaVersion := "2.12.10"

import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val sharedSettings = Seq(
  organization := "oen",
  scalaVersion := "2.12.10",
  version := "0.1.0-SNAPSHOT",
  libraryDependencies ++= Seq(
    "com.lihaoyi" %%% "scalatags" % "0.7.0",
    "org.typelevel" %% "cats-core" % "1.6.1",
    "io.circe" %%% "circe-generic" % "0.11.1",
    "io.circe" %%% "circe-literal" % "0.11.1",
    "io.circe" %%% "circe-generic-extras" % "0.11.1",
    "io.circe" %%% "circe-parser" % "0.11.1",
    "io.scalaland" %%% "chimney" % "0.3.2",
    "com.softwaremill.quicklens" %%% "quicklens" % "1.4.12"
  ),
  scalacOptions ++= Seq(
    "-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature",
    "-Ypartial-unification",
    "-language:higherKinds"
  )
)

lazy val jsSettings = Seq(
  libraryDependencies ++= Seq(
    "me.shadaj" %%% "slinky-web" % "0.6.3",
    "com.lambdaminute" %%% "slinky-wrappers-react-router" % "0.4.1",
    "io.suzaku" %%% "diode" % "1.1.5"
  ),
  npmDependencies in Compile ++= Seq(
    "react" -> "16.12.0",
    "react-dom" -> "16.12.0",
    "react-popper" -> "1.3.6",
    "react-router-dom" -> "5.1.2",
    "path-to-regexp" -> "6.0.0",
    "bootstrap" -> "4.3.1",
    "jquery" -> "3.4.1"
  ),
  scalaJSUseMainModuleInitializer := true,
  localUrl := ("0.0.0.0", 12345),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
  version.in(webpack) := "4.41.2",
  webpackBundlingMode := BundlingMode.LibraryAndApplication(),
  webpackBundlingMode.in(fastOptJS) := BundlingMode.LibraryOnly(),
  scalacOptions += "-P:scalajs:sjsDefinedByDefault"
)

lazy val jvmSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-effect" % "1.4.0",
    "com.typesafe.akka" %% "akka-http"   % "10.1.9",
    "com.typesafe.akka" %% "akka-stream" % "2.5.24",
    "de.heikoseeberger" %% "akka-http-circe" % "1.27.0",
    "ch.megard" %% "akka-http-cors" % "0.4.1",
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.24",
    "ch.qos.logback" % "logback-classic" % LogbackVersion
  ),
  target := baseDirectory.value / ".." / "target"
)

lazy val app =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Full).in(file("."))
    .settings(sharedSettings)
    .jsSettings(jsSettings)
    .jvmSettings(jvmSettings)

lazy val appJS = app.js
  .enablePlugins(WorkbenchPlugin)
  .enablePlugins(ScalaJSBundlerPlugin)
  .disablePlugins(RevolverPlugin)

lazy val appJVM = app.jvm
  .enablePlugins(JavaAppPackaging)
  .settings(
    dockerExposedPorts := Seq(8080),
    dockerBaseImage := "oracle/graalvm-ce:19.2.1",
    (unmanagedResourceDirectories in Compile) += (resourceDirectory in(appJS, Compile)).value,
    mappings.in(Universal) ++= webpack.in(Compile, fullOptJS).in(appJS, Compile).value.map { f =>
      f.data -> s"assets/${f.data.getName()}"
    },
    mappings.in(Universal) ++= Seq(
      (target in(appJS, Compile)).value / ("scala-" + scalaBinaryVersion.value) / "scalajs-bundler" / "main" / "node_modules" / "bootstrap" / "dist" / "css" / "bootstrap.min.css" -> "assets/bootstrap.min.css"
    ),
    bashScriptExtraDefines += """addJava "-Dassets=${app_home}/../assets""""
  )

disablePlugins(RevolverPlugin)
