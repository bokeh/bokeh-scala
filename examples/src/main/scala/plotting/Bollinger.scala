package io.continuum.bokeh
package examples
package plotting

object Bollinger extends App {
    /*
    // Example of computing Bollinger bands with the Patch glyph.
    // Maybe this can be simplified?
    // Any other critique is well appreciated.

    // Declare output file

    output_file("bollinger.html", title="Bollinger bands (file)")

    // Define Bollinger Bands.

    upperband = np.random.random_integers(100, 150, size=100)

    lowerband = upperband - 100

    x_data = np.arange(1, 101)


    // Bollinger shading glyph:

    band = np.append(lowerband, upperband[::-1])  // Reverse the upper band data and append it to the lower band data.

    x_banddata = np.append(x_data, x_data[::-1])  // Do the same for the x data.
                                                  // Usually, it doesn"t matter what gets appended to what for the x data.

    // TODO: Explain how Patch works, and why the data has to be manipulated in this manner.

    patch(x_banddata, band, color="#7570B3", fill_alpha=0.2, x_axis_type="datetime")


    // Define plot parameters.

    curplot().title := "Bollinger Bands"
    curplot().height := 600
    curplot().width := 800

    grid().grid_line_alpha = 0.4

    // Finally, display plot.

    show()
    */
}
