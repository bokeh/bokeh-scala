package io.continuum.bokeh
package sampledata

object PeriodicTable extends CSVSampleData {
    def load(): List[Element] = {
        def nonEmpty(str: String): Option[String] =
            Option(str).filter(_.trim.nonEmpty)

        loadRows("elements.csv").map {
            case List(_, atomic_number, symbol, name, atomic_mass, color, electronic_configuration,
                electronegativity, atomic_radius, ionic_radius, van_der_Waals_radius,
                ionization_energy, electron_affinity, standard_state, bonding_type,
                melting_point, boiling_point, density, metal, year_discovered, group, period) =>

                Element(
                    atomic_number = atomic_number.toInt,
                    symbol = symbol,
                    name = name,
                    atomic_mass = atomic_mass.replaceAll("\\[|\\]", "").toDouble,
                    color = color,
                    electronic_configuration = electronic_configuration,
                    electronegativity = nonEmpty(electronegativity).map(_.toDouble),
                    atomic_radius = nonEmpty(atomic_radius).map(_.toDouble),
                    ionic_radius = nonEmpty(ionic_radius),
                    van_der_Waals_radius = nonEmpty(van_der_Waals_radius).map(_.toDouble),
                    ionization_energy = nonEmpty(ionization_energy).map(_.toDouble),
                    electron_affinity = nonEmpty(electron_affinity).map(_.toDouble),
                    standard_state = nonEmpty(standard_state),
                    bonding_type = nonEmpty(bonding_type),
                    melting_point = nonEmpty(melting_point).map(_.toInt),
                    boiling_point = nonEmpty(boiling_point).map(_.toInt),
                    density = nonEmpty(density).map(_.toDouble),
                    metal = metal,
                    year_discovered = year_discovered,
                    group = group match {
                        case "-"   => None
                        case group => Some(group.toInt)
                    },
                    period = period.toInt)
        }
    }
}

case class Element(
    atomic_number: Int,                     // (units: g/cm^3)
    symbol: String,
    name: String,
    atomic_mass: Double,                    // (units: amu)
    color: Color,                           // (convention for molecular modeling color)
    electronic_configuration: String,
    electronegativity: Option[Double],      // (units: Pauling)
    atomic_radius: Option[Double],          // (units: pm)
    ionic_radius: Option[String],           // (units: pm)
    van_der_Waals_radius: Option[Double],   // (units: pm)
    ionization_energy: Option[Double],      // (units: kJ/mol)
    electron_affinity: Option[Double],      // (units: kJ/mol)
    standard_state: Option[String],         // (solid, liquid, gas)
    bonding_type: Option[String],
    melting_point: Option[Int],             // (units: K)
    boiling_point: Option[Int],             // (units: K)
    density: Option[Double],                // (units: g/cm^3)
    metal: String,
    year_discovered: String,
    group: Option[Int],
    period: Int)
