package com.young.spray.router.httpserviceactor

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

/**
 * Created by dell on 2016/2/2.
 */
object HttpServiceExampleBoot {

  def main(args: Array[String]) {
    implicit val system = ActorSystem("example")
    val actor = system.actorOf(Props[HttpServiceActorExample])
    IO(Http) ! Http.Bind(actor, interface = "localhost", port = 9999)
  }
}
