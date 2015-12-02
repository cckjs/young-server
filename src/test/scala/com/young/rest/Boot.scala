package com.young.rest

import spray.can.Http
import akka.actor.ActorSystem
import akka.io.IO
import akka.actor.Props

/**
 * @author dell
 */

object Boot extends App {
  implicit val system = ActorSystem("spray-demo")
  val service = system.actorOf(Props[CustomerServiceActor], "customer-service")
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}
