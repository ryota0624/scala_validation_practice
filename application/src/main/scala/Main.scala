import data.user.User
import data.user.User.UserCreationParam
import shapeless.ops.record.Keys
import shapeless.record.recordOps
import validator.{AgeValidator, UserCreationParamValidatorImpl1}
object Main extends App {
  val okUserCreationParam = UserCreationParam(24, UserCreationParam.Color(200, 200, 200), UserCreationParam.Color(100, 200, 100))

  val faiUserCreationParam = UserCreationParam(24, UserCreationParam.Color(300, 200, 200), UserCreationParam.Color(100, 200, 100))


  val user: User = UserCreationParamValidatorImpl1(okUserCreationParam).apply().getOrElse(sys.error("fail"))
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
