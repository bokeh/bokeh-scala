# bokeh-scala

Scala bindings for [Bokeh][bokeh] plotting library.

[![Build Status][travis]](https://travis-ci.org/bokeh/bokeh-scala)

## Requirements

* [Java](http://wwww.java.com) JRE 1.7+
* [Scala](http://www.scala-lang.org) 2.10.* or 2.11.*

## Usage

Binary release artifacts are published to Sonatype OSS and synced to [Maven
Central][central]. To include latest stable version of bokeh in your project,
use:

```scala
libraryDependencies += "io.continuum.bokeh" %% "bokeh" % "0.7"
```

Development snapshots are available as well. To use this you have to add
Sonatype's snapshots repository to your build:

```scala
resolvers += Opts.resolver.sonatypeSnapshots
libraryDependencies += "io.continuum.bokeh" %% "bokeh" % "0.8-SNAPSHOT"
```

See [this][sample] sample project for detailed integration example.

## Documentation

API documentation (scaladoc) is available for [stable][api-stable] and
[development][api-devel] versions.

## Development

Install [nodejs][nodejs]. This is required to build bokehjs. Then:

```bash
$ git clone --recursive https://github.com/bokeh/bokeh-scala.git
$ cd bokeh-scala
$ ./sbt
> examples/runMain io.continuum.bokeh.examples.models.Anscombe
```
or
```bash
> examples/runAll
```

## Support

Submit any questions and suggestions to Bokeh's [mailing list][group] and report
any Scala-specific issues in the [issue tracker][issues]. Issues directly related
bokeh or bokehjs, please report in the [bokeh's issue tracker][bokeh-issues].

## License

Copyright &copy; 2014-2016 by Mateusz Paprocki and contributors.

Published under [The MIT License][license], see LICENSE.

[bokeh]: http://bokeh.pydata.org
[central]: http://search.maven.org/#search%7Cga%7C1%7Cbokeh
[sample]: https://github.com/bokeh/bokeh-scala-sample
[api-stable]: https://s3.amazonaws.com/bokeh-scala/docs/2.11/0.7/index.html
[api-devel]: https://s3.amazonaws.com/bokeh-scala/docs/2.11/0.8-SNAPSHOT/index.html
[nodejs]: https://nodejs.org/download/
[group]: https://groups.google.com/a/continuum.io/forum/#!forum/bokeh
[issues]: https://github.com/bokeh/bokeh-scala/issues
[bokeh-issues]: https://github.com/bokeh/bokeh/issues
[travis]: https://api.travis-ci.org/bokeh/bokeh-scala.png?branch=master
[license]: http://www.opensource.org/licenses/mit-license.php
