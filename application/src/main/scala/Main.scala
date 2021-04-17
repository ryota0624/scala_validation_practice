
import field_of.Field

import scala.util.{Failure, Success, Try}

object Main {
  case class ValidationFailure(message: String, fields: Seq[String] = Nil) {
    def occurredOn(field: String): ValidationFailure = copy(fields = fields :+ field)
  }

  type ValidationResult[T] = Either[ValidationFailure, T]
  type Validator[I, O] = I => ValidationResult[O]

  implicit class ValidationResultOps[T](e: ValidationResult[T]) {
    def occurredOn(field: String): ValidationResult[T] = {
      e.left.map(_.occurredOn(field))
    }
  }

  implicit class ValidatableField[E](val field: Field[E]) extends AnyVal {
    def validate[O](
                     validator: Validator[E, O]
                   ): ValidationResult[O] =
      validator(field.value).occurredOn(field.name)
  }

  case class Input(age: Int)
  case class DoubleInput(age1: Int, age2: Int)

  class Age private (private val number: Int) {
    require(number >= 0, "年齢は0以上じゃないとあかんな")
  }

  object Age {
    def validate(n: Int): Either[ValidationFailure, Age] = {
      Try(new Age(n)) match {
        case Failure(exception) =>
          Left(ValidationFailure(exception.getMessage))
        case Success(value) =>
          Right(value)
      }
    }
  }

  def main(args: Array[String]) {
    val input = DoubleInput(age1 = -1, age2 = 10)
    println(Field.of(input.age1).validate(Age.validate))

  }
}
