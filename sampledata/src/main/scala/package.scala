package io.continuum.bokeh {
    package sampledata {
        package object iris {
            lazy val flowers = Flowers.load()
        }

        package object mtb {
            lazy val obiszow_mtb_xcm = MTB.load("obiszow_mtb_xcm.csv")
        }

        package object daylight {
            lazy val Warsaw2013 = Daylight.load("daylight_warsaw_2013.csv")
        }

        package object webbrowsers {
            lazy val webbrowsers_nov_2013 = WebBrowsers.load("browsers_nov_2013.csv")
        }
    }

    package object sampledata {
        lazy val autompg        = AutoMPG.load()
        lazy val periodic_table = PeriodicTable.load()
        lazy val sprint         = Sprint.load()
        lazy val unemployment   = Unemployment.load()
        lazy val us_counties    = USCounties.load()
        lazy val us_holidays    = USHolidays.load()
        lazy val us_states      = USStates.load()
        lazy val world_cities   = WorldCities.load()
    }
}
