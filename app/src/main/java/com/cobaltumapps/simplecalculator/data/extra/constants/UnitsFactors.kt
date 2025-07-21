package com.cobaltumapps.simplecalculator.data.extra.constants

object UnitsFactors {
    val weightFactors = listOf(1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0) // ug, mg, g, kg, t, kt, mt
    val lengthFactors = listOf(
        1000.0, // nm - µm
        1000.0, // µm - mm
        10.0,   // mm - cm
        10.0,   // cm - dm
        10.0,   // dm - m
        10.0,   // m - dam
        10.0,   // dam - hm
        10.0    // hm - km
    )

    val timeFactors = listOf(
        1000.0,      // ns - µs
        1000.0,      // µs - ms
        1000.0,      // ms - s
        60.0,        // s - min
        60.0,        // min - h
        24.0,        // h - day
        7.0,         // day - week
        4.345,       // week - month
        12.0,        // month - year
        10.0,        // year - decade
        10.0         // decade - century
    )

    val volumeFactors = listOf(
        1e3, // ml - l
        1e3, // l - m³
        35.314, // m³ - ft³
        1728.0, // ft³ - inch³
        231.0 // inch³ - US gallon
    )

    val areaFactors = listOf(
        1e6,  // nm² - µm²
        1e6,  // µm² - mm²
        100.0,   // mm² - cm²
        100.0,   // cm² - dm²
        100.0,   // dm² - m²
        100.0,    // m² - dam²
        1e+4,  // dam² - km²
    )

    val frequencyFactors = listOf<Double>(
        10.0, // cHz - dHz
        10.0, // cHz - dHz
        10.0, // dHz - Hz
        1e3, // Hz - kHz
        1e3, // kHz - MHz
        1e3, // MHz - GHz
        1e3,  // GHz - THz
        1e3  // THz - PHz
    )

    val dataBinaryFactors = listOf(
        8.0,    // Bit - Byte (1 Byte = 8 Bits - 1 Bit = 0.125 Byte)
        1e3,  // Byte - KB
        1e3,  // KB - MB
        1e3,  // MB - GB
        1e3,  // GB - TB
        1e3,  // TB - PB
        1e3,  // PB - EB
        1e3,  // EB - ZB
        1e3   // ZB - YB
    )

    val dataTransferRateFactors = listOf(
        1e3,  // kbit/s → mbit/s
        1e3,  // mbit/s → gbit/s
        1e3,  // gbit/s → tbit/s
        1e3,  // tbit/s → pbit/s
    )

}