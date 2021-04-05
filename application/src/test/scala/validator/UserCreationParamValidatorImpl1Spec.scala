package validator

import data.user.User.UserCreationParam

class UserCreationParamValidatorImpl1Spec extends UserCreationParamValidatorImplSpec {
  override def createValidator: UserCreationParam => UserCreationParamValidator = UserCreationParamValidatorImpl1(_)
}
