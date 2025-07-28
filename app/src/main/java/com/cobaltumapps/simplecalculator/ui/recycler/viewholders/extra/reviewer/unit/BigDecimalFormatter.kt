package com.cobaltumapps.simplecalculator.ui.recycler.viewholders.extra.reviewer.unit

import com.cobaltumapps.simplecalculator.v15.calculator.components.display.formatter.Formatter
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class BigDecimalFormatter(private val formatter: Formatter): Formatter {

    override fun format(sourceString: String): String {
        val formattedDecimal = formatDecimal(sourceString)
        return if (BigDecimal(formattedDecimal).compareTo(BigDecimal("0.0")) == 0) "0"
        else formatter.format(formattedDecimal)
    }

    private fun formatDecimal(sourceString: String, minFraction: Int = 8, maxFraction: Int = 8): String {
        val value = BigDecimal(sourceString)
        val absValue = value.abs()
        val symbols = DecimalFormatSymbols(Locale.US)

        val scientificFormat = DecimalFormat("0." + "#".repeat(minFraction) + "E0", symbols)
        scientificFormat.maximumFractionDigits = minFraction

        val plainFormat = DecimalFormat("0." + "#".repeat(maxFraction), symbols)

        return when {
            absValue < BigDecimal("1E-6") || absValue >= BigDecimal("1E6") ->
                scientificFormat.format(value)
            else ->
                plainFormat.format(value)
        }
    }

}