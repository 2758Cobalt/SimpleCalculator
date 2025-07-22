package com.cobaltumapps.simplecalculator.data.extra.enums.units

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

enum class VolumeUnit(override val factor: Double): UnitConversion {
    MILLILITER(1e-6),           // 0.000001 м³
    LITER(1e-3),                // 0.001 м³
    CUBIC_METER(1.0),           // базовая единица
    CUBIC_FOOT(0.0283168),      // 1 фут³ = 0.0283168 м³
    CUBIC_INCH(1.6387e-5),      // 1 дюйм³ = 0.000016387 м³
    US_GALLON(3.78541e-3)       // 1 галлон (США) = 0.00378541 м³;
}