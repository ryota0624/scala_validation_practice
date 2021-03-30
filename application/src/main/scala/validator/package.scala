import mymacro.WithinContext

package object validator {
  implicit class ValidationResultOps[T](val v: Either[validation.ValidationFailed, T])
    extends WithinContext {
    override type Self = Either[validation.ValidationFailed, T]

    override def within(field: String): Either[validation.ValidationFailed, T] =
      v.left.map(_.within(field))
  }

}
