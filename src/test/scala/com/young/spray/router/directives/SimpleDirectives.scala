package com.young.spray.router.directives

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import spray.routing.{HttpServiceActor, Route}

/**
 * Created by dell on 2016/2/2.
 */
class SimpleDirectives extends HttpServiceActor {

  def receive = runRoute(orderRoute)

  val router2: Route = {
    ctx => ctx.complete("Hello")
  }

  val router: Route = {
    path("order" / IntNumber) {
      id: Int => get {
        complete {
          "recieve get request id =" + id
        }
      }
    } ~ path("find" / IntNumber) {
      id => get {
        complete {
          "receive find id =" + id
        }
      }
    }
  }

  def innerRoute(id: Int): Route = get {
    complete {
      "recieve get request id =" + id
    }
  }

  //这么写其实等价于router,在spray的Directives眼里一切都是Route,该例子表达的是一个外部的Route和内部的Route,当然get是Route
  //complete也是Route一切皆Route
  val routerEquals: Route = {
    path("order" / IntNumber) { id => innerRoute(id) }
  }

  val route1OrRoute2: Route = {
    path("route1OrRoute2" / IntNumber) {
      id => {
        (get|put){
        complete{"sdfsdfsfdsf"+id}
        }
      }
    }
  }
  val order = path("order" / IntNumber) & parameters('a, 'b ?)
  val orderRoute =
    order { (orderId, a, b) =>
      {
        get{
          complete{
            ""+a+","+b
          }
        }
      }
    }
}


object SimpleDirectives {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("test")
    val actor = system.actorOf(Props[SimpleDirectives])
    IO(Http) ! Http.Bind(actor, interface = "localhost", port = 9999)
  }
}
