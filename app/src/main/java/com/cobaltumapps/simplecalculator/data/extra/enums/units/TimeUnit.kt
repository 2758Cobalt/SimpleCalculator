package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class TimeUnit(override val factor: Double): UnitConversion {
    NS(1e-9),
    US(1e-6),
    MS(1e-3),
    S(1.0),
    MIN(60.0),
    H(3600.0),
    DAY(86400.0),
    WEEK(604800.0),
    MONTH(2.628e6),
    YEAR(3.154e7),
    DECADE(3.154e8),
    CENTURY(3.154e9)
}