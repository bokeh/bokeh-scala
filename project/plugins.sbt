addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "0.99.5.1")

addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "0.98.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

addSbtPlugin("com.untyped" % "sbt-js" % "0.7")

libraryDependencies ++= Seq(
    "org.jgrapht" % "jgrapht-ext" % "0.9.0",
    "com.google.javascript" % "closure-compiler" % "v20131014")

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature", "-language:postfixOps")
