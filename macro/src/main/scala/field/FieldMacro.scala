package field

import com.github.dwickern.macros.NameOfImpl

import scala.reflect.macros.whitebox

trait FieldMacro {
  def fieldOf[E](expr: E): Field[E] = macro FieldMacroImpl.fieldOf[E]
}

object FieldMacro extends FieldMacro

object FieldMacroImpl {
  def fieldOf[E](c: whitebox.Context)(expr: c.Expr[Any]): c.Expr[E] = {
    import c.universe._
    val nameExpr = NameOfImpl.nameOf(c)(expr)

    // 第一引数をofNameに食わせる
    // 第二引数を抽出
    // exprの関数呼び出しを
    c.Expr(q"""
         import field.Field
         Field(${nameExpr}, ${expr})
       """)
  }
}
