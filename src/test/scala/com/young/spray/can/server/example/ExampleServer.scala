package com.young.spray.can.server.example

import akka.actor.Actor
import akka.util.Timeout
import spray.can.Http
import spray.http.MediaTypes._
import spray.http._

import scala.concurrent.duration._
import HttpMethods._

/**
 * Created by dell on 2016/2/1.
 */
class ExampleServer extends Actor {
  implicit val timeout: Timeout = 1.second

  lazy val xml = "<root>\n  <user>\n    <id>1</id>\n  </user>\n</root>"

  def receive = {
    case _: Http.Connected => sender ! Http.Register(self)
    //HttpRequest构造方法有 GET,urlPath,Header,Entity,Protecol
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      sender!indexHtml
    case HttpRequest(GET,Uri.Path("/user/1"),_,_,_)=>
      sender!HttpResponse(entity = HttpEntity(xml))
  }

  lazy val indexHtml = HttpResponse(entity = HttpEntity(`text/html`,
    <html>
      <body>
        <h1>Say hello to
          <i>spray-can</i>
          !</h1>
        <p>Defined resources:</p>
        <ul>
          <li>
            <a href="/ping">/ping</a>
          </li>
          <li>
            <a href="/stream">/stream</a>
          </li>
          <li>
            <a href="/server-stats">/server-stats</a>
          </li>
          <li>
            <a href="/crash">/crash</a>
          </li>
          <li>
            <a href="/timeout">/timeout</a>
          </li>
          <li>
            <a href="/timeout/timeout">/timeout/timeout</a>
          </li>
          <li>
            <a href="/stop">/stop</a>
          </li>
        </ul>
        <p>Test file upload</p>
        <form action="/file-upload" enctype="multipart/form-data" method="post">
          <input type="file" name="datafile" multiple=" "></input>
          <br/>
          <input type="submit">Submit</input>
        </form>
      </body>
    </html>.toString()))
}
