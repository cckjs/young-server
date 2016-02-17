package com.young.example.rest

import akka.actor.{Actor, ActorRefFactory, Props}
import org.json4s.DefaultFormats
import spray.httpx.Json4sSupport
import spray.routing._

/**
 * Created by dell on 2016/2/15.
 */
class UserServiceActor extends Actor with UserService with UserRequestFactory {

  implicit def actorRefFactory: ActorRefFactory = context

  /**
   * 为了满足runRoute函数中的一些implict变量
   */
  implicit val system = context.system

  /**
   * 默认的Route,处理url不存在的情况
   */
  val defaultRouter :Route = {
    ctx=> ctx.complete("url not found")
  }

  /**
   *这里可以连接多个Route对象,用~连接
   * @return
   */
  def receive = runRoute(userRouter~defaultRouter)


  //提供具体的请求处理业务
  /**
   * 这里是将请求转发到UserActor进行逻辑处理
   * @param message
   * @return
   */
  override def handleRequest(message: ProcessMessage): Route = {
    ctx => createUserRequest(ctx, Props[UserActor], message)
  }
}

/**
 * 具体的User Controller
 */
trait UserService extends HttpService with Json4sSupport {
  val json4sFormats = DefaultFormats
  val userRouter = path("user" / IntNumber) {
    id: Int => get {
      //拒绝空请求
      rejectEmptyResponse {
        handleRequest(GetUser(id))
      }
    } ~ post {
      entity(as[User]) {
        user: User => handleRequest(AddUser(user))
      }
    } ~ delete {
      handleRequest(DeleteUser(id))
    }
  }

  //提供具体的请求处理业务
  def handleRequest(message: ProcessMessage): Route
}
