package io.continuum.bokeh

trait Row[R, V] {
    def toList(obj: R): List[V]
}
object Row {
    implicit def Tuple1Row[V]: Row[Tuple1[V],                         V] = new Row[Tuple1[V],                         V] {
        def toList(t: Tuple1[V])                         = List(t._1)
    }
    implicit def Tuple2Row[V]: Row[Tuple2[V, V],                      V] = new Row[Tuple2[V, V],                      V] {
        def toList(t: Tuple2[V, V])                      = List(t._1, t._2)
    }
    implicit def Tuple3Row[V]: Row[Tuple3[V, V, V],                   V] = new Row[Tuple3[V, V, V],                   V] {
        def toList(t: Tuple3[V, V, V])                   = List(t._1, t._2, t._3)
    }
    implicit def Tuple4Row[V]: Row[Tuple4[V, V, V, V],                V] = new Row[Tuple4[V, V, V, V],                V] {
        def toList(t: Tuple4[V, V, V, V])                = List(t._1, t._2, t._3, t._4)
    }
    implicit def Tuple5Row[V]: Row[Tuple5[V, V, V, V, V],             V] = new Row[Tuple5[V, V, V, V, V],             V] {
        def toList(t: Tuple5[V, V, V, V, V])             = List(t._1, t._2, t._3, t._4, t._5)
    }
    implicit def Tuple6Row[V]: Row[Tuple6[V, V, V, V, V, V],          V] = new Row[Tuple6[V, V, V, V, V, V],          V] {
        def toList(t: Tuple6[V, V, V, V, V, V])          = List(t._1, t._2, t._3, t._4, t._5, t._6)
    }
    implicit def Tuple7Row[V]: Row[Tuple7[V, V, V, V, V, V, V],       V] = new Row[Tuple7[V, V, V, V, V, V, V],       V] {
        def toList(t: Tuple7[V, V, V, V, V, V, V])       = List(t._1, t._2, t._3, t._4, t._5, t._6, t._7)
    }
    implicit def Tuple8Row[V]: Row[Tuple8[V, V, V, V, V, V, V, V],    V] = new Row[Tuple8[V, V, V, V, V, V, V, V],    V] {
        def toList(t: Tuple8[V, V, V, V, V, V, V, V])    = List(t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8)
    }
    implicit def Tuple9Row[V]: Row[Tuple9[V, V, V, V, V, V, V, V, V], V] = new Row[Tuple9[V, V, V, V, V, V, V, V, V], V] {
        def toList(t: Tuple9[V, V, V, V, V, V, V, V, V]) = List(t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9)
    }
}
