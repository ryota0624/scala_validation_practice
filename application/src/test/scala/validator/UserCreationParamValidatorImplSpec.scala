package validator

import data.user.User.UserCreationParam
import org.scalatest.EitherValues._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpecLike

trait UserCreationParamValidatorImplSpec
    extends AnyWordSpecLike
    with TableDrivenPropertyChecks {
  def createValidator: (UserCreationParam) => UserCreationParamValidator
  val params = Table[String, UserCreationParam, Seq[String]](
    ("statement", "param", "expectedResult"),
    (
      "ok",
      UserCreationParam(
        24,
        UserCreationParam.Color(200, 200, 200),
        UserCreationParam.Color(100, 200, 100)
      ),
      Nil
    ),
    (
      "failure in favorite color green",
      UserCreationParam(
        24,
        UserCreationParam.Color(200, 200, 300),
        UserCreationParam.Color(100, 200, 100)
      ),
      Seq(
        "requirement failed: number should be smaller than 255. at favoriteColor.blue. not satisfied for ColorElement$"
      )
    ),
    (
      "failure in favorite color green with blue, and un favorite red, and age",
      UserCreationParam(
        -1,
        UserCreationParam.Color(200, -1, 300),
        UserCreationParam.Color(1000, 200, 100)
      ),
      Seq(
        "requirement failed: number should be positive. at age. not satisfied for Age$",
        "requirement failed: number should be smaller than 255. at favoriteColor.blue. not satisfied for ColorElement$",
        "requirement failed: number should be greeter than 0. at favoriteColor.green. not satisfied for ColorElement$",
        "requirement failed: number should be smaller than 255. at unFavoriteColor.red. not satisfied for ColorElement$"
      ),
    )
  )

  forAll(params) {
    (
        statement: String,
        param: UserCreationParam,
        expectedResult: Seq[String]
    ) =>
      s"result should be $statement" in {
        val result = createValidator(param).apply()
        expectedResult match {
          case Nil =>
            result.toEither.isRight shouldBe true
          case _ =>
            result.toEither.left.value
              .map(_.description)
              .toList shouldBe expectedResult

        }
      }

  }

}
