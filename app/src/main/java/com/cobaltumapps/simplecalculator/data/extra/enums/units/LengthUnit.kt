package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class LengthUnit(override val factor: Double): UnitConversion {
    NM(1e-9),
    UM(1e-6),
    MM(1e-3),
    CM(1e-2),
    DM(1e-1),
    M(1.0),
    DAM(10.0),
    HM(100.0),
    KM(1e3)
}