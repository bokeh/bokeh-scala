package io.continuum.bokeh

import java.io.File
import java.awt.Desktop

import scalax.io.JavaConverters._
import scalax.file.Path

import scala.collection.mutable.ListBuffer
import scala.xml.{Node,NodeSeq,XML}

import play.api.libs.json.{Json,Writes}

class Document(objs: Component*) {
    private val objects = ListBuffer[Component](objs: _*)

    def add(objs: Component*) {
        objects ++= objs
    }

    def fragment(resources: Resources): HTMLFragment = HTMLFragmentWriter(objects.toList, resources).write()
    def fragment(): HTMLFragment = fragment(Resources.default)

    def save(file: File, resources: Resources): HTMLFile = HTMLFileWriter(objects.toList, resources).write(file)
    def save(file: File): HTMLFile = save(file, Resources.default)

    def save(path: String, resources: Resources): HTMLFile = save(new File(path), resources)
    def save(path: String): HTMLFile = save(new File(path))
}

class HTMLFragment(val html: NodeSeq, val styles: NodeSeq, val scripts: NodeSeq) {
    def head: NodeSeq = styles ++ scripts
    def logo: NodeSeq = {
        <div>
            <a href="http://bokeh.pydata.org" target="_blank" class="bk-logo bk-logo-small bk-logo-notebook"></a>
            <span>BokehJS successfully loaded.</span>
        </div>
    }
    def preamble: NodeSeq = head ++ logo
}

object HTMLFragmentWriter {
    def apply(obj: Component): HTMLFragmentWriter = apply(obj, Resources.default)

    def apply(obj: Component, resources: Resources): HTMLFragmentWriter = apply(obj :: Nil, resources)

    def apply(objs: List[Component]): HTMLFragmentWriter = apply(objs, Resources.default)

    def apply(objs: List[Component], resources: Resources): HTMLFragmentWriter = new HTMLFragmentWriter(objs, resources)
}

class HTMLFragmentWriter(objs: List[Component], resources: Resources) {
    def write(): HTMLFragment = {
        new HTMLFragment(divs ++ scripts, resources.styles, resources.scripts)
    }

    implicit val ModelReprFormat = Json.format[ModelRepr]

    case class Root(root_ids: List[String], references: List[ModelRepr])
    case class Doc(roots: List[Root], title: String, version: String)
    case class RenderItem(docid: String, elementid: String, modelid: Option[String])

    implicit val RootFormat = Json.format[Root]
    implicit val DocFormat = Json.format[Doc]
    implicit val RenderItemFormat = Json.format[RenderItem]

    case class Spec(docs_json: Map[String, Doc], render_items: List[RenderItem])

    protected def title: String = "Bokeh Application"

    protected lazy val spec: Spec = {
        val root = Root(objs.map(_.id.value), JSONSerializer.serialize(objs))
        var doc = Doc(root :: Nil, title, Resources.bokehjsVersion)
        val docid = IdGenerator.next()
        val elementid = IdGenerator.next()
        val render_item = RenderItem(docid, elementid, None)
        Spec(Map(docid -> doc), render_item :: Nil)
    }

    protected def divs: NodeSeq = {
        spec.render_items.flatMap { item => <div class="plotdiv" id={ item.elementid }></div> }
    }

    protected def scripts: NodeSeq = {
        def stringify[T:Writes](obj: T): String = {
            resources.stringify(Json.toJson(obj))
        }

        val code = s"""
            |var docs_json = ${stringify(spec.docs_json)};
            |var render_items = ${stringify(spec.render_items)};
            |
            |Bokeh.embed.embed_items(docs_json, render_items);
            """
        resources.wrap(code.stripMargin.trim).asScript
    }
}

class HTMLFile(val file: File) {
    def url: String = {
        val uri = file.toURI
        s"${uri.getScheme}://${uri.getSchemeSpecificPart}"
    }

    def view() {
        if (Desktop.isDesktopSupported && Desktop.getDesktop.isSupported(Desktop.Action.BROWSE))
            Desktop.getDesktop.browse(file.toURI)
    }
}

object HTMLFileWriter {
    def apply(obj: Component): HTMLFileWriter = apply(obj, Resources.default)

    def apply(obj: Component, resources: Resources): HTMLFileWriter = apply(obj :: Nil, resources)

    def apply(objs: List[Component]): HTMLFileWriter = apply(objs, Resources.default)

    def apply(objs: List[Component], resources: Resources): HTMLFileWriter = new HTMLFileWriter(objs, resources)
}

class HTMLFileWriter(objs: List[Component], resources: Resources) extends HTMLFragmentWriter(objs, resources) {
    def write(file: File): HTMLFile = {
        val html = stringify(renderFile(write()))
        Path(file).write(html)
        new HTMLFile(file)
    }

    protected def stringify(html: Node) = {
        val writer = new java.io.StringWriter()
        val doctype = "<!DOCTYPE html>"
        XML.write(writer, html, "UTF-8", xmlDecl=false, doctype=null)
        s"$doctype\n${writer.toString}"
    }

    protected def renderTitle: Option[Node] = {
        spec.render_items
            .headOption
            .flatMap { item => spec.docs_json.get(item.docid) }
            .map { doc => <title>{doc.title}</title> }
    }

    protected def renderFile(fragment: HTMLFragment): Node = {
        <html lang="en">
            <head>
                <meta charset="utf-8" />
                { renderTitle orNull }
                { fragment.head }
            </head>
            <body>
                { fragment.html }
            </body>
        </html>
    }
}
