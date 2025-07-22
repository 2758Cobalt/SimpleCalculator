package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class SpeedUnit(override val factor: Double): UnitConversion {
    KM_H(1000.0 / 3600.0),    // км/ч → м/с
    KM_S(1000.0),             // км/с → м/с
    MPH(0.44704),             // миль/ч → м/с
    MPS(1.0),                 // м/с → м/с
    MPS_F(1609.344),          // миль/с → м/с
    KNOT(0.514444),           // узлы → м/с
    C(299792458.0)            // скорость света → м/с
}