package validator

import data.user.User.UserCreationParam
import data.user.{Age, Color, ColorElement, User}
import mymacro.{WithinContext, within}
import validation.ValidationFailed

import scala.util.Try

case class UserCreationParamValidator(param: UserCreationParam)
    extends (() => Either[ValidationFailed, User]) {
  @within private def favoriteColor = ColorValidator(param.favoriteColor)()
  @within private def unFavoriteColor = ColorValidator(param.unFavoriteColor)()
  @within private def age = AgeValidator(param.age)

  override def apply(): Either[ValidationFailed, User] =
    for {
      favoriteColor <- favoriteColor
      unFavoriteColor <- unFavoriteColor
      age <- age
    } yield User(
      favoriteColor = favoriteColor,
      unFavoriteColor = unFavoriteColor,
      age = age
    )
}

case class ColorValidator(color: UserCreationParam.Color)
    extends (() => Either[ValidationFailed, Color]) {
  @within private def red = ColorElementValidator(color.red)
  @within private def blue = ColorElementValidator(color.blue)
  @within private def green = ColorElementValidator(color.green)

  override def apply(): Either[ValidationFailed, Color] =
    for {
      red <- red
      blue <- blue
      green <- green
    } yield Color(red, blue, green)
}

object ColorElementValidator
    extends (Int => Either[ValidationFailed, ColorElement]) {
  override def apply(v1: Int): Either[ValidationFailed, ColorElement] =
    Try {
      ColorElement(v1)
    }.toEither.left.map(ValidationFailed(_, ColorElement.getClass))
}

object AgeValidator extends (Int => Either[ValidationFailed, Age]) {
  override def apply(v1: Int): Either[ValidationFailed, Age] =
    Try {
      Age(v1)
    }.toEither.left.map(ValidationFailed(_, Age.getClass))
}

