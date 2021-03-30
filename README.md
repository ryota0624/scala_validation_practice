# scala_validation_practice

validation対象のオブジェクトのどのフィールドvalidationが失敗したかわかるようにいい感じにしてみるとりくみ。

macroでGO

[動く様子](./application/src/test/scala/validator/UserCreationParamValidatorSpec.scala)

[こうやって使うんだよ](./application/src/main/scala/validator/UserCreationParamValidator.scala)

[こういう仕組みでフィールド名をつけているのさ](./macro/src/main/scala/mymacro/WithinContext.scala)
