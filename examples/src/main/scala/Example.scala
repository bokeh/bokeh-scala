package io.continuum.bokeh
package examples

trait Example extends App {
    case class Config(dev: Boolean = false, quiet: Boolean = false)

    def config: Config = _config
    private var _config: Config = _

    private def parse(): Config = {
        val example = getClass.getSimpleName.stripSuffix("$")
        val parser = new scopt.OptionParser[Config](example) {
            opt[Unit]('d', "dev")
                .action { (_, config) => config.copy(dev=true) }
                .text("enable development resources (uses requirejs)")

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
