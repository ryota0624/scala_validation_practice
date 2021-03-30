package mymacro

import language.experimental.macros
import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.reflect.macros.whitebox
import scala.meta._

class desugar extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro MyMacro.impl
}

object MyMacro {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList
    annottees.head.
    val (annottee, expandees) = inputs match {
      case (param: ValDef) :: (rest @ (_ :: _)) => (param, rest)
      case (param: TypeDef) :: (rest @ (_ :: _)) => (param, rest)
      case _ => (EmptyTree, inputs)
    }
    println((annottee, expandees))
    val outputs = expandees
    c.Expr[Any](Block(outputs, Literal(Constant(()))))
  }
}

class identity extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro identityMacro.impl
}

object identityMacro {
//  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
//    import c.universe._
//    val inputs = annottees.map(_.tree).toList
//    val (annottee, expandees) = inputs match {
//      case (param: ValDef) :: (rest @ (_ :: _)) => (param, rest)
//      case (param: TypeDef) :: (rest @ (_ :: _)) => (param, rest)
//      case _ => (EmptyTree, inputs)
//    }
//    println((annottee, expandees))
//    val outputs = expandees
//    c.Expr[Any](Block(outputs, Literal(Constant(()))))
//  }

  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList
    val (annottee, expandees) = inputs match {
      case (param: ValDef) :: (rest @ (_ :: _)) => (param, rest)
      case (param: TypeDef) :: (rest @ (_ :: _)) => (param, rest)
      case _ => (EmptyTree, inputs)
    }
    println((annottee, expandees))
    val outputs = expandees
    c.Expr[Any](Block(outputs, Literal(Constant(()))))
  }
}
