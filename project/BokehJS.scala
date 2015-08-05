import sbt._
import Keys._

import scala.util.Try

object FilePickle {
    import upickle._

    implicit val fileWriter = Writer[File] {
        case file => Js.Str(file.getPath)
    }
    implicit val fileReader = Reader[File] {
        case Js.Str(path) => new File(path)
    }
}

class Cache(cacheFile: File, fileFilter: PathFinder) {
    def sync(fn: Seq[File] => Unit) {
        val files = fileFilter.get

        val cache = readCache().toMap
        val newCache = files.map { file => file -> file.lastModified }

        val removed = cache.keySet -- files.toSet toSeq
        val modified = files.filter { file =>
            cache.get(file) match {
                case Some(lastModified) => file.lastModified > lastModified
                case None               => true // this is a new file
            }
        }

        fn(removed ++ modified)
        writeCache(newCache)
    }

    import FilePickle._

    protected def readCache(): Seq[(File, Long)] = {
        if (cacheFile.exists) {
            upickle.read[Seq[(File, Long)]](IO.read(cacheFile))
        } else {
            Seq.empty
        }
    }

    protected def writeCache(cache: Seq[(File, Long)]) {
        IO.write(cacheFile, upickle.write(cache))
    }
}

object BokehJS {
    object BokehJSKeys {
        val nodeBinary = taskKey[String]("Detected node.js binary, either node or nodejs")
        val bokehjsUpdate = taskKey[Unit]("Resolve BokehJS dependencies (i.e. run `npm install`)")
        val bokehjsVersion = taskKey[String]("BokehJS version as obtained from src/coffee/main.coffe")
        val bokehjsProps = taskKey[Seq[File]]("Write BokehJS configuration to bokehjs.properties")
        val bokehjsSources = settingKey[PathFinder]("BokehJS source file filter")
        val bokehjsBuildDir = settingKey[File]("BokehJS target build directory")
        val bokehjsBuild = taskKey[Unit]("Build BokehJS using gulp build system")
    }

    import BokehJSKeys._

    lazy val bokehjsSettings = Seq(
        nodeBinary <<= Def.task {
            val nodes = "node" :: "nodejs" :: Nil
            nodes.view.find(node => Try { s"$node --version" !! }.isSuccess) getOrElse {
                sys.error("could not detect node.js on this system")
            }
        },
        sourceDirectory in Compile := baseDirectory.value / "src",
        unmanagedResourceDirectories in Compile += baseDirectory.value / "build",
        bokehjsUpdate in Compile <<= Def.task {
            val prefix = baseDirectory.value
            val log = streams.value.log
            val ret = s"npm install --prefix=$prefix --spin=false" ! log
            if (ret != 0) sys.error("npm install failed")
        },
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
        resourceGenerators in Compile <+= bokehjsProps in Compile,
        bokehjsSources in Compile := {
            val all = (sourceDirectory in Compile).value.***
            all.filter(_.isFile).filter(!_.isHidden)
        },
        bokehjsBuildDir in Compile := (classDirectory in Compile).value,
        bokehjsBuild in Compile <<= Def.task {
            def gulp(args: String*) = {
                val node = nodeBinary.value
                val prefix = baseDirectory.value
                val gulp = prefix / "node_modules" / "gulp" / "bin" / "gulp.js"
                val gulpfile = prefix / "gulpfile.js"
                val log = streams.value.log
                val ret = s"$node $gulp --gulpfile $gulpfile ${args.mkString(" ")}" ! log
                if (ret != 0) sys.error("gulp build failed")
            }

            val cacheFile = target.value / "gulp.cache"
            val fileFilter = (bokehjsSources in Compile).value

            val cache = new Cache(cacheFile, fileFilter)

            cache.sync { modified =>
                if (modified.length > 0) {
                    val plural = if (modified.length != 1) "s" else ""
                    streams.value.log.info(s"Running gulp build for ${modified.length} file$plural")
                    val buildDir = (bokehjsBuildDir in Compile).value.getPath
                    gulp("scripts", "styles", "--build-dir", buildDir)

                }
            }
        } dependsOn(bokehjsUpdate in Compile),
        watchSources <++= Def.task { (bokehjsSources in Compile).value.get },
        compile in Compile <<= (compile in Compile).dependsOn(bokehjsBuild in Compile))
}
