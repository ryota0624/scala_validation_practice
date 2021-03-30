package mymacro

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.meta.{Defn, Stat, Term, XtensionParseInputLike, XtensionQuasiquoteTerm}
import scala.reflect.macros.whitebox

trait WithinContext {
  type Self
  def within(field: String): Self
}

class within extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro WithinContextMacro.impl
}

object WithinContextMacro {
  def impl(c: whitebox.Context)(annottees: c.Tree*): c.Tree = {
    annottees.headOption.map { tree =>
      val parsed = tree.toString().parse[Stat].get
      val updated = parsed match {
        case method: Defn.Def =>
          val newBody = s"""(${method.body}).within("${method.name.value}")""".parse[Term].get
          method.copy(body = newBody)
        case _ =>
          parsed
      }

      c.parse(updated.syntax)
    } getOrElse sys.error("annottee not found")
  }
}

