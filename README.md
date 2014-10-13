# bokeh-scala

Scala bindings for [Bokeh][bokeh] plotting library.

[![Build Status][travis]](https://travis-ci.org/bokeh/bokeh-scala)

## Requirements

* [Java](http://wwww.java.com) JRE 1.6+

## Usage

Binary release artifacts are published to Sonatype OSS and synced to Maven
Central. To include latest stable version of bokeh in your project, use:

```scala
libraryDependencies += "io.continuum.bokeh" %% "bokeh" % "0.2"
```

Development snapshots are available as well. To use this you have to add
Sonatype's snapshots repository to your build:

```scala
resolvers += Opts.resolver.sonatypeSnapshots
libraryDependencies += "io.continuum.bokeh" %% "bokeh" % "0.3-SNAPSHOT"
```

See [this][sample] sample project for detailed integration example.

## Documentation

API docs are available for [stable][api-stable] and [development][api-devel]
versions.

## Development

bokeh uses submodules, so either clone with `--recursive` or use
`git submodule update`.

```bash
$ git clone --recursive https://github.com/bokeh/bokeh-scala.git
$ cd bokeh-scala
$ ./sbt
> examples/runMain io.continuum.bokeh.examples.glyphs.Anscombe
```
or
```bash
> examples/runAll
```

## Support

Submit any questions and suggestions to Bokeh's [mailing list][group].

## License

Copyright &copy; 2014 by Mateusz Paprocki and contributors.

Published under [The MIT License][license], see LICENSE.

[bokeh]: http://bokeh.pydata.org
[sample]: https://github.com/bokeh/bokeh-scala-sample
[api-stable]: https://s3.amazonaws.com/bokeh-scala/docs/2.11/0.2/index.html
[api-devel]: https://s3.amazonaws.com/bokeh-scala/docs/2.11/0.3-SNAPSHOT/index.html
[group]: https://groups.google.com/a/continuum.io/forum/#!forum/bokeh
[travis]: https://api.travis-ci.org/bokeh/bokeh-scala.png?branch=master
[license]: http://www.opensource.org/licenses/mit-license.php
