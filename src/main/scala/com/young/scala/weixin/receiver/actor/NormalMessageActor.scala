package com.young.scala.weixin.receiver.actor

import akka.actor.{Props, Actor}
import com.young.rest.{RestServer, RequestMessage, CustomerRequestCreator}
import com.young.scala.weixin.entity.ErrorMessage
import com.young.scala.weixin.receiver.router.NormalMessageRouter
import spray.routing._

/**
 * Created by dell on 2016/1/20.
 */
class NormalMessageActor extends Actor with NormalMessageRouter{

  implicit def actorRefFactory = context

  def receive = runRoute(normalMessageRouter)

  def handleRequest(message: ErrorMessage): Route =
    ctx => customerRequest(ctx, Props[RestServer], message)
}
