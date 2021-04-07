import data.user.User
import data.user.User.UserCreationParam
import shapeless.ops.record.Keys
import shapeless.record.recordOps
import validator.{AgeValidator, UserCreationParamValidatorImpl1}

import scala.util.{Failure, Success, Try}
object Main extends App {
  val okUserCreationParam = UserCreationParam(24, UserCreationParam.Color(200, 200, 200), UserCreationParam.Color(100, 200, 100))

  val faiUserCreationParam = UserCreationParam(24, UserCreationParam.Color(300, 200, 200), UserCreationParam.Color(100, 200, 100))


  val user: User = UserCreationParamValidatorImpl1(okUserCreationParam).getOrElse(sys.error("fail"))
  val userList = User.userG.to(user)


  def validateAge() = {
//    val symbol = Symbol("age")
    val age = userList.get(Symbol("age"))
    (age)
  }

  val fields = Keys[User.userG.Repr].apply.toList[Symbol]
  println(fields)
  println(validateAge())
}

object Sample {
  case class Input(age: Int)
  case class DoubleInput(age1: Int, age2: Int)

  class Age private (private val number: Int) {
    require(number >= 0, "年齢は0以上じゃないとあかんな")
  }
  case class ValidationFailure(message: String) {
    def addMessage(msg: String): String = msg ++ ":" ++ message
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

    val result = for {
      age1 <- Age.validate(input.age1).left.map(_.addMessage("age1"))
      age2 <- Age.validate(input.age2).left.map(_.addMessage("age2"))
    } yield (age1, age2)
    println(result)
  }
}
