package com.young.spray.io

import akka.util.ByteString

/**
 * Created by dell on 2016/2/1.
 */
sealed trait Message

case object ConnectionCreated extends Message

case object ConnectionFailed extends Message

case class ConnectionError(message: String) extends Message

case class ConnectionData(data: ByteString) extends Message

case object ConnectionClose extends Message

case object ConnectionClosed extends Message
