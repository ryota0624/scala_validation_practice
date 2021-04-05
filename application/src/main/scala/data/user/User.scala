package data.user

import shapeless.LabelledGeneric

case class User(
    age: Age,
    favoriteColor: Color,
    unFavoriteColor: Color
)

object User {
  case class UserCreationParam(
      age: Int,
      favoriteColor: UserCreationParam.Color,
      unFavoriteColor: UserCreationParam.Color
  )
  object UserCreationParam {
    case class Color(red: Int, green: Int, blue: Int)
  }


  val userG = LabelledGeneric[User]

}

case class Age(number: Int) {
  require(number >= 0, "number should be positive")
}

case class Color(
    red: ColorElement,
    green: ColorElement,
    blue: ColorElement
)

case class ColorElement(number: Int) {
  require(number >= 0, "number should be greeter than 0")
  require(number <= 255, "number should be smaller than 255")
}
