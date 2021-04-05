package validator

import data.user.User.UserCreationParam

class UserCreationParamValidatorImpl2Spec extends UserCreationParamValidatorImplSpec {
  override def createValidator: UserCreationParam => UserCreationParamValidator = UserCreationParamValidatorImpl2(_)
}
