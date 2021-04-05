package field

trait Field[T] {
  protected def name: String
  protected def value: T
  def apply[R](fn: (String, T) => R): R = fn(name, value)
  def apply[R](fn: T => String => R): R = fn(value)(name)

  def tuple: (String, T) = (name, value)
}

object Field {
  def apply[T](n: String, v: => T): Field[T] =
    new Field[T] {
      override protected def name: String = n
      override protected def value: T = v
    }
}
