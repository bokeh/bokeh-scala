package io.continuum.bokeh
package examples
package models

import widgets.{
    DataTable,TableColumn,
    NumberFormatter,StringFormatter,
    IntEditor,NumberEditor,StringEditor,SelectEditor}

object DataTables extends Example {
    val mpg = sampledata.autompg

    object source extends ColumnDataSource {
        val index = column(mpg.index)
        val manufacturer = column(mpg.manufacturer)
        val model = column(mpg.model)
        val displ = column(mpg.displ)
        val year = column(mpg.year)
        val cyl = column(mpg.cyl)
        val trans = column(mpg.trans)
        val drv = column(mpg.drv)
        val cls = column(mpg.cls)
        val cty = column(mpg.cty)
        val hwy = column(mpg.hwy)
    }

    import source.{index,manufacturer,model,displ,year,cyl,trans,drv,cls,cty,hwy}

    val plot = {
        val xdr = new DataRange1d()
        val ydr = new DataRange1d()
        val plot = new Plot().title().x_range(xdr).y_range(ydr).width(1000).height(300)
        val xaxis = new LinearAxis().plot(plot)
        plot.below <<= (xaxis :: _)
        val yaxis = new LinearAxis().plot(plot)
        val ygrid = new Grid().plot(plot).dimension(1).ticker(yaxis.ticker.value)
        plot.left <<= (yaxis :: _)
        val cty_glyph = new Circle().x('index).y(cty).fill_color("#396285").size(8).fill_alpha(0.5).line_alpha(0.5)
        val hwy_glyph = new Circle().x('index).y(hwy).fill_color("#CE603D").size(8).fill_alpha(0.5).line_alpha(0.5)
        val cty_renderer = new GlyphRenderer().data_source(source).glyph(cty_glyph)
        val hwy_renderer = new GlyphRenderer().data_source(source).glyph(hwy_glyph)
        plot.renderers := List(cty_renderer, hwy_renderer, xaxis, yaxis, ygrid)
        val tooltips = List(
            "Manufacturer" -> "@manufacturer",
            "Model"        -> "@model",
            "Displacement" -> "@displ",
            "Year"         -> "@year",
            "Cylinders"    -> "@cyl",
            "Transmission" -> "@trans",
            "Drive"        -> "@drv",
            "Class"        -> "@cls")
        val cty_hover_tool = new HoverTool().plot(plot).renderers(cty_renderer :: Nil).tooltips(Tooltip(tooltips :+ ("City MPG"    -> "@cty")))
        val hwy_hover_tool = new HoverTool().plot(plot).renderers(hwy_renderer :: Nil).tooltips(Tooltip(tooltips :+ ("Highway MPG" -> "@hwy")))
        val select_tool = new BoxSelectTool().plot(plot).renderers(cty_renderer :: hwy_renderer :: Nil).dimensions(Dimension.Width :: Nil)
        plot.tools := List(cty_hover_tool, hwy_hover_tool, select_tool)
        plot
    }

    val data_table = {
        val manufacturers = mpg.manufacturer.distinct.sorted
        val models        = mpg.model.distinct.sorted
        val transmissions = mpg.trans.distinct.sorted
        val drives        = mpg.drv.distinct.sorted
        val classes       = mpg.cls.distinct.sorted

        val columns = List(
            new TableColumn().field('manufacturer) .title("Manufacturer") .editor(new SelectEditor().options(manufacturers)) .formatter(new StringFormatter().font_style(FontStyle.Bold)),
            new TableColumn().field('model)        .title("Model")        .editor(new StringEditor().completions(models)),
            new TableColumn().field('displ)        .title("Displacement") .editor(new NumberEditor().step(0.1))              .formatter(new NumberFormatter().format("0.0")),
            new TableColumn().field('year)         .title("Year")         .editor(new IntEditor()),
            new TableColumn().field('cyl)          .title("Cylinders")    .editor(new IntEditor()),
            new TableColumn().field('trans)        .title("Transmission") .editor(new SelectEditor().options(transmissions)),
            new TableColumn().field('drv)          .title("Drive")        .editor(new SelectEditor().options(drives)),
            new TableColumn().field('cls)          .title("Class")        .editor(new SelectEditor().options(classes)),
            new TableColumn().field('cty)          .title("City MPG")     .editor(new IntEditor()),
            new TableColumn().field('hwy)          .title("Highway MPG")  .editor(new IntEditor())
        )
        new DataTable().source(source).columns(columns).editable(true)
    }

    val layout = new VBox().children(plot :: data_table :: Nil)

    val document = new Document(layout)
    val html = document.save("data_tables.html", config.resources)
    info(s"Wrote ${html.file}. Open ${html.url} in a web browser.")
}
