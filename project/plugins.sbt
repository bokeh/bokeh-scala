addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.2")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.10.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.1")

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-language:_")
