package validator

import cats.implicits._
import data.user.User.UserCreationParam
import data.user.{Age, Color, ColorElement, User}
import mymacro.within
import validation.ValidationFailed

import scala.util.Try

case class UserCreationParamValidator(param: UserCreationParam)
    extends (() => ValidationResult[User]) {
  @within private def favoriteColor = ColorValidator(param.favoriteColor)()
  @within private def unFavoriteColor = ColorValidator(param.unFavoriteColor)()
  @within private def age = AgeValidator(param.age)

  override def apply(): ValidationResult[User] =
    (
      age,
      favoriteColor,
      unFavoriteColor
    ) mapN User.apply
}

case class ColorValidator(color: UserCreationParam.Color)
    extends (() => ValidationResult[Color]) {
  @within private def red = ColorElementValidator(color.red)
  @within private def blue = ColorElementValidator(color.blue)
  @within private def green = ColorElementValidator(color.green)

  override def apply(): ValidationResult[Color] =
    (red, blue, green).mapN(Color)
}

object ColorElementValidator extends (Int => ValidationResult[ColorElement]) {
  override def apply(v1: Int): ValidationResult[ColorElement] =
    Try {
      ColorElement(v1)
    } asValidationResult (ValidationFailed(_, ColorElement.getClass))
}

object AgeValidator extends (Int => ValidationResult[Age]) {
  override def apply(v1: Int): ValidationResult[Age] =
    Try {
      Age(v1)
    } asValidationResult (ValidationFailed(_, Age.getClass))
}
