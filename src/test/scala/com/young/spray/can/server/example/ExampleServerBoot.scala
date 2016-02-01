package com.young.spray.can.server.example

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

/**
 * Created by dell on 2016/2/1.
 */
object ExampleServerBoot {

  def main(args: Array[String]) {
    implicit val system = ActorSystem("server")
    val serverActor = system.actorOf(Props[ExampleServer])
    IO(Http)!Http.Bind(serverActor,interface = "localhost",port=8888)
  }
}
