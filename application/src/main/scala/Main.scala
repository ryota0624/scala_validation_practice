
import field_of.Field.ValidationFailure
import field_of.Field

import scala.util.{Failure, Success, Try}

object Main {
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
