package com.young.example.rest

import akka.actor.Actor

/**
 * Created by Administrator on 2016/2/16.
 */
class UserActor extends Actor{

  def receive={
    case message:GetUser=>sender!GetResult(User(message.userId,"yangyong","yangyong"))
    case message:DeleteUser=>sender!DeleteResult(User(message.userId,"yangyong","yangyong"))
    case message:AddUser=>sender!AddResult("add success")
  }
}
