package com.young.spray.io

import akka.actor.Actor
import akka.io.Tcp

/**
 * Created by dell on 2016/1/29.
 */
class SimpleHandler extends Actor{

  import Tcp._

  def receive={
    case Received(data)=>
      println("handler receive data = ["+data.utf8String+"]")
      sender!Write(data)
    case PeerClosed =>
      println("异常断开")
      context.stop(self)
  }
}
