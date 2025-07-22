package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

import com.cobaltumapps.simplecalculator.data.extra.constants.UniversalConversionStrategyContext
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.ConversionStrategy
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion

class UniversalConversionStrategy: ConversionStrategy {

    override fun calculate(
        calculatorId: String,
        userEntry: Float,
        selectedItemIndex: Int
    ): List<Number> {
        val units = UniversalConversionStrategyContext.unitsByCalculatorId[calculatorId] ?: emptyArray()
        return convert(userEntry, selectedItemIndex, units)
    }

    private fun convert(
        userEntry: Float,
        fromIndex: Int,
        units: Array<UnitConversion>
    ): List<Number> {
        val fromUnit = units[fromIndex]
        val inBase = userEntry * fromUnit.factor
        return units.map { to ->
            inBase / to.factor
        }
    }
}