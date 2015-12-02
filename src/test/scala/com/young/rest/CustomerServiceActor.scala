package com.young.rest

import org.json4s.DefaultFormats

import akka.actor.Actor
import akka.actor.Props
import spray.httpx.Json4sSupport
import spray.routing.Directive.pimpApply
import spray.routing.HttpService
import spray.routing.Route

/**
 * @author dell
 */
class CustomerServiceActor extends Actor with CustomerService with CustomerRequestCreator {
  
  implicit def actorRefFactory = context
  
  def receive = runRoute(customerRouter)

  def handleRequest(message: RequestMessage): Route =
    ctx => customerRequest(ctx, Props[RestServer], message)
}

trait CustomerService extends HttpService with Json4sSupport {
  val json4sFormats = DefaultFormats
  val customerRouter = path("customers" / LongNumber) {
    id: Long =>
      get {
        rejectEmptyResponse {
          handleRequest { GetCustomer(id) }
        }
      } ~ put {
        entity(as[Customer]) {
          customer: Customer =>
            {
              handleRequest(UpdateCustomer(new Customer(id, customer.birthDate, customer.name)))
            }
        }
      } ~ delete {
        handleRequest(DeleteCustomer(id))
      } ~ post {
        entity(as[Customer]) {
          customer: Customer =>
            {
              handleRequest(CreateCustomer(customer.birthDate, customer.name))
            }
        }
      }
  }
  def handleRequest(message: RequestMessage): Route
}