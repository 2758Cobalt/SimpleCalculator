package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

interface ConversionStrategy {
    fun calculate(calculatorId: String, userEntry: Float, selectedItemIndex: Int): List<Number>
}