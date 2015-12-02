name := "young-server"
 
version := "1.0"
 
scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
 
resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")
 
libraryDependencies ++=
{
     val akka_version="2.3.12"
     val spray_version="1.3.1"
     val slick_version = "3.0.2"
     Seq("com.typesafe.akka" %% "akka-actor" % akka_version,
         "com.typesafe.akka" %% "akka-testkit" % akka_version,
         "com.typesafe.akka" %% "akka-remote" % akka_version,
         "com.typesafe.akka" %% "akka-cluster" % akka_version,
         "commons-net" % "commons-net" % "3.3",
         "commons-io" % "commons-io" % "2.4",
         "io.spray" %% "spray-caching" % spray_version,
         "io.spray" %% "spray-io" % spray_version,
         "io.spray" %% "spray-http" % spray_version,
         "io.spray" %% "spray-routing" % spray_version,
         "io.spray" %% "spray-can" % spray_version,
         "io.spray" %% "spray-json" % spray_version,
         "com.googlecode.concurrentlinkedhashmap" % "concurrentlinkedhashmap-lru" % "1.4.2",
         "com.typesafe.slick" %% "slick" % slick_version,
         "mysql" % "mysql-connector-java" % "5.1.17",
         "com.zaxxer" % "HikariCP" % "2.4.0",
         "com.h2database" % "h2" % "1.4.188",
         "org.slf4j" % "slf4j-nop" % "1.6.4",
         "org.json4s" %% "json4s-core" % "3.3.0",
         "org.json4s" %% "json4s-native" % "3.3.0",
         "io.spray" %% "spray-json" % "1.3.2"
         )
}