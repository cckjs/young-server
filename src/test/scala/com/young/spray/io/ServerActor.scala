package com.young.spray.io

import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props, Actor}
import akka.io.{IO, Tcp}
import Tcp._

/**
 * Created by dell on 2016/1/29.
 */
class ServerActor extends Actor {
  import  context.system
  //绑定ip和端口信息
  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 9999))

  def receive = {
    //绑定成功
    case b@Bound(localAddress) => println("bind ip=" + b.localAddress.getHostName + ",port = " + b.localAddress.getPort)
    case CommandFailed(_: Bind) => println("bind fail")
      context stop self
    case c@Connected(remote, local) =>
      val handler = context.actorOf(Props[SimpleHandler])
      //这里connection和handler都不能是全局的,因为每个ClientActor都是一个实例,但是连接的服务端是同一个
      val connection = sender
      //这里意思是获取connection以后,注册一个处理数据的handler
      connection ! Register(handler)
  }
}

object ServerActor{
  def main(args: Array[String]) {
    val system = ActorSystem("server")
    val server = system.actorOf(Props[ServerActor])
  }

}
