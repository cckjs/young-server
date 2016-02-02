package com.young.spray.router.httpserviceactor

import spray.routing.{Route, HttpServiceActor}

/**
 * Created by dell on 2016/2/2.
 */
case class User(name:String,age:Int)
class HttpServiceActorExample extends HttpServiceActor {

  val router:Route = {
    path("hello") {
      get {
        complete {
          User("yangyong",30).toString
        }
      }~post{
        complete{
          "post"
        }
      }
    }~path("world"){
      get{
        complete{
          "world"
        }
      }
    }
  }

  val router_reject :Route={
    path("reject") {
      get {
       reject
      }
    }
  }

  /**
   * 可以当做一个默认的router,当输入的连接不存在的时候就执行这个router
   */
  val router_simple :Route={
    complete("您输入的地址不存在,请检查")
  }


  def receive = runRoute(router~router_reject~router_simple)
}
