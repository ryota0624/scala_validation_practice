package validation



case class ValidationFailed(ctx: ValidationContext, message: String) {
  def within(field: String): ValidationFailed = copy(ctx = ctx.addField(field))
}
