import sbt._
import Keys._

import scala.util.Try

import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

import com.typesafe.sbt.SbtPgp
import com.typesafe.sbt.JavaVersionCheckPlugin.autoImport._

object Dependencies {
    val scalaio = {
        val namespace = "com.github.scala-incubator.io"
        val version = "0.4.3"
        Seq(namespace %% "scala-io-core" % version,
            namespace %% "scala-io-file" % version)
    }

    val breeze = "org.scalanlp" %% "breeze" % "0.12"

    val upickle = Def.setting { "com.lihaoyi" %%% "upickle" % "0.3.8" }

    val scalatags = Def.setting { "com.lihaoyi" %%% "scalatags" % "0.5.4" }

    val specs2 = "org.specs2" %% "specs2" % "2.3.11" % Test

    val scopt = "com.github.scopt" %% "scopt" % "3.3.0"

    val joda_time = "joda-time" % "joda-time" % "2.9.2"

    val joda_conv = "org.joda" % "joda-convert" % "1.8.1"

    val opencsv = "net.sf.opencsv" % "opencsv" % "2.3"

    val ical4j = "org.mnode.ical4j" % "ical4j" % "1.0.2"

    val reflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }

    val paradise = "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full

    val xml = "org.scala-lang.modules" %% "scala-xml" % "1.0.2"
}

object BokehBuild extends Build {
    override lazy val settings = super.settings ++ Seq(
        organization := "io.continuum.bokeh",
        description := "Scala bindings for Bokeh plotting library",
        homepage := Some(url("http://bokeh.pydata.org")),
        licenses := Seq("MIT-style" -> url("http://www.opensource.org/licenses/mit-license.php")),
        scalaVersion := "2.11.7",
        scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature"),
        scalacOptions += "-language:postfixOps,implicitConversions,higherKinds,experimental.macros",
        scalacOptions in (Compile, doc) := Seq("-groups", "-implicits"),
        addCompilerPlugin(Dependencies.paradise),
        shellPrompt := { state =>
            "continuum (%s)> ".format(Project.extract(state).currentProject.id)
        },
        cancelable := true
    )

    val runAll = inputKey[Unit]("Run all discovered main classes.")
    val upload = taskKey[Unit]("Upload scaladoc to S3.")

