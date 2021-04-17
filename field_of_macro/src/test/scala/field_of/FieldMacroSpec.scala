package field_of

import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpecLike
case class Data(name: String)
class FieldMacroSpec extends AnyWordSpecLike {
  "Field" in {
    val data = Data("taro")
    val f = FieldMacro.fieldOf(data.name)(_ ++ _)
    f shouldBe ("nametaro")
  }
}
