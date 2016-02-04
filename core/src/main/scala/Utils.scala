package io.continuum.bokeh

object Utils {
    def uuid4(): String = java.util.UUID.randomUUID.toString

    def snakify(name: String, sep: Char = '_'): String = {
        name.replaceAll("([A-Z]+)([A-Z][a-z])", s"$$1$sep$$2")
            .replaceAll("([a-z\\d])([A-Z])", s"$$1$sep$$2")
            .toLowerCase
    }

    def getClassName[T](obj: T): String = {
        obj.getClass.getSimpleName.stripSuffix("$")
    }
}
