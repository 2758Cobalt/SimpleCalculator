package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

import com.cobaltumapps.simplecalculator.data.extra.constants.context.UniversalConversionStrategyContext
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.ConversionStrategy
import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.UnitConversion
import java.math.BigDecimal
import java.math.MathContext

class UniversalConversionStrategy: ConversionStrategy {

    override fun calculate(
        calculatorId: String,
        userEntry: String,
        selectedItemIndex: Int
    ): List<Number> {
        val units = UniversalConversionStrategyContext.unitsByCalculatorId[calculatorId] ?: emptyArray()
        return convert(userEntry, selectedItemIndex, units)
    }

    private fun convert(
        userEntry: String,
        fromIndex: Int,
        units: Array<UnitConversion>
    ): List<BigDecimal> {
        val fromUnit = units[fromIndex]
        val inputValue = BigDecimal(userEntry)
        val inBase = inputValue.multiply(BigDecimal.valueOf(fromUnit.factor), MathContext.UNLIMITED)

        return units.map { to ->
            inBase.divide(BigDecimal.valueOf(to.factor), MathContext.DECIMAL128)
        }
    }

}