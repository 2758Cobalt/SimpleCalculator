package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class FrequencyUnit(override val factor: Double): UnitConversion {
    CHZ(0.01),
    DHZ(0.1),
    HZ(1.0),
    KHZ(1e3),
    MHZ(1e6),
    GHZ(1e9),
    THZ(1e12),
    PHZ(1e15)
}