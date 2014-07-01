package org.continuumio.bokeh

object Utils {
    def uuid4(): String = java.util.UUID.randomUUID.toString

    def snakify(name: String): String =
        name.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
            .replaceAll("([a-z\\d])([A-Z])", "$1_$2")
            .toLowerCase
}
