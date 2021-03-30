package validation

case class ValidationContext(target: Class[Nothing], fields: Seq[String]) {
  def addField(field: String): ValidationContext = {
    copy(fields = fields :+ field)
  }
}
