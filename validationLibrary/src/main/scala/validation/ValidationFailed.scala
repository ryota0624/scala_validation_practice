package validation

case class ValidationFailed(ctx: ValidationContext, message: String) {
  def within(field: String): ValidationFailed = copy(ctx = ctx.addField(field))

  def description: String =
    s"${message}. at ${ctx.fields.reverse.mkString(".")}. not satisfied for ${ctx.convertTarget.getSimpleName}"
}

object ValidationFailed {
  def apply(throwable: Throwable, clazz: Class[_]): ValidationFailed = {
    val ctx = ValidationContext.apply(clazz, Nil)
    ValidationFailed(
      ctx,
      throwable.getMessage,
    )
  }
}
