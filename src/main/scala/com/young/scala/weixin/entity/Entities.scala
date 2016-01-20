package com.young.scala.weixin.entity

import scala.beans.BeanProperty

/**
 * Created by Administrator on 2016/1/19.
 */


sealed trait Message

case class CheckDevelopMessage(signature: String, timestamp: String, nonce: String, echostr: String) extends Message

case class AccessToken(access_token:String,expires_in:Int)

case class GetAccessTokenParam(grant_type:String,appid:String,secret:String)



