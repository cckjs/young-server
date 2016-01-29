package com.young.spray.io

import akka.actor.Actor
import akka.io.Tcp.Connected
import akka.util.ByteString

/**
 * Created by dell on 2016/1/29.
 */
class ClientListener extends Actor {

  def receive = {
    case "failed" => println("listener connect fail ")
      context.stop(self)
    case data: ByteString =>
      println("listener receive data =[" + data.toString() + "]")
    case c@Connected(remote, local) =>
      println("listener connected remote=" + remote + ",local=" + local)
  }
}
