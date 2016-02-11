package io.continuum.bokeh {
    package sampledata {
        package object iris {
            val flowers = Flowers.load()
        }

        package object mtb {
            val obiszow_mtb_xcm = MTB.load("obiszow_mtb_xcm.csv")
        }

        package object daylight {
            val Warsaw2013 = Daylight.load("daylight_warsaw_2013.csv")
        }

        package object webbrowsers {
            val webbrowsers_nov_2013 = WebBrowsers.load("browsers_nov_2013.csv")
        }
    }

    package object sampledata {
        val autompg        = AutoMPG.load()
        val periodic_table = PeriodicTable.load()
        val sprint         = Sprint.load()
        val unemployment   = Unemployment.load()
        val us_counties    = USCounties.load()
        val us_holidays    = USHolidays.load()
        val us_states      = USStates.load()
        val world_cities   = WorldCities.load()
    }
}
