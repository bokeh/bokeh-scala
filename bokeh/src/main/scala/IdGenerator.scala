package io.continuum.bokeh

trait IdGenerator {
    def next(): String
}

object UUIDGenerator extends IdGenerator {
    def next() = Utils.uuid4()
}

object CounterGenerator extends IdGenerator {
    private var counter = 0

    def next() = synchronized {
        val id = counter
        counter += 1
        id.toString
    }
}

object IdGenerator {
    private var implementation: Option[IdGenerator] = None

    def setImplementation(impl: IdGenerator, silent: Boolean=false) {
        implementation match {
            case Some(_) => if (!silent) throw new IllegalStateException("ID generator was already configured")
            case None    => implementation = Some(impl)
        }
    }

    def next(): String = {
        implementation getOrElse UUIDGenerator next()
    }
}
