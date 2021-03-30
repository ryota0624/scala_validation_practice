import shapeless.ops.record.Selector
import shapeless.{LabelledGeneric, _}
import data.Data

object Main extends App {
//  val s = MyMacro.desugar(List(1, 2, 3).reverse)
//  println(s)

  val d = Data(1, 2)
  println(d.double())
  println(d.fail("oh, no!").stack.mkString(","))

  //  implicit val genD = LabelledGeneric[Data]
//  val hl = genD.to(Data(1, 100))
//
//  implicit class RecordOps2[L <: HList](val l : L) extends AnyVal with Serializable {
//    def getWK(k: Witness)(implicit selector : Selector[L, k.T]): (k.T, selector.Out) = (k.value, selector(l))
//  }
//
//
//  val a = (hl.getWK(Symbol("value")))
//  println(hl.getWK(Symbol("value"))._1.name)
}
