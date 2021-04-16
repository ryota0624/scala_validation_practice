package field_of

object Field {
  case class ValidationFailure(message: String, fields: Seq[String] = Nil) {
    def occurredOn(field: String): ValidationFailure = copy(fields = fields :+ field)
  }

  type ValidationResult[T] = Either[ValidationFailure, T]
  type Validator[I, O] = I => ValidationResult[O]

  implicit class ValidationResultOps[T](e: ValidationResult[T]) {
    def occurredOn(field: String): ValidationResult[T] = {
      e.left.map(_.occurredOn(field))
    }
  }

  def of[E](expr: E): Field[E] = macro FieldOfMacroImpl.fieldOf[E]
}


case class Field[V](value: V, name: String) {
  import Field._
  def validate[O](
                   validator: Validator[V, O]
                 ): ValidationResult[O] =
    validator(value).occurredOn(name)
}

import scala.reflect.macros.whitebox

object FieldOfMacro {
  def fieldOf[E](expr: E): Field[E] = macro FieldOfMacroImpl.fieldOf[E]
}

object FieldOfMacroImpl {
  def fieldOf[E](c: whitebox.Context)(expr: c.Expr[Any]): c.Expr[E] = {
    import c.universe._
    import com.github.dwickern.macros.NameOfImpl
    val nameExpr = NameOfImpl.nameOf(c)(expr)
    c.Expr(q"""
         import field_of.Field
         Field(${expr}, ${nameExpr})
       """)
  }
}
