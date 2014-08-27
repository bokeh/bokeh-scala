package io.continuum.bokeh
package examples

import scopt.{OptionParser,Read}

private object CustomReads {
    implicit val resourcesReads: Read[Resources] = Read.reads { string =>
        Resources.fromString(string) getOrElse {
            throw new IllegalArgumentException(s"'$string' is not a valid resource mode.")
        }
    }
}

trait Example extends App {
    case class Config(resources: Resources = Resources.default, quiet: Boolean = false)

    def config: Config = _config
    private var _config: Config = _

    private def parse(): Config = {
        val example = getClass.getSimpleName.stripSuffix("$")
        val parser = new scopt.OptionParser[Config](example) {
            import CustomReads._

            opt[Resources]('r', "resources")
                .action { (resources, config) => config.copy(resources=resources) }
                .text("configure access to external resources")

            opt[Unit]('d', "dev")
                .action { (_, config) =>
                    IdGenerator.setImplementation(CounterGenerator, silent=true)
                    config.copy(resources=Resources.AbsoluteDev)
                }
                .text("enable development mode")

            opt[Unit]('q', "quiet")
                .action { (_, config) => config.copy(quiet=true) }
                .text("don't print messages to the terminal")

            help("help") text("prints this usage text")
        }

        parser.parse(args, Config()) getOrElse { sys.exit(1) }
    }

    override def delayedInit(body: => Unit) {
        val fn = () => _config = parse()
        super.delayedInit({ fn(); body })
    }

    def info(text: => String) {
        if (!config.quiet) println(text)
    }
}
