package mymacro

import scala.language.experimental.macros
import scala.reflect.macros.whitebox
import scala.annotation.tailrec

object WithNameImpl {
  def withName(c: whitebox.Context)(tree: c.Tree): c.Tree = {
    import c.universe._

    // 第一引数をofNameに食わせる
    // 第二引数を抽出
    // exprの関数呼び出しを
    ???
  }

  def transform(code: String): String = {
    ???
  }
}


