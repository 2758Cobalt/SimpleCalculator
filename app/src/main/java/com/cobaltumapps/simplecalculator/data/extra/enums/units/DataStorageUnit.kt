package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class DataStorageUnit(override val factor: Double): UnitConversion {
    BIT(0.125), // 1 Bit = 0.125 Byte
    BYTE(1.0),
    KB(1e3),
    MB(1e6),
    GB(1e9),
    TB(1e12),
    PB(1e15),
    EB(1e18),
    ZB(1e21),
    YB(1e24)
}