package com.cobaltumapps.simplecalculator.data.extra.constants

object UnitsFactors {
    val weightFactors = listOf(1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0) // ug, mg, g, kg, t, kt, mt
    val lengthFactors = listOf(
        1000.0, // nm → µm
        1000.0, // µm → mm
        10.0,   // mm → cm
        10.0,   // cm → dm
        10.0,   // dm → m
        10.0,   // m → dam
        10.0,   // dam → hm
        10.0    // hm → km
    )

    val timeFactors = listOf(
        1000.0,      // ns → µs
        1000.0,      // µs → ms
        1000.0,      // ms → s
        60.0,        // s → min
        60.0,        // min → h
        24.0,        // h → day
        7.0,         // day → week
        4.345,     // week → month
        12.0,        // month → year
        10.0,        // year → decade
        10.0         // decade → century
    )

    val volumeFactors = listOf(10.0, 10.0, 10.0, 10.0, 10.0, 10.0, 1000.0) // ml, cl, dl, l, dal, hl, kl, m³
}