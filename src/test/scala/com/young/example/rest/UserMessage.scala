package com.young.example.rest

/**
 * Created by dell on 2016/2/15.
 */

case class User(id:Int,name:String,desc:String)

sealed trait ProcessMessage

case class GetUser(userId:Int) extends ProcessMessage

case class AddUser(user:User) extends ProcessMessage

case class DeleteUser(userId:Int) extends ProcessMessage