    lazy val publishSettings = Seq(
        publishTo := {
            val nexus = "https://oss.sonatype.org/"
            if (isSnapshot.value)
                Some("snapshots" at nexus + "content/repositories/snapshots")
            else
                Some("releases" at nexus + "service/local/staging/deploy/maven2")
        },
        publishMavenStyle := true,
        publishArtifact in Test := false,
        pomIncludeRepository := { _ => false },
        pomExtra := (
            <scm>
                <url>https://github.com/bokeh/bokeh-scala</url>
                <connection>scm:git:https://github.com/bokeh/bokeh-scala.git</connection>
            </scm>
            <developers>
                <developer>
                    <id>mattpap</id>
                    <name>Mateusz Paprocki</name>
                    <url>mateuszpaprocki.pl</url>
                </developer>
            </developers>
        ),
        credentials ++= {
            (for {
                username <- sys.env.get("SONATYPE_USERNAME")
                password <- sys.env.get("SONATYPE_PASSWORD")
            } yield {
                Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)
            }) orElse {
                val path = Path.userHome / ".sonatype" / "credentials"
                if (path.exists) Some(Credentials(path)) else None
            } toList
        },
        javaVersionPrefix in javaVersionCheck := Some("1.7")
    )

    lazy val commonSettings = Defaults.coreDefaultSettings ++ publishSettings ++ Seq(
        parallelExecution in Test := false,
        fork := true,
        runAll := {
            val args = Def.spaceDelimited("<args>").parsed
            val results = (discoveredMainClasses in Compile).value.sorted.flatMap { mainClass =>
                val classpath = Attributed.data((fullClasspath in Compile).value)
                val logger = streams.value.log

                val result = (runner in run).value.run(mainClass, classpath, args, logger)

                result match {
                    case Some(msg) => logger.error(s"$mainClass: $msg"); Some(mainClass)
                    case None      => logger.success(mainClass);         None
                }
            }

            if (results.nonEmpty) {
                val failures = results.mkString(", ")
                sys.error(s"failed to run: $failures")
            }
        }
    )

    lazy val bokehSettings = Seq(
        libraryDependencies ++= {
            import Dependencies._
            Seq(xml, upickle.value, scalatags.value, specs2)
        },
        sourceGenerators in Compile += Def.task {
            val cmd = "python" :: "-c" :: "import bokeh; print(bokeh.__version__)" :: Nil
            val proc = Process(cmd, None, "PYTHONPATH" -> "bokehjs/")
            val version = Try { proc !! }.map(_.trim) getOrElse {
                sys.error("could not run python to determine bokeh version")
            }
            val srcDir = (sourceManaged in Compile).value
            val outFile = srcDir / "Version.scala"
            val code = s"""
                |package io.continuum.bokeh
                |
                |object Version {
                |    final val version = "$version"
                |    final override def toString = version
                |}
                """.stripMargin.trim
            IO.write(outFile, code)
            Seq(outFile)
        }.taskValue
    )

    lazy val bokehJVMSettings = commonSettings ++ bokehSettings ++ Seq(
        upload := {
            val local = target in (Compile, doc) value
            val remote = s"s3://bokeh-scala/docs/${scalaBinaryVersion.value}/${version.value}"
            s"aws s3 sync $local $remote --delete --acl public-read" !
        },
        initialCommands in Compile := """import io.continuum.bokeh._"""
    )

    lazy val bokehSJSSettings = Defaults.coreDefaultSettings ++ bokehSettings

    lazy val coreSettings = Seq(
        libraryDependencies ++= {
            import Dependencies._
            Seq(reflect.value, upickle.value, specs2)
        }
    )

    lazy val coreJVMSettings = commonSettings ++ coreSettings

    lazy val coreSJSSettings = Defaults.coreDefaultSettings ++ coreSettings

    lazy val bokehjsSettings = commonSettings ++ BokehJS.bokehjsSettings

    lazy val thirdpartySettings = commonSettings ++ Seq(
        libraryDependencies ++= {
            import Dependencies._
            Seq(joda_time, joda_conv, breeze, reflect.value, upickle.value, specs2)
        }
    )

    lazy val sampledataSettings = commonSettings ++ Seq(
        libraryDependencies ++= {
            import Dependencies._
            scalaio ++ Seq(joda_time, joda_conv, xml, opencsv, ical4j, specs2)
        }
    )

    lazy val examplesSettings = commonSettings ++ Seq(
        libraryDependencies ++= {
            import Dependencies._
            Seq(breeze, scopt, specs2)
        },
        javaOptions ++= {
            val netlib = "com.github.fommil.netlib"
            val linalg = Seq("BLAS", "LAPACK", "ARPACK")
            linalg.map(name => s"-D$netlib.$name=$netlib.F2j$name")
        }
    )

    lazy val allSettings = Seq(
        publishLocal := {},
        publish := {}
    )

    lazy val bokeh = crossProject.crossType(CrossType.Pure).in(file("bokeh"))
        .jvmSettings(bokehSettings: _*).jsSettings(bokehSJSSettings: _*).dependsOn(core)

    lazy val core = crossProject.crossType(CrossType.Pure).in(file("core"))
        .jvmSettings(coreSettings: _*).jsSettings(coreSJSSettings: _*)

    lazy val bokehJVM = bokeh.jvm.dependsOn(bokehjs)
    lazy val bokehSJS = bokeh.js

    lazy val coreJVM = core.jvm
    lazy val coreSJS = core.js

    lazy val bokehjs = project in file("bokehjs/bokehjs") settings(bokehjsSettings: _*)

    lazy val thirdparty = project in file("thirdparty") settings(thirdpartySettings: _*) dependsOn(bokehJVM)
    lazy val sampledata = project in file("sampledata") settings(sampledataSettings: _*) dependsOn(bokehJVM)
    lazy val examples = project in file("examples") settings(examplesSettings: _*) dependsOn(bokehJVM, thirdparty, sampledata)

    lazy val all = project.in(file(".")).disablePlugins(SbtPgp).settings(allSettings: _*)
        .aggregate(bokehJVM, bokehSJS, coreJVM, coreSJS, bokehjs, thirdparty, sampledata, examples)

    override def projects = Seq(bokehJVM, bokehSJS, coreJVM, coreSJS, bokehjs, thirdparty, sampledata, examples, all)
}
