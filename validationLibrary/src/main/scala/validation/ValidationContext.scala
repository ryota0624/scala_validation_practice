package validation

case class ValidationContext(convertTarget: Class[_], fields: Seq[String]) {
  def addField(field: String): ValidationContext = {
    copy(fields = fields :+ field)
  }
}
