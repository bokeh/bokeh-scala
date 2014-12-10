package io.continuum.bokeh {
    package sampledata {
        package object iris {
            def flowers = Flowers.load()
        }

        package object daylight {
            def Warsaw2013 = Daylight.load("daylight_warsaw_2013.csv")
        }
    }

    package object sampledata {
        val sprint = Sprint.load()
        def autompg = AutoMPG.load()
        def us_states = USStates.load()
        def us_counties = USCounties.load()
        def us_holidays = USHolidays.load()
        def unemployment = Unemployment.load()
    }
}
