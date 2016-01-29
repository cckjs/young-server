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
class ClientActor(remote: InetSocketAddress) extends Actor {

  var buffer: ListBuffer[ByteString] = ListBuffer[ByteString]()

  import context.system

  IO(Tcp) ! Connect(remote)

  var messageSender: ActorRef = null

  var connection: ActorRef = null

  def receive = {
    case CommandFailed(_: Connect) =>
      println("connect failed")
      context.stop(self)
    case data: ByteString => buffer += data
      println(buffer.length)
    case c@Connected(remote, local) =>
      connection = sender()
      connection ! Register(self)
      context.become {
        case data: ByteString =>
          if(buffer!=null&&buffer.length!=0){
            for(temp<-buffer){
              connection ! Write(temp)
              println("client write data [" + temp.utf8String + "] to server's handler")
            }
            buffer = null
          }
          connection ! Write(data)
          println("client write data [" + data.utf8String + "] to server's handler")
        case CommandFailed(w: Write) => println("OS buffer is full")
        case Received(data) => println("client receive data =" + data.utf8String)
        case "close" => connection ! Close
        case _: ConnectionClosed => println("client is closed")
          context.stop(self)
      }
  }

}

object ClientActor {
  def main(args: Array[String]) {
    val system = ActorSystem("client")
    val remote = new InetSocketAddress("localhost", 9999)
    val client = system.actorOf(Props(new ClientActor(remote)))
    for(i <- 1 to 100){
      Thread.sleep(10)
      client ! ByteString("hello_"+i)
    }

  }
}