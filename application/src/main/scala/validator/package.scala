import cats.data.ValidatedNel
import mymacro.WithinContext

import scala.util.{Success, Try, Failure}

package object validator {
  implicit class ValidationResultOps[T](val v: ValidationResult[T])
    extends WithinContext {
    override type Self = ValidationResult[T]

    override def within(field: String): ValidationResult[T] =
      v.leftMap(_.map(_.within(field)))
  }

  type ValidationResult[A] = ValidatedNel[validation.ValidationFailed, A]

  implicit class TryOps[T](v: Try[T]) {
    import cats.implicits._

    def asValidationResult[R](catchException: Throwable => R): ValidatedNel[R, T] = {
      v match {
        case Failure(exception) =>
          catchException(exception).invalidNel
        case Success(value) =>
          value.validNel
      }
    }
  }
}
