package me.eax.examples.thrift

import com.twitter.finagle.Thrift
import com.twitter.finagle.Thrift.Client
import com.twitter.util.{Await, Future}
import me.eax.examples.thrift.game._

object Main extends App {

  val service = Thrift.server.serveIface("127.0.0.1:6666", new Server)

  val client = MyClient.startclient()
  Await.ready(service)
}