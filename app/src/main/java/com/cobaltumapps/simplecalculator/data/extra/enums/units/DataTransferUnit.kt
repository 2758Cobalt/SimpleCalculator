package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class DataTransferUnit(override val factor: Double): UnitConversion {
    KBPS(1e3),
    MBPS(1e6),
    GBPS(1e9),
    TBPS(1e12),
    PBPS(1e15)
}