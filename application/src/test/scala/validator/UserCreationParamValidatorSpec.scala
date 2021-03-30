package validator

import data.user.User.UserCreationParam
import org.scalatest.EitherValues._
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalatest.wordspec.AnyWordSpecLike

class UserCreationParamValidatorSpec
    extends AnyWordSpecLike
    with TableDrivenPropertyChecks {
  val params = Table[String, UserCreationParam, Option[String]](
    ("statement", "param", "expectedResult"),
    (
      "ok",
      UserCreationParam(
        24,
        UserCreationParam.Color(200, 200, 200),
        UserCreationParam.Color(100, 200, 100)
      ),
      None
    ),
    (
      "failure in favorite color green",
      UserCreationParam(
        24,
        UserCreationParam.Color(200, 200, 300),
        UserCreationParam.Color(100, 200, 100)
      ),
      Some("requirement failed: number should be smaller than 255. at favoriteColor.blue. not satisfied for ColorElement$ requirement")
    )
  )

  forAll(params) {
    (
        statement: String,
        param: UserCreationParam,
        expectedResult: Option[String]
    ) =>
      s"result should be $statement" in {
        val result = UserCreationParamValidator(param)()
        expectedResult match {
          case Some(value) =>
            result.left.value.description shouldBe value
          case None =>
            result.isRight shouldBe true
        }
      }

  }

}
