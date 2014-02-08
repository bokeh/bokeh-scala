import sbt._
import Keys._

import sbtassembly.{Plugin=>SbtAssembly}
import org.sbtidea.SbtIdeaPlugin

object ProjectBuild extends Build {
    override lazy val settings = super.settings ++ Seq(
        organization := "org.continuum",
        version := "0.1-SNAPSHOT",
        description := "Scala bindings for Bokeh plotting library",
        scalaVersion := "2.10.3",
        scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:_"),
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

        val breeze = "org.scalanlp" %% "breeze" % "0.5.2"

        val jopt = "net.sf.jopt-simple" % "jopt-simple" % "4.5"

        val play_json = "com.typesafe.play" %% "play-json" % "2.2.1"

        val specs2 = "org.specs2" %% "specs2" % "2.1.1" % "test"

        val reflect = Def.setting { "org.scala-lang" % "scala-reflect" % scalaVersion.value }

        val compiler = Def.setting { "org.scala-lang" % "scala-compiler" % scalaVersion.value }

        val paradise = Def.setting { "org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full }

        val quasiquotes = Def.setting { "org.scalamacros" % "quasiquotes" % "2.0.0-M3" cross CrossVersion.full }
    }

    val jrebelJar = settingKey[Option[File]]("Location of jrebel.jar")
    val jrebelOptions = settingKey[Seq[String]]("http://manuals.zeroturnaround.com/jrebel/misc/index.html#agent-settings")
    val jrebelCommand = taskKey[Seq[String]]("JVM command line options enabling JRebel")

    val debugPort = settingKey[Int]("Port for remote debugging")
    val debugCommand = taskKey[Seq[String]]("JVM command line options enabling remote debugging")

    lazy val ideaSettings = SbtIdeaPlugin.settings

    lazy val assemblySettings = SbtAssembly.assemblySettings ++ {
        import SbtAssembly.AssemblyKeys._
        Seq(test in assembly := {},
            jarName in assembly := "Bokeh.jar",
            target in assembly := target.value / "lib")
    }

    lazy val pluginSettings = ideaSettings ++ assemblySettings

    lazy val bokehSettings = Project.defaultSettings ++ pluginSettings ++ {
        Seq(libraryDependencies ++= {
                import Dependencies._
                scalaio ++ Seq(compiler.value, breeze, jopt, play_json, specs2)
            },
            fork in run := true,
            initialCommands in Compile := """
                import scala.reflect.runtime.{universe=>u}
                import scalax.io.JavaConverters._
                import scalax.file.Path
                import play.api.libs.json.Json
                import org.continuumio.bokeh._
                """,
            jrebelJar := {
                val jar = Path.userHome / ".jrebel" / "jrebel" / "jrebel.jar"
                if (jar.exists) Some(jar) else None
            },
            jrebelOptions := Seq("-Drebel.load_embedded_plugins=false", "-Drebel.stats=false", "-Drebel.usage_reporting=false"),
            jrebelCommand <<= (jrebelJar, jrebelOptions) map { (jar, options) =>
                jar.map(jar => Seq("-XX:+CMSClassUnloadingEnabled", "-noverify", s"-javaagent:$jar") ++ options) getOrElse Nil
            },
            debugPort := 5005,
            debugCommand <<= (debugPort) map { (port) =>
                Seq("-Xdebug", s"-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=127.0.0.1:$port")
            })
    }

    lazy val macrosSettings = Project.defaultSettings ++ {
        Seq(addCompilerPlugin("org.scalamacros" % "paradise" % "2.0.0-M3" cross CrossVersion.full),
            libraryDependencies ++= {
                import Dependencies._
                Seq(reflect.value, quasiquotes.value, play_json, specs2)
            })
    }

    lazy val examplesSettings = Project.defaultSettings ++ {
        Seq(libraryDependencies ++= {
                import Dependencies._
                Seq(breeze, specs2)
            })
    }

    lazy val bokeh = Project(id="bokeh", base=file("."), settings=bokehSettings) dependsOn(macros)
    lazy val macros = Project(id="macros", base=file("macros"), settings=macrosSettings)
    lazy val examples = Project(id="examples", base=file("examples"), settings=examplesSettings) dependsOn(bokeh)

    override def projects = Seq(bokeh, macros, examples)
}
