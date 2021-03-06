package mymacro

import scala.annotation.StaticAnnotation
import scala.language.experimental.macros
import scala.meta.{Defn, Stat, Term, XtensionParseInputLike, XtensionQuasiquoteTerm}
import scala.reflect.macros.whitebox

class identity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro identityMacro.impl
}

object identityMacro {
  def impl(c: whitebox.Context)(annottees: c.Tree*): c.Tree = {
    annottees.head
  }
}

class ToZero extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro ToZeroMacro.impl
}

object ToZeroMacro {

  def impl(c: whitebox.Context)(annottees: c.Tree*): c.Tree = {
    annottees.headOption.map { tree =>
      val parsed = tree.toString().parse[Stat].get
      val updated = parsed match {
        case method: Defn.Def =>
          val newBody = s"${method.body} + 1000".parse[Term].get
          method.copy(body = newBody)
        case _ =>
          parsed
      }

      c.parse(updated.syntax)
    } getOrElse (sys.error("annottee not found"))
  }
}

