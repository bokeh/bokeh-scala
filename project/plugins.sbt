addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-feature", "-language:postfixOps")
