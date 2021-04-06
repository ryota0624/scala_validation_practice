package field

import validator.Validator

trait Field[T] {
  protected def name: String
  protected def value: T
  import Validator._
  def apply[O](validator: Validator[T, O]): ValidationResult[O] = {
    val ret = validator(value)
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
