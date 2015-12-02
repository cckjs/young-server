package com.young.rest

import java.util.Date

/**
 * @author dell
 */

case class Customer(id: Long, birthDate: Date, name: String)

sealed trait RequestMessage

case class GetCustomer(id: Long) extends RequestMessage

case class DeleteCustomer(id: Long) extends RequestMessage

case class UpdateCustomer(customer: Customer) extends RequestMessage

case class CreateCustomer(birthDate: Date, name: String) extends RequestMessage

case object AllCustomers extends RequestMessage

sealed trait ResponseMessage

case class Created(location: String) extends ResponseMessage

case class Deleted(message: String) extends ResponseMessage

case class OneCustomer(customer: Customer) extends ResponseMessage

case class ListCustomers(items: List[Customer]) extends ResponseMessage

case class Error(message: String)
  
