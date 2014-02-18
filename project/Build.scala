import sbt._
import Keys._

import sbtassembly.{Plugin=>SbtAssembly}
import org.sbtidea.SbtIdeaPlugin
import com.typesafe.sbt.SbtPgp

object ProjectBuild extends Build {
    override lazy val settings = super.settings ++ Seq(
        organization := "org.continuumio",
        version := "0.1-SNAPSHOT",
        description := "Scala bindings for Bokeh plotting library",
        homepage := Some(url("http://bokeh.pydata.org")),
        licenses := Seq("MIT-style" -> url("http://www.opensource.org/licenses/mit-license.php")),
        scalaVersion := "2.10.3",
        scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature", "-language:_"),
        shellPrompt := { state =>
            "continuum (%s)> ".format(Project.extract(state).currentProject.id)
        },
        cancelable := true,
        resolvers ++= Seq(
            Resolver.sonatypeRepo("releases"),
            Resolver.sonatypeRepo("snapshots"),
            Resolver.typesafeRepo("releases"),
            Resolver.typesafeRepo("snapshots"))
    )

    object Dependencies {
        val scalaio = {
            val namespace = "com.github.scala-incubator.io"
            val version = "0.4.2"
            Seq(namespace %% "scala-io-core" % version,
                namespace %% "scala-io-file" % version)
        }

        val breeze = "org.scalanlp" %% "breeze" % "0.6"

        val shapeless = "com.chuusai" % "shapeless" % "2.0.0-M1" cross CrossVersion.full

        val jopt = "net.sf.jopt-simple" % "jopt-simple" % "4.5"

        val play_json = "com.typesafe.play" %% "play-json" % "2.2.1"

        val opencsv = "net.sf.opencsv" % "opencsv" % "2.3"

        val specs2 = "org.specs2" %% "specs2" % "2.3.8" % "test"

        val reflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }

        val compiler = Def.setting { "org.scala-lang" % "scala-compiler" % scalaVersion.value }

        val paradise = Def.setting { "org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full }

        val quasiquotes = Def.setting { "org.scalamacros" % "quasiquotes" % "2.0.0-M3" cross CrossVersion.full }
    }

    val bokehDir = settingKey[File]("Location of Bokeh library.")
    val runAll = taskKey[Unit]("Run all discovered main classes.")

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
                <url>https://github.com/mattpap/bokeh-scala</url>
                <connection>scm:git:https://github.com/mattpap/bokeh-scala.git</connection>
            </scm>
            <developers>
                <developer>
                    <id>mattpap</id>
                    <name>Mateusz Paprocki</name>
                </developer>
            </developers>
        )
    )

    lazy val commonSettings = Seq(
        bokehDir := file("..") / "bokeh",
        runAll := {
            val results = (discoveredMainClasses in Compile).value.sorted.map { mainClass =>
                val classpath = Attributed.data((fullClasspath in Compile).value)
                val logger = streams.value.log

                val result = (runner in run).value.run(mainClass, classpath, Nil, logger)

                result match {
                    case Some(msg) => logger.error(s"$mainClass: $msg"); Some(mainClass)
                    case None      => logger.success(mainClass);         None
                }
            } flatten

            if (results.nonEmpty) {
                val failures = results.mkString(", ")
                sys.error(s"failed to run: $failures")
            }
        }
    )

    lazy val pgpSettings = SbtPgp.settings

    lazy val ideaSettings = SbtIdeaPlugin.settings

    lazy val assemblySettings = SbtAssembly.assemblySettings ++ {
        import SbtAssembly.AssemblyKeys._
        Seq(test in assembly := {},
            jarName in assembly := "Bokeh.jar",
            target in assembly := target.value / "lib")
    }

    lazy val pluginSettings = pgpSettings ++ ideaSettings ++ assemblySettings

    lazy val bokehSettings = Project.defaultSettings ++ commonSettings ++ pluginSettings ++ {
        Seq(libraryDependencies ++= {
                import Dependencies._
                scalaio ++ Seq(compiler.value, breeze, shapeless, jopt, play_json, opencsv, specs2)
            },
            fork in run := true,
            parallelExecution in Test := false,
            initialCommands in Compile := """
                import scala.reflect.runtime.{universe=>u,currentMirror=>cm}
                import scalax.io.JavaConverters._
                import scalax.file.Path
                import play.api.libs.json.Json
                import org.continuumio.bokeh._
                """)
    }

    lazy val coreSettings = Project.defaultSettings ++ commonSettings ++ {
        Seq(addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full),
            libraryDependencies ++= {
                import Dependencies._
                Seq(reflect.value, quasiquotes.value, shapeless, play_json, specs2)
            })
    }

    lazy val examplesSettings = Project.defaultSettings ++ commonSettings ++ {
        Seq(libraryDependencies ++= {
                import Dependencies._
                Seq(breeze, specs2)
            })
    }

    lazy val bokeh = Project(id="bokeh", base=file("."), settings=bokehSettings) dependsOn(bokehCore) aggregate(bokehCore)
    lazy val bokehCore = Project(id="bokeh-core", base=file("core"), settings=coreSettings)
    lazy val bokehExamples = Project(id="bokeh-examples", base=file("examples"), settings=examplesSettings) dependsOn(bokeh)

    override def projects = Seq(bokeh, bokehCore, bokehExamples)
}
