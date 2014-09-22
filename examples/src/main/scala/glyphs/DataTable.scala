package io.continuum.bokeh
package examples
package glyphs

import ColumnType.{Numeric,Autocomplete,Dropdown}
import widgets.{TableColumn,HandsonTable}

object DataTable extends Example {
    val mpg = sampledata.autompg

    val source = new ColumnDataSource()
        .addColumn('manufacturer, mpg.manufacturer)
        .addColumn('model, mpg.model)
        .addColumn('displ, mpg.displ)
        .addColumn('year, mpg.year)
        .addColumn('cyl, mpg.cyl)
        .addColumn('trans, mpg.trans)
        .addColumn('drv, mpg.drv)
        .addColumn('cls, mpg.cls)
        .addColumn('cty, mpg.cty)
        .addColumn('hwy, mpg.hwy)

    val manufacturers = mpg.manufacturer.distinct.sorted
    val models        = mpg.model.distinct.sorted
    val transmissions = mpg.trans.distinct.sorted
    val drives        = mpg.drv.distinct.sorted
    val classes       = mpg.cls.distinct.sorted

    val columns =
        new TableColumn().field("manufacturer").header("Manufacturer").`type`(Autocomplete).source(manufacturers)   ::
        new TableColumn().field("model").header("Model").`type`(Autocomplete).source(models)                        ::
        new TableColumn().field("displ").header("Displacement").`type`(Numeric).format("0.00")                      ::
        new TableColumn().field("year").header("Year").`type`(Numeric)                                              ::
        new TableColumn().field("cyl").header("Cylinders").`type`(Numeric)                                          ::
        new TableColumn().field("trans").header("Transmission").`type`(Dropdown).strict(true).source(transmissions) ::
        new TableColumn().field("drv").header("Drive").`type`(Autocomplete).strict(true).source(drives)             ::
        new TableColumn().field("cls").header("Class").`type`(Autocomplete).strict(true).source(classes)            ::
        new TableColumn().field("cty").header("City MPG").`type`(Numeric)                                           ::
        new TableColumn().field("hwy").header("Highway MPG").`type`(Numeric)                                        :: Nil

    val table = new HandsonTable().source(source).columns(columns).sorting(true)

    val document = new Document(table)
    val html = document.save("data_table.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
