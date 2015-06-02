import sbt._
import Keys._

object BokehJS {
    object BokehJSKeys {
        val bokehjsVersion = taskKey[String]("BokehJS version as obtained from src/coffee/main.coffe")
        val bokehjsProps = taskKey[Seq[File]]("Write BokehJS configuration to bokehjs.properties")
    }

    import BokehJSKeys._

    lazy val bokehjsSettings = Seq(
        sourceDirectory in Compile := baseDirectory.value / "src",
        bokehjsVersion <<= Def.task {
            val srcDir = sourceDirectory in Compile value
            val jsMain = srcDir / "coffee" / "main.coffee"
            val regex = """^\s*Bokeh.version = '(.*)'\s*$""".r
            IO.readLines(jsMain) collectFirst {
                case regex(version) => version
            } getOrElse {
                sys.error(s"Unable to read BokehJS version from $jsMain")
            }
        },
        bokehjsProps in Compile <<= Def.task {
            val resDir = resourceManaged in Compile value
            val outFile = resDir / "bokehjs.properties"
            val version = bokehjsVersion value
            val props = s"bokehjs.version=$version\n"
            IO.write(outFile, props)
            Seq(outFile)
        },
        resourceGenerators in Compile <+= bokehjsProps in Compile)
}
