package com.young.example.rest

import akka.actor.{ActorRef, Props, Actor}
import com.young.example.rest.UserRequest.UserWithProps
import org.json4s.DefaultFormats
import spray.http.StatusCode
import spray.http.StatusCodes._
import spray.httpx.Json4sSupport
import spray.routing.RequestContext

import scala.concurrent.duration.Duration

/**
 * Created by Administrator on 2016/2/16.
 */

trait UserRequest extends Actor with Json4sSupport {
  def requestContext: RequestContext

  def message: ProcessMessage

  def target: ActorRef

  import context._

  setReceiveTimeout(Duration(2, "s"))

  target ! message

  def receive = {
    case message:GetResult=>complete(OK,GetResult)
    case message:DeleteResult=>complete(OK,DeleteResult)
    case message:AddResult=>complete(OK,AddResult)
  }
  def complete[T <: AnyRef](status: StatusCode, obj: T) = {
    requestContext.complete(status, obj)
    stop(self)
  }
}

object UserRequest {

  case class UserWithProps(requestContext: RequestContext, props: Props, message: ProcessMessage) extends UserRequest {
    implicit val json4sFormats = DefaultFormats
    lazy val target = context.actorOf(props)
  }

}

trait UserRequestFactory {

  this: Actor =>
  def createUserRequest(requestContext: RequestContext, props: Props, message: ProcessMessage):ActorRef={
    context.actorOf(Props(new UserWithProps(requestContext,props,message)))
  }
}
