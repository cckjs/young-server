package com.young.example.rest

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

/**
 * Created by Administrator on 2016/2/16.
 */
object UserExampleBoot {

  def main(args: Array[String]) {
    implicit val system = ActorSystem("user")
    val actor = system.actorOf(Props[UserServiceActor])
    IO(Http)!Http.Bind(actor,interface = "localhost",port = 9999)
  }
}
