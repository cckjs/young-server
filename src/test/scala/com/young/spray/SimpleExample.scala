package com.young.spray

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

/**
 * Created by Administrator on 2016/1/20.
 */
object SimpleExample extends App with SimpleRoutingApp{

  implicit val system = ActorSystem("my-system")
  startServer(interface = "localhost", port = 8080) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
    path("world"){
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    }
    path("receive"){
      post{
        complete{
          <h1>Say hello to spray</h1>
        }
      }
    }
  }
}
