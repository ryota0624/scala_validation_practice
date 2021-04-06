package validator

import cats.implicits._
import data.user.User.UserCreationParam
import data.user.{Color, User}

object UserCreationParamValidatorImpl2
    extends UserCreationParamValidator
    with Validator[UserCreationParam, User] {
  override def apply(param: UserCreationParam): ValidationResult[User] =
    (
      fieldOf(param.age)(AgeValidator),
      fieldOf(param.favoriteColor)(colorValidator),
      fieldOf(param.unFavoriteColor)(ColorValidator)
    ) mapN User.apply

  object ColorValidator extends Validator[UserCreationParam.Color, Color] {
    override def apply(
        color: UserCreationParam.Color
    ): ValidationResult[Color] =
      (
        fieldOf(color.red)(ColorElementValidator),
        fieldOf(color.blue)(ColorElementValidator),
        fieldOf(color.green)(ColorElementValidator)
      ).mapN(Color)
  }

  def colorValidator: Validator[UserCreationParam.Color, Color] = { color =>
    (
      fieldOf(color.red)(ColorElementValidator),
      fieldOf(color.blue)(ColorElementValidator),
      fieldOf(color.green)(ColorElementValidator)
    ).mapN(Color)
  }

}
