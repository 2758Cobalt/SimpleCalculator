package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class PressureUnit(override val factor: Double): UnitConversion {
    PA(1.0),                   // Паскаль (базовая единица)
    HPA(100.0),                // Гектопаскаль
    KPA(1_000.0),              // Килопаскаль
    BAR(100_000.0),            // Бар
    ATM(101_325.0),            // Атмосфера
    TORR(133.322),             // Торр
    PSI(6_894.76)              // Фунт на кв. дюйм
}