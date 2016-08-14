package me.eax.examples.thrift

import me.eax.examples.thrift.game.Rt

import scala.collection.mutable.ArrayBuffer

/**
  * Created by evgeniy on 13.08.16.
  */
object functions {
  def find(id: String,list: Seq[game.Rt]): Option[Rt] = {
    val w = list.find(wa => wa._1 == id)
    w
  }
  def equat(fst: Seq[Rt], snd: Seq[Rt]): Boolean = {
    snd.eq(fst)
  }
}
