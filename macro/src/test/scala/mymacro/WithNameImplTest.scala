package mymacro

import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike

import scala.language.postfixOps

class WithNameImplTest extends AnyWordSpecLike {
  "withName" in {
    val original = """withName(amount, fnc)"""
    WithNameImpl.transform(original) shouldBe """fnc("amount", amount)"""

  }
}
