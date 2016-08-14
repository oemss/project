package me.eax.examples.thrift

import com.twitter.util.Future
import me.eax.examples.thrift.game.MyServ
import me.eax.examples.thrift.game._

import collection.mutable.HashMap
import scala.collection.mutable
import scala.runtime.Nothing$


/**
  * Created by evgeniy on 13.08.16.
  */
class Server extends MyServ[Future]{
  var db = new mutable.HashMap[Rt,Seq[Rt]]()

  var tag: Seq[Rt] = Seq()
  var rec: Seq[Rt] = Seq()

  override def add(idR: String, idT: String): Future[Unit] = {
    val fst = functions.find(idR,rec)
    val snd = functions.find(idT,tag)
    (fst,snd) match {
      case (Some(f),Some(s)) => {
        val dbValue = db.get(f)
        dbValue match {
          case Some(v) => db.put(f,v ++ Seq(s))
          case None => db.put(f,Seq(s))
        }
      }
      case (_,_) => error("Not found")
    }
    Future.Unit
  }


  override def delete(idR: String, idT: String): Future[Unit] = {
    val found = functions.find(idR,rec)
    found match {
      case Some(value) => {
        val localFound = db.get(value)
        localFound match {
          case Some(v) => {
            db.remove(value)
            db.put(value,v.filter(x => x._1 != idT))
          }
          case None => error("Not found")
        }
      }
    }

    Future.Unit
  }

  /**
    * Возвращает список имен записей по списку тэгов
    * @param lstT
    * @return
    */
  override def listR(lstT: Seq[String]): Future[Seq[Rt]] = {
    Future {
      var msg: Seq[Rt] = Seq()
      db.filter(w => w._2.map(x => x._1) == lstT).foreach(x => msg ++= Seq(x._1))
      println("msg = " + msg)
      msg
    }
  }

  /**
    * Возвращает список тэгов по id записи
    * @param idR
    * @return
    */
  override def listT(idR: String): Future[Seq[Rt]] = {
    Future {
      val found = functions.find(idR, rec)
      found match {
        case Some(value) => {
          val hlpval = db.get(value)
          hlpval match {
            case Some(v) => v
            case None => Seq()
          }
        }
        case None => Seq()
      }
    }
  }

  override def put(where: Short,wt: Rt): Future[Unit] = {
    where match {
      case 0 => rec ++= Seq(wt)
      case _ => tag ++= Seq(wt)
    }
    Future.Unit
  }

}
