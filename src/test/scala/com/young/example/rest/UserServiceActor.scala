package com.young.example.rest

import akka.actor.{Props, ActorRefFactory, Actor}
import com.young.rest.RequestMessage
import org.json4s.{Formats, DefaultFormats}
import spray.httpx.Json4sSupport
import spray.routing._
import spray.util.LoggingContext

/**
 * Created by dell on 2016/2/15.
 */
class UserServiceActor extends Actor with UserService with UserRequestFactory {

  implicit def actorRefFactory: ActorRefFactory = context

  implicit val system = context.system

  def receive = runRoute(userRouter)


  //提供具体的请求处理业务
  override def handleRequest(message: ProcessMessage): Route = {
    ctx => createUserRequest(ctx, Props[UserActor], message)
  }


}

trait UserService extends HttpService with Json4sSupport {
  val json4sFormats = DefaultFormats
  val userRouter = path("user" / IntNumber) {
    id: Int => get {
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
