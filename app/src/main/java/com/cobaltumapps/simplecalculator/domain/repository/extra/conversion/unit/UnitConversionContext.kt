package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.ConversionStrategy

class UnitConversionContext(
    private val strategies: Map<String, ConversionStrategy>
): ConversionStrategy {

    override fun calculate(
        calculatorId: String,
        userEntry: String,
        selectedItemIndex: Int
    ): List<Number> {
        val strategy = strategies[calculatorId] ?: return emptyList()
        return strategy.calculate(calculatorId, userEntry, selectedItemIndex)
    }
}

