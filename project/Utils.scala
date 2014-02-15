import sbt._
import Keys._

object JRebel {
    object Keys {
        val jrebelJar = settingKey[Option[File]]("Location of jrebel.jar")
        val jrebelOptions = settingKey[Seq[String]]("http://manuals.zeroturnaround.com/jrebel/misc/index.html#agent-settings")
        val jrebelCommand = taskKey[Seq[String]]("JVM command line options enabling JRebel")
    }

    import Keys._

    def defaultSettings = Seq(
        jrebelJar := {
            val jar = Path.userHome / ".jrebel" / "jrebel" / "jrebel.jar"
            if (jar.exists) Some(jar) else None
        },
        jrebelOptions := Seq("-Drebel.load_embedded_plugins=false", "-Drebel.stats=false", "-Drebel.usage_reporting=false"),
        jrebelCommand <<= (jrebelJar, jrebelOptions) map { (jar, options) =>
            jar.map(jar => Seq("-XX:+CMSClassUnloadingEnabled", "-noverify", s"-javaagent:$jar") ++ options) getOrElse Nil
        }
    )
}

object Debug {
    object Keys {
        val debugPort = settingKey[Int]("Port for remote debugging")
        val debugCommand = taskKey[Seq[String]]("JVM command line options enabling remote debugging")
    }

    import Keys._

    def defaultSettings = Seq(
        debugPort := 5005,
        debugCommand <<= (debugPort) map { (port) =>
            Seq("-Xdebug", s"-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=127.0.0.1:$port")
        }
    )
}
