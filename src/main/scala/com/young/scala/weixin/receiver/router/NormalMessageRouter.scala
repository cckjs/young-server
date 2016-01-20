package com.young.scala.weixin.receiver.router

import com.young.java.util.xml.XMLUtils
import com.young.scala.weixin.entity.{ReceiveNormalMessage, ErrorMessage}
import com.young.scala.weixin.util.Xml
import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport
import spray.routing._

/**
 * Created by dell on 2016/1/20.
 */
trait NormalMessageRouter extends HttpService {

  val normalMessageRouter = path("message/normal") {
    post {
      entity(as[String]) {
        message: String => handleRequest(new ReceiveNormalMessage())
      }
    }
  }

  def handleRequest(message: ReceiveNormalMessage): Route
}
