package data

import mymacro.{ToZero, WithinContext, within}

//@identity
case class Data(value: Int, n: Long, stack: Seq[String] = Nil) {
//  @ToZero
  def double() = {
    println(2)
    value * 2
  }

  @within
  def fail(message: String): Data = copy(stack = stack :+ message)


  private def add(str: String): Data = copy(stack = stack :+ str)
}

object Data {
  implicit class WithinOps(data: Data) extends WithinContext {
    override type Self = Data

    override def within(str: String): Data = data.add(str)
  }
}
