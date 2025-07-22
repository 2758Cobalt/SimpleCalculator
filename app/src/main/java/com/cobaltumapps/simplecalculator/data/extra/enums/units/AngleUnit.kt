package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class AngleUnit(override val factor: Double) : UnitConversion {
    RADIANS(1.0),               // Радиан
    DEGREES(Math.PI / 180.0),   // Градус
    GRADIAN(Math.PI / 200.0),   // Градиан (град)
    MINUTES(Math.PI / (180.0 * 60.0)),  // Угловая минута
    SECONDS(Math.PI / (180.0 * 3600.0)),// Угловая секунда
    CIRCLE(2 * Math.PI),        // Полный круг
    QUADRANT(Math.PI / 2.0)     // Четверть круга
}
