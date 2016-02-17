package com.young.example.rest

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

/**
 * Created by Administrator on 2016/2/16.
 */
object UserExampleBoot {

  def main(args: Array[String]) {
    implicit val system = ActorSystem("user")
    val actor = system.actorOf(Props[UserServiceActor])
    /**
     * 启动监听端口,很简单只需要向IO(Http)这个actor发送一个Http.Bind消息即可
     * 参数中的actor就是controller,由这个actor来控制跳转
     */

    IO(Http)!Http.Bind(actor,interface = "localhost",port = 9999)
  }
}
