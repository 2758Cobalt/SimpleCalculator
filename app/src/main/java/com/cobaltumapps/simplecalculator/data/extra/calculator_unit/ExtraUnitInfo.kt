package com.cobaltumapps.simplecalculator.data.extra.calculator_unit

/** Data-класс содержащий информацию о единице измерения для UI */

data class ExtraUnitInfo(
    val unitName: String,
    val unitPreview: String?,
    var unitValue: Float
)