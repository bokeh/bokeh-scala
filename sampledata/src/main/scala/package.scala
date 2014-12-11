package io.continuum.bokeh {
    package sampledata {
        package object iris {
            val flowers = Flowers.load()
        }

        package object daylight {
            val Warsaw2013 = Daylight.load("daylight_warsaw_2013.csv")
        }

        package object webbrowsers {
            val webbrowsers_nov_2013 = WebBrowsers.load("browsers_nov_2013.csv")
        }
    }

    package object sampledata {
        val sprint = Sprint.load()
        val autompg = AutoMPG.load()
        val us_states = USStates.load()
        val us_counties = USCounties.load()
        val us_holidays = USHolidays.load()
        val unemployment = Unemployment.load()
    }
}
