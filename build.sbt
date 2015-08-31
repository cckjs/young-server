name := "young-server"
 
version := "1.0"
 
scalaVersion := "2.11.6"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
 
resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")
 
libraryDependencies ++=
{
     val akka_version="2.3.12"
     val spray_version="1.3.1"
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
         "com.googlecode.concurrentlinkedhashmap" % "concurrentlinkedhashmap-lru" % "1.4.2"
         )
}