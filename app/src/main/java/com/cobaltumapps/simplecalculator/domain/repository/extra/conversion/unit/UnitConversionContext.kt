package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

class UnitConversionContext(
    private val strategies: Map<String, ConversionStrategy>
): ConversionStrategy {

    override fun calculate(
        calculatorId: String,
        userEntry: Float,
        selectedItemIndex: Int
    ): List<Number> {
        val strategy = strategies[calculatorId] ?: return emptyList()
        return strategy.calculate(calculatorId, userEntry, selectedItemIndex)
    }
}

