package com.young.spray.io

import akka.actor.Actor
import akka.util.ByteString

/**
 * Created by dell on 2016/2/1.
 */
class ClientActorListener extends Actor {

  def receive = {
    case ConnectionClosed => println("connection closed")
    case ConnectionCreated =>
      for (i <- 1 to 1000) {
        Thread.sleep(5)
        sender ! ByteString("hello_" + i)
      }
    case data: ConnectionData => println("receive server data -" + data.data.utf8String)
    case ConnectionFailed => println("connect server failed")
    case error: ConnectionError => println("receive server data error --" + error.message)
  }
}
