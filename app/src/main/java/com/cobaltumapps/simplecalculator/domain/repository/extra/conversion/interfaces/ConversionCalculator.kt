package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces

interface ConversionCalculator {
    fun convertUnits(userEntry: Double, selectedUnitIndex: Int, factors: List<Double>): List<Double>
}