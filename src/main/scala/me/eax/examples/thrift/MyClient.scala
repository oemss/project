package me.eax.examples.thrift

import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import me.eax.examples.thrift.game.{MyServ, Rt}

/**
  * Created by evgeniy on 13.08.16.
  */
object MyClient {
  def startclient(): MyServ[Future] = {
    val client = Thrift.client.newIface[MyServ[Future]]("127.0.0.1:6666")
    var rty = Rt(
      id = "1", name = "Hello"
    )
    Await.ready(client.put(1,rty))
    rty = Rt(
      id = "2", name = "World"
    )
    Await.ready(client.put(1,rty))
    rty = Rt(
      id = "1", name = "Evgeny"
    )
    Await.ready(client.put(0,rty))
    rty = Rt(
      id = "2", name = "Magic"
    )
    Await.ready(client.put(0,rty))

    Await.ready(client.add("1","1"))
    //Await.ready(client.add("2","2"))
    Await.ready(client.add("1","2"))

    //client.listT("1")
    //client.delete("1","2")
    //client.listT("1")
    val sas = Await.result(client.listR(Seq("1","2")))
    println(sas)
    val clie = client.listT("3").get()
    client
  }
}
