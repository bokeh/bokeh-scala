# bokeh-scala

Scala bindings for [Bokeh][bokeh] plotting library.

[![Build Status][travis]](https://travis-ci.org/mattpap/bokeh-scala)
[![Coverage Status][coveralls]](https://coveralls.io/r/mattpap/bokeh-scala)

## Requirements

* [Java](http://wwww.java.com) JRE 1.6+

## Usage

```bash
$ git clone --recursive https://github.com/mattpap/bokeh-scala
$ cd bokeh-scala
$ ./sbt
> examples/runMain io.continuum.bokeh.examples.glyphs.Anscombe
```
or
```bash
> examples/runAll
```

## Development

bokeh-scala uses submodules, so either clone with `--recursive` or use
`git submodule update`.

## Support

Submit any questions and suggestions to Bokeh's [mailing list][group].

## License

Copyright &copy; 2014 by Mateusz Paprocki and contributors.

Published under [The MIT License][license], see LICENSE.

[bokeh]: http://bokeh.pydata.org
[group]: https://groups.google.com/a/continuum.io/forum/#!forum/bokeh
[travis]: https://api.travis-ci.org/mattpap/bokeh-scala.png?branch=master
[coveralls]: https://coveralls.io/repos/mattpap/bokeh-scala/badge.png
[license]: http://www.opensource.org/licenses/mit-license.php
