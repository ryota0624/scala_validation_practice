package validator

import cats.implicits._
import data.user.User.UserCreationParam
import data.user.{Age, Color, ColorElement, User}
import mymacro.within
import validation.ValidationFailed

import scala.util.Try

trait UserCreationParamValidator extends (() => ValidationResult[User])

case class UserCreationParamValidatorImpl1(param: UserCreationParam)
    extends UserCreationParamValidator {
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

trait Validator[I, O] extends (I => ValidationResult[O])

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
