package field_of

import field.Field
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike

class FieldSpec extends AnyWordSpecLike {
  "Field" in {
      Field("field", 100).tuple shouldBe ("field", 100)
  }
}
