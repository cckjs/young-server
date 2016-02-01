package com.young.spray.router

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

/**
 * Created by dell on 2016/2/1.
 */
object SimpleRouting extends SimpleRoutingApp {

  implicit val system = ActorSystem("example")

  startServer(interface = "localhost", port = 8888){
    path("hello"){
      get{
        complete{
          <h1>Say hello to spray</h1>
        }
      }
    }
  }

  def main(args: Array[String]) {

  }

}
