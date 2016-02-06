package io.continuum.bokeh
package tests

import org.specs2.mutable._

sealed trait MyEnum extends EnumType
@enum object MyEnum extends Enumerated[MyEnum] {
    case object Foo extends MyEnum
    case object Bar extends MyEnum
    case object Baz extends MyEnum
}

class EnumSpec extends Specification {
    "Enumerated[EnumType]" should {
        "support values" in {
            MyEnum.values shouldEqual List(MyEnum.Foo, MyEnum.Bar, MyEnum.Baz)
        }

        "support fromString" in {
            MyEnum.fromString("Foo") shouldEqual MyEnum.Foo
            MyEnum.fromString("Bar") shouldEqual MyEnum.Bar
            MyEnum.fromString("Baz") shouldEqual MyEnum.Baz
        }

        "support toString" in {
            MyEnum.toString shouldEqual "MyEnum(Foo, Bar, Baz)"
        }
    }
}
