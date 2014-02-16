package org.continuumio.bokeh
package examples
package glyphs

import Double.NaN

object ForeignCitizens extends App {
    // EuroStat 112/2013 - 17 July 2013
    // http://epp.eurostat.ec.europa.eu/cache/ITY_PUBLIC/3-17072013-BP/EN/3-17072013-BP-EN.PDF
    val statistics = List(
        ("EU27",            34323.4,  6.8,   13613.5,  2.7,   20709.9,  4.1),
        ("Belgium",         1224.9,   11.0,  778.6,    7.0,   446.3,    4.0),
        ("Bulgaria",        42.4,     0.6,   11.3,     0.2,   31.1,     0.4),
        ("Czech Republic",  423.0,    4.0,   151.3,    1.4,   271.7,    2.6),
        ("Denmark",         358.7,    6.4,   134.9,    2.4,   223.8,    4.0),
        ("Germany",         7409.8,   9.1,   2744.8,   3.4,   4665.0,   5.7),
        ("Estonia3",        206.6,    15.7,  14.4,     1.1,   192.2,    14.6),
        ("Ireland",         487.9,    10.6,  388.8,    8.5,   99.1,     2.2),
        ("Greece",          975.4,    8.6,   151.2,    1.3,   824.2,    7.3),
        ("Spain",           5562.1,   12.0,  2354.5,   5.1,   3207.6,   6.9),
        ("France",          3858.3,   5.9,   1353.1,   2.1,   2505.2,   3.8),
        ("Croatia",         23.3,     0.5,   7.7,      0.2,   15.6,     0.4),
        ("Italy",           4825.6,   7.9,   1450.1,   2.4,   3375.4,   5.5),
        ("Cyprus",          172.4,    20.0,  108.3,    12.6,  64.1,     7.4),
        ("Latvia3",         332.9,    16.3,  6.7,      0.3,   326.2,    16.0),
        ("Lithuania",       20.6,     0.7,   3.0,      0.1,   17.6,     0.6),
        ("Luxembourg",      229.9,    43.8,  198.7,    37.9,  31.2,     5.9),
        ("Hungary",         207.6,    2.1,   127.9,    1.3,   79.7,     0.8),
        ("Malta",           20.5,     4.9,   NaN,      NaN,   NaN,      NaN),
        ("Netherlands",     697.7,    4.2,   360.8,    2.2,   336.9,    2.0),
        ("Austria",         947.7,    11.2,  382.7,    4.5,   565.0,    6.7),
        ("Poland",          57.5,     0.1,   18.4,     0.0,   39.0,     0.1),
        ("Portugal",        439.1,    4.2,   108.0,    1.0,   331.1,    3.1),
        ("Romania",         36.5,     0.2,   7.0,      0.0,   29.5,     0.1),
        ("Slovenia",        85.6,     4.2,   6.1,      0.3,   79.5,     3.9),
        ("Slovakia",        70.7,     1.3,   54.0,     1.0,   16.7,     0.3),
        ("Finland",         181.7,    3.4,   68.3,     1.3,   113.4,    2.1),
        ("Sweden",          646.1,    6.8,   276.0,    2.9,   370.1,    3.9),
        ("United Kingdom",  4802.3,   7.6,   2344.1,   3.7,   2458.2,   3.9),
        ("Iceland",         21.0,     6.6,   16.5,     5.2,   4.5,      1.4),
        ("Liechtenstein",   12.1,     33.3,  6.0,      16.6,  6.1,      16.7),
        ("Norway",          409.2,    8.2,   247.2,    5.0,   161.9,    3.3),
        ("Switzerland",     1815.1,   22.8,  1141.1,   14.3,  673.9,    8.5),
        ("Montenegro",      44.3,     7.2,   0.7,      0.1,   43.6,     7.0),
        ("Turkey",          235.1,    0.3,   95.8,     0.1,   139.2,    0.2))

    val labels = List[(String, Color)](
        ("< 1%",       "#ffeeaa"),
        ("1% - 4%",    "#ffdd55"),
        ("4% - 7%",    "#f5c60a"),
        ("7% - 10%",   "#d4aa00"),
        ("10% - 13%",  "#aa8800"),
        ("> 13%",      "#645000"))

    val ncEU27: Color = "#ff5555"
    val ncnEU27: Color = "#0066ff"

    val plot = new Plot()

    val session = new HTMLFileSession("foreign_citizens.html")
    session.save(plot)
    println(s"Wrote ${session.file}. Open ${session.url} in a web browser.")
}
