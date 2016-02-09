package io.continuum.bokeh

object Json extends Json
trait Json extends upickle.AttributeTagged with JsonSyntax {
    implicit def JsWriter[T <: Js.Value] = Writer[T] {
        case value => value
    }

    implicit def EnumWriter[T <: EnumType] = Writer[T] {
        case value => writeJs(value.name)
    }

    implicit val PercentWriter = Writer[Percent] {
        case percent => writeJs(percent.value)
    }

    implicit val ColorWriter = Writer[Color] {
        case color => writeJs(color.toCSS)
    }

    implicit val FontSizeWriter = Writer[FontSize] {
        case size => writeJs(size.toCSS)
    }

    implicit val TooltipWriter = Writer[Tooltip] {
        case StringTooltip(string) => writeJs(string)
        case HTMLTooltip(html)     => writeJs(html.toString)
        case TabularTooltip(rows)  => writeJs(rows)
    }

    implicit val OrientationWrites = Writer[Orientation] {
        case Orientation.Angle(value) => writeJs(value)
        case value                    => EnumWriter.write(value)
    }

    implicit def HasFieldsWriter[T <: HasFields] = Writer[T] {
        case (obj: Model) => writeJs(obj.getRef)
        case  obj         => obj.fieldsToJson(false)
    }
}

trait JsonSyntax { self: Json =>
    // Inspired by play-json's "Simplified JSON Syntax"

    case class JsWrapper(value: Js.Value)
    implicit def wrapJsValue[T:Writer](value: T): JsWrapper = JsWrapper(writeJs(value))

    def obj(pairs: (String, JsWrapper)*): Js.Obj = {
        Js.Obj(pairs.map { case (k, v) => k -> v.value }: _*)
    }

    def arr(items: JsWrapper*): Js.Arr = {
        Js.Arr(items.map(_.value): _*)
    }
}
