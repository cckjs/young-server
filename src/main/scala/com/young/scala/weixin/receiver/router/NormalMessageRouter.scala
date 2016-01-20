package com.young.scala.weixin.receiver.router

import com.young.scala.weixin.entity.ErrorMessage
import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport
import spray.routing._

/**
 * Created by dell on 2016/1/20.
 */
trait NormalMessageRouter extends HttpService with Json4sSupport {

  val json4sFormats = DefaultFormats

  val normalMessageRouter = path("message/normal") {
    post {
      entity(as[ErrorMessage]) {
        message: ErrorMessage => {
          handleRequest(message)
        }
      }
    }
  }

  def handleRequest(message: ErrorMessage): Route
}
