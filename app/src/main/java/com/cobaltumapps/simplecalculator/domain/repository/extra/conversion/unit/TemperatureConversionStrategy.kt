package com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.unit

import com.cobaltumapps.simplecalculator.domain.repository.extra.conversion.interfaces.ConversionStrategy
import java.math.BigDecimal
import java.math.MathContext

class TemperatureConversionStrategy : ConversionStrategy {

    override fun calculate(
        calculatorId: String,
        userEntry: String,
        selectedItemIndex: Int
    ): List<BigDecimal> {
        val result = mutableListOf<BigDecimal>()

        val input = try {
            BigDecimal(userEntry)
        } catch (e: NumberFormatException) {
            return emptyList()
        }

        val mc = MathContext.DECIMAL128

        val converters: List<(BigDecimal) -> BigDecimal> = when (selectedItemIndex) {
            // Celsius
            0 -> listOf(
                { it },
                { it.multiply(BigDecimal("9")).divide(BigDecimal("5"), mc).add(BigDecimal("32")) },
                { it.add(BigDecimal("273.15")) },
                { it.multiply(BigDecimal("9")).divide(BigDecimal("5"), mc).add(BigDecimal("491.67")) },
                { it.multiply(BigDecimal("0.8")) }
            )
            // Fahrenheit
            1 -> listOf(
                { it.subtract(BigDecimal("32")).multiply(BigDecimal("5")).divide(BigDecimal("9"), mc) },
                { it },
                { it.subtract(BigDecimal("32")).multiply(BigDecimal("5")).divide(BigDecimal("9"), mc).add(BigDecimal("273.15")) },
                { it.add(BigDecimal("459.67")) },
                { it.subtract(BigDecimal("32")).multiply(BigDecimal("0.44444")) }
            )
            // Kelvin
            2 -> listOf(
                { it.subtract(BigDecimal("273.15")) },
                { it.subtract(BigDecimal("273.15")).multiply(BigDecimal("9")).divide(BigDecimal("5"), mc).add(BigDecimal("32")) },
                { it },
                { it.multiply(BigDecimal("1.8")) },
                { it.subtract(BigDecimal("273.15")).multiply(BigDecimal("0.8")) }
            )
            // Rankine
            3 -> listOf(
                { it.subtract(BigDecimal("491.67")).multiply(BigDecimal("5")).divide(BigDecimal("9"), mc) },
                { it.subtract(BigDecimal("459.67")) },
                { it.multiply(BigDecimal("5")).divide(BigDecimal("9"), mc) },
                { it },
                { it.subtract(BigDecimal("491.67")).multiply(BigDecimal("0.44444")) }
            )
            // Reaumur
            4 -> listOf(
                { it.divide(BigDecimal("0.8"), mc) },
                { it.multiply(BigDecimal("2.25")).add(BigDecimal("32")) },
                { it.divide(BigDecimal("0.8"), mc).add(BigDecimal("273.15")) },
                { it.multiply(BigDecimal("2.25")).add(BigDecimal("491.67")) },
                { it }
            )
            else -> emptyList()
        }

        for (converter in converters) {
            result.add(converter(input))
        }

        return result
    }
}
