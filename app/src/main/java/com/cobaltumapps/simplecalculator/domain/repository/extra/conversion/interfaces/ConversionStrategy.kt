package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces

interface ConversionStrategy {
    fun calculate(calculatorId: String, userEntry: String, selectedItemIndex: Int): List<Number>
}