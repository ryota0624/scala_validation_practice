package validator

import cats.implicits._
import data.user.User.UserCreationParam
import data.user.{Color, User}
import field.FieldMacro

case class UserCreationParamValidatorImpl2(param: UserCreationParam)
    extends UserCreationParamValidator
    with FieldMacro {
  override def apply(): ValidationResult[User] =
    (
      fieldOf(param.age)(AgeValidator(_).apply),
      fieldOf(param.favoriteColor) {
        UserCreationParamValidatorImpl2.ColorValidator(_).apply
      },
      fieldOf(param.unFavoriteColor) {
        UserCreationParamValidatorImpl2.ColorValidator(_).apply
      }
    ) mapN User.apply
}

object UserCreationParamValidatorImpl2 extends FieldMacro {
  object ColorValidator
      extends ((UserCreationParam.Color) => ValidationResult[Color]) {
    override def apply(
        color: UserCreationParam.Color
    ): ValidationResult[Color] =
      (
        fieldOf(color.red)(ColorElementValidator(_).apply),
        fieldOf(color.blue)(ColorElementValidator(_).apply),
        fieldOf(color.green)(ColorElementValidator(_).apply)
      ).mapN(Color)
  }
}
