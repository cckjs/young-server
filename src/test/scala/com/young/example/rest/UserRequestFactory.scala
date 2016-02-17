package com.young.example.rest

import akka.actor.{Actor, ActorRef, Props, ReceiveTimeout}
import com.young.example.rest.UserRequest.UserRequestActor
import com.young.rest.Error
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

  /**
   * 首先UserRequest actor将消息message发送给UserActor进行逻辑处理
   */
  target ! message

  /**
   * 接收UserActor返回来的消息
   * @return
   */
  def receive = {
    case message:GetResult=>complete(OK,message)
    case message:DeleteResult=>complete(OK,message)
    case message:AddResult=>complete(OK,message)
    case Error(message) => complete(BadRequest, message)
    case ReceiveTimeout => complete(GatewayTimeout, "Request Timeout")
  }

  /**
   * 将结果返回给客户端
   * @param status
   * @param obj
   * @tparam T
   */
  def complete[T <: AnyRef](status: StatusCode, obj: T) = {
    requestContext.complete(status, obj)
    println("complete result = "+obj)
    stop(self)
  }
}

object UserRequest {

  /**
   * 其实是UserRequest的实现类,也就是一个UserRequest并且是一个actor
   * @param requestContext
   * @param props
   * @param message
   */
  case class UserRequestActor(requestContext: RequestContext, props: Props, message: ProcessMessage) extends UserRequest {
    implicit val json4sFormats = DefaultFormats
    /**
     * 这个target是UserRequest里的target,在这里是创建了一个UserActor
     * 这一步执行完成后UserActor就被创建出来了
     * 现在一共是创建了两个actor,一个是UserRequest actor另一个是UserActor
     */
    lazy val target = context.actorOf(props)
  }

}

/**
 * 创建请求的处理actor,一个请求一个actor
 */
trait UserRequestFactory {
  this: Actor =>
  def createUserRequest(requestContext: RequestContext, props: Props, message: ProcessMessage):ActorRef={
    /**
     * 这里的Props其实是创建了一个UserRequest Actor 因为UserWithProps就是一个actor
     */
    context.actorOf(Props(new UserRequestActor(requestContext,props,message)))
  }
}
