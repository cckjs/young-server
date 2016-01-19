package com.young.scala.weixin.entity

/**
 * Created by Administrator on 2016/1/19.
 */
sealed trait Message
case class CheckDevelopMessage(signature:String,timestamp:String,nonce:String,echostr:String) extends Message


