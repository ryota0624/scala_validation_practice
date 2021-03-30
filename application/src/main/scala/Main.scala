import data.Data
import data.user.User.UserCreationParam

object Main extends App {
  val okUserCreationParam = UserCreationParam(24, UserCreationParam.Color(200, 200, 200), UserCreationParam.Color(100, 200, 100))

  val faiUserCreationParam = UserCreationParam(24, UserCreationParam.Color(300, 200, 200), UserCreationParam.Color(100, 200, 100))

}
