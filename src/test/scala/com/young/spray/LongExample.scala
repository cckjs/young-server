package com.young.spray

import akka.actor.{Props, ActorSystem, Actor}
import akka.io.IO
import com.young.rest.CustomerServiceActor
import com.young.spray.SimpleExample._
import spray.can.Http
import spray.routing.HttpService

/**
 * Created by Administrator on 2016/1/21.
 */
class LongExample extends Actor with HttpService {

  implicit def actorRefFactory = context

  override def  timeoutRoute = router

  implicit val handleExceptions=router
  
  def receive = runRoute(router)

  val router: spray.routing.Route = path("hello") {
    get {
      complete {
        <h1>Say hello to spray</h1>
      }
    }
  }
}

object LongExample{
  def main(args: Array[String]) {
    implicit val system = ActorSystem("spray-demo")
    val service = system.actorOf(Props[LongExample], "long-example")
    IO(Http) ! Http.Bind(service, interface = "localhost", port = 8080)
  }
}

