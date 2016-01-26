package io.continuum.bokeh
package sampledata

object PeriodicTable extends CSVSampleData {
    def load(): Elements = {
        val List(_, atomic_number, symbol, name, atomic_mass, color, electronic_configuration,
                 electronegativity, atomic_radius, ionic_radius, van_der_Waals_radius,
                 ionization_energy, electron_affinity, standard_state, bonding_type,
                 melting_point, boiling_point, density, metal, year_discovered, group, period) =
            loadRows("elements.csv").transpose

        def opt(str: String): Option[String] =
            Option(str).filter(_.trim.nonEmpty)

        Elements(
            atomic_number = atomic_number.map(_.toInt),
            symbol = symbol,
            name = name,
            atomic_mass = atomic_mass,
            color = color.map(Color.from_string),
            electronic_configuration = electronic_configuration,
            electronegativity = electronegativity.map(opt(_).map(_.toDouble)),
            atomic_radius = atomic_radius.map(opt(_).map(_.toDouble)),
            ionic_radius = ionic_radius.map(opt),
            van_der_Waals_radius = van_der_Waals_radius.map(opt(_).map(_.toDouble)),
            ionization_energy = ionization_energy.map(opt(_).map(_.toDouble)),
            electron_affinity = electron_affinity.map(opt(_).map(_.toDouble)),
            standard_state = standard_state.map(opt),
            bonding_type = bonding_type.map(opt),
            melting_point = melting_point.map(opt(_).map(_.toInt)),
            boiling_point = boiling_point.map(opt(_).map(_.toInt)),
            density = density.map(opt(_).map(_.toDouble)),
            metal = metal,
            year_discovered = year_discovered,
            group = group.map {
                case "-"   => None
                case group => Some(group.toInt)
            },
            period = period.map(_.toInt))
    }
}

case class Elements(
    atomic_number: List[Int],                     // (units: g/cm^3)
    symbol: List[String],
    name: List[String],
    atomic_mass: List[String],                    // (units: amu)
    color: List[Color],                           // (convention for molecular modeling color)
    electronic_configuration: List[String],
    electronegativity: List[Option[Double]],      // (units: Pauling)
    atomic_radius: List[Option[Double]],          // (units: pm)
    ionic_radius: List[Option[String]],           // (units: pm)
    van_der_Waals_radius: List[Option[Double]],   // (units: pm)
    ionization_energy: List[Option[Double]],      // (units: kJ/mol)
    electron_affinity: List[Option[Double]],      // (units: kJ/mol)
    standard_state: List[Option[String]],         // (solid, liquid, gas)
    bonding_type: List[Option[String]],
    melting_point: List[Option[Int]],             // (units: K)
    boiling_point: List[Option[Int]],             // (units: K)
    density: List[Option[Double]],                // (units: g/cm^3)
    metal: List[String],
    year_discovered: List[String],
    group: List[Option[Int]],
    period: List[Int])
