package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer.unit

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter.Formatter
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class BigDecimalFormatter(private val formatter: Formatter): Formatter {
    private val decimalPrecision: Int = 20
    private val maxZeroDigitsThreshold: Int = 10

    override fun format(sourceString: String): String {
        return formatter.format(formatDecimal(sourceString))
    }

    private fun formatDecimal(sourceString: String): String {
        val value = BigDecimal(sourceString)

        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return "0"
        }

        val scaled = value
            .setScale(decimalPrecision, RoundingMode.HALF_UP)
            .stripTrailingZeros()

        val plain = scaled.toPlainString()

        val parts = plain.split('.')
        val integerPart = parts[0].trimStart('-')
        val fractionalPart = if (parts.size > 1) parts[1] else ""

        val leadingZeros = fractionalPart.takeWhile { it == '0' }.length
        val trailingZerosInInt = integerPart.reversed().takeWhile { it == '0' }.length

        val tooManyZeros =
            leadingZeros >= maxZeroDigitsThreshold ||
                    trailingZerosInInt >= maxZeroDigitsThreshold ||
                    integerPart.length >= maxZeroDigitsThreshold + 1

        return if (tooManyZeros) {
            scaled.round(MathContext(decimalPrecision)).toEngineeringString()
        } else {
            plain
        }
    }

}