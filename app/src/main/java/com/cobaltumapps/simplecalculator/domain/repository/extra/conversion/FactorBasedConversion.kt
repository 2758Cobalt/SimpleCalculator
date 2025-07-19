package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.ConversionCalculator

class FactorBasedConversion: ConversionCalculator {

    override fun convertUnits(value: Double, index: Int, factors: List<Double>): List<Double> {
        val result = mutableListOf<Double>()

        if (factors.isNotEmpty()) {
            var currentValue = value
            for (i in index - 1 downTo 0) {
                currentValue *= factors[i]
                result.add(0, currentValue)
            }

            result.add(value)

            currentValue = value
            for (i in index..<factors.size) {
                currentValue /= factors[i]
                result.add(currentValue)
            }
        }

        return result
    }

}