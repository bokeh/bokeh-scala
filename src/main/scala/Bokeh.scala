package org.continuumio.bokeh

import scala.reflect.runtime.{universe=>u,currentMirror=>cm}

abstract class PlotObject extends HasFields {
    object id extends Field[this.type, String](this, uuid4())

    final def fields: List[String] = {
        val fieldNames = cm.reflect(this)
            .symbol
            .typeSignature
            .members
            .filter(_.isModule)
            .map(_.asModule)
            .filter(_.typeSignature <:< u.typeOf[Field[_, _]])
            .map(_.name.decoded)
            .toList

        fieldNames
    }
}

class PlotContext extends PlotObject {
    object children extends Field[this.type, List[List[Plot]]](this)
}
