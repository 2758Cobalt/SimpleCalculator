package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class AreaUnit(override val factor: Double): UnitConversion {
    NM2(1e-18),
    UM2(1e-12),
    MM2(1e-6),
    CM2(1e-4),
    DM2(1e-2),
    M2(1.0),
    DAM2(1e2),
    KM2(1e6)
}