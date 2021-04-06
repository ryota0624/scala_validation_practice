package field

trait Field[T] {
  protected def name: String
  protected def value: T
  def apply[R <: String => R](fn1: T => R): R = {
    val ret = fn1(value)
    ret.apply(name)
  }

  def tuple: (String, T) = (name, value)
}

object Field {
  def apply[T](n: String, v: => T): Field[T] =
    new Field[T] {
      override protected def name: String = n
      override protected def value: T = v
    }
}
