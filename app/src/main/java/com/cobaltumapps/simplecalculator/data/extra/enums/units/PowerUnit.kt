package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class PowerUnit(override val factor: Double) : UnitConversion {
    MILLI_W(0.001),
    WATT(1.0),               // Ватт — базовая единица
    KW(1_000.0),             // Киловатт
    MW(1_000_000.0),         // Мегаватт
    HP(745.7),               // Лошадиная сила (метрическая)
    BTU_PER_HOUR(0.29307107),// BTU/ч
    CALORIE_PER_SEC(4.1868), // Калория в секунду
    ERG_PER_SEC(1e-7)        // Эрг в секунду
}