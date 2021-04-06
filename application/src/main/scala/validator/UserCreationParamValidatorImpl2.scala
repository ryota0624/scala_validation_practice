package validator

import cats.implicits._
import data.user.User.UserCreationParam
import data.user.{Color, User}
import field.FieldMacro

case class UserCreationParamValidatorImpl2(param: UserCreationParam)
    extends UserCreationParamValidator
    with FieldMacro {
  import UserCreationParamValidatorImpl2._
  override def apply(): ValidationResult[User] =
    (
      fieldOf(param.age)(AgeValidator),
      fieldOf(param.favoriteColor)(ColorValidator),
      fieldOf(param.unFavoriteColor)(ColorValidator)
    ) mapN User.apply
}

object UserCreationParamValidatorImpl2 extends FieldMacro {
  object ColorValidator
      extends (UserCreationParam.Color => ValidationResult[Color]) {
    override def apply(
        color: UserCreationParam.Color
    ): ValidationResult[Color] =
      (
        fieldOf(color.red)(ColorElementValidator),
        fieldOf(color.blue)(ColorElementValidator),
        fieldOf(color.green)(ColorElementValidator)
      ).mapN(Color)
  }
}
