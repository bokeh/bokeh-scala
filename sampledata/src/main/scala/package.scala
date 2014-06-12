package org.continuumio.bokeh {
    package sampledata {
        package object iris {
            def flowers = Flowers.load()
        }
    }

    package object sampledata {
        def us_states = USStates.load()
        def us_counties = USCounties.load()
        def unemployment = Unemployment.load()
    }
}
