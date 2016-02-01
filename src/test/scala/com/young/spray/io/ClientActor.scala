package com.young.spray.io

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.util.ByteString

import scala.collection.mutable.ListBuffer

/**
 * Created by dell on 2016/1/29.
 */
class ClientActor(remote: InetSocketAddress, listener: ActorRef) extends Actor {

  var buffer: ListBuffer[ByteString] = ListBuffer[ByteString]()

  import context.system

  IO(Tcp) ! Connect(remote)

  var messageSender: ActorRef = null

  var connection: ActorRef = null

  def receive = {
    case CommandFailed(_: Connect) =>
      context.stop(self)
      listener ! ConnectionFailed
    case c@Connected(remote, local) =>
      connection = sender()
      connection ! Register(self)
      context.become {
        case data: ByteString =>
          connection ! Write(data)
        case CommandFailed(w: Write) =>
          listener ! ConnectionError("OS buffer is full")
        case Received(data) =>
          listener ! ConnectionData(data)
        case ConnectionClose => connection ! Close
        case _: ConnectionClosed =>
          context.stop(self)
          listener ! ConnectionClosed
      }
      listener ! ConnectionCreated
  }

}

object ClientActor {
  def main(args: Array[String]) {
    val system = ActorSystem("client")
    val remote = new InetSocketAddress("localhost", 9999)
    val listener = system.actorOf(Props[ClientActorListener])
    val client = system.actorOf(Props(new ClientActor(remote, listener)))
  }
}