package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class WeightUnit(override val factor: Double): UnitConversion {
    UG(1e-6),
    MG(1e-3),
    G(1.0),
    KG(1e3),
    T(1e6),
    KT(1e9),
    MT(1e12)
}