package com.young.scala.weixin.receiver.actor

import akka.actor.Actor
import com.young.scala.weixin.receiver.router.NormalMessageRouter

/**
 * Created by dell on 2016/1/20.
 */
abstract class NormalMessageActor extends Actor with NormalMessageRouter{

  implicit def actorRefFactory = context

  def receive = runRoute(normalMessageRouter)


}
