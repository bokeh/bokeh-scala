addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

addSbtPlugin("com.untyped" % "sbt-js" % "0.7")

libraryDependencies ++= Seq(
    "org.jgrapht" % "jgrapht-ext" % "0.9.0",
    "com.google.javascript" % "closure-compiler" % "v20131014")

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature", "-language:postfixOps")
