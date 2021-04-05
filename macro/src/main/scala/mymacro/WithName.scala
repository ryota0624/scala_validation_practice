package mymacro

trait WithName {
  /**
   * Example usage:
   * {{{
   *   val amount = 5
   *   withName(amount, (name: String, amount: Int) => s"$name:$amount") => "amount:5"
   *   withName(amount, (name: String, amount: Int) => s"$name:$amount") => "amount:5"
   *   withName(amount, (name: String) => (amount: Int) => validate(amount).mapLeft(_.addField(name))) => "amount:5"
   *   withName(amount, fnc: (name: String) => (amount: Int)) => "amount:5"
   *   fnc("amount", amount)
   * }}}
   */
  def withName[E](tree: Any): E = macro WithNameImpl.withName
}

object WithName extends WithName
