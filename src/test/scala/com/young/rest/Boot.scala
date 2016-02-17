package com.young.rest

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

/**
 * Created by dell on 2016/2/17.
 */
object Boot extends App {
  implicit val system = ActorSystem("spray-demo")
  val service = system.actorOf(Props[CustomerServiceActor], "customer-service")
  IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
}
