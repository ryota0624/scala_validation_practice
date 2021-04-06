package validator

import cats.implicits._
import data.user.User.UserCreationParam
import data.user.{Age, Color, ColorElement, User}
import mymacro.within
import validation.ValidationFailed

import scala.util.Try

trait UserCreationParamValidator extends Validator[UserCreationParam, User]

object UserCreationParamValidatorImpl1
    extends UserCreationParamValidator {

  override def apply(param: UserCreationParam): ValidationResult[User] = {
    @within def favoriteColor = ColorValidator(param.favoriteColor)()
    @within def unFavoriteColor = ColorValidator(param.unFavoriteColor)()
    @within def age = AgeValidator(param.age)
    (
      age,
      favoriteColor,
      unFavoriteColor
    ) mapN User.apply
  }
}

case class ColorValidator(color: UserCreationParam.Color)
    extends (() => ValidationResult[Color]) {
  @within private def red = ColorElementValidator(color.red)
  @within private def blue = ColorElementValidator(color.blue)
  @within private def green = ColorElementValidator(color.green)

  override def apply(): ValidationResult[Color] =
    (red, blue, green).mapN(Color)
}

object ColorElementValidator extends Validator[Int, ColorElement] {
  override def apply(v1: Int): ValidationResult[ColorElement] =
    Try {
      ColorElement(v1)
    } asValidationResult (ValidationFailed(_, ColorElement.getClass))
}

object AgeValidator extends Validator[Int, Age] {
  override def apply(v1: Int): ValidationResult[Age] =
    Try {
      Age(v1)
    } asValidationResult (ValidationFailed(_, Age.getClass))
}
