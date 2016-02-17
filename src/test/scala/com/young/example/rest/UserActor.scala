package com.young.example.rest

import akka.actor.Actor

/**
 * Created by Administrator on 2016/2/16.
 */
class UserActor extends Actor{

  /**
   * 接收从UserRequest actor发送过来的消息,处理完成后通过sender将结果返回给UserRequest actor
   * @return
   */
  def receive={
    case message:GetUser=>sender!GetResult(User(message.userId,"yangyong","yangyong"))
    case message:DeleteUser=>sender!DeleteResult(User(message.userId,"yangyong","yangyong"))
    case message:AddUser=>sender!AddResult("add success")
  }
}
