package io.continuum.bokeh
package tests

import org.specs2.mutable._

sealed trait XYZEnum extends EnumType with LowerCase
@enum object XYZEnum extends Enumerated[XYZEnum] {
    case object X extends XYZEnum
    case object Y extends XYZEnum
    case object Z extends XYZEnum
}

class JsonSpec extends Specification {
    "JSON serializer" should {
        "support Option[T] & Some[T] & None.type" in {
            Json.writeJs[Option[Int]](Some(1)) shouldEqual Js.Num(1)
            Json.writeJs[Option[Int]](None)    shouldEqual Js.Null

            Json.writeJs(Some(1)) shouldEqual Js.Num(1)
            Json.writeJs(None)    shouldEqual Js.Null
        }

        "support Map[K:Stringable, V]" in {
            val result = Js.Obj("x" -> Js.Num(1), "y" -> Js.Num(2), "z" -> Js.Num(3))

            import XYZEnum.{X,Y,Z}

            Json.writeJs(Map("x" -> 1, "y" -> 2, "z" -> 3)) shouldEqual result
            Json.writeJs(Map('x  -> 1, 'y  -> 2, 'z  -> 3)) shouldEqual result
            Json.writeJs(Map(X   -> 1, Y   -> 2, Z   -> 3)) shouldEqual result

            Json.writeJs(Map("z" -> 3, "y" -> 2, "x" -> 1)) shouldEqual result
            Json.writeJs(Map('z  -> 3, 'y  -> 2, 'x  -> 1)) shouldEqual result
            Json.writeJs(Map(Z   -> 3, Y   -> 2, X   -> 1)) shouldEqual result
        }
    }
}
